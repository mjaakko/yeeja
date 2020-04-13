package xyz.malkki.yeeja.connection;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.malkki.yeeja.discovery.YeelightDevice;
import xyz.malkki.yeeja.connection.commands.YeelightCommand;
import xyz.malkki.yeeja.connection.internal.CommandResponseRouter;
import xyz.malkki.yeeja.connection.internal.InputThread;
import xyz.malkki.yeeja.connection.internal.OutputThread;
import xyz.malkki.yeeja.internal.ThreadUtils;
import xyz.malkki.yeeja.network.InetAddressFactory;
import xyz.malkki.yeeja.network.SocketFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static xyz.malkki.yeeja.internal.MathUtils.doubleToInt;

/**
 * Represents a connection to a Yeelight device.
 *
 * YeelightConnection can be used for running commands with {@link #runCommand(YeelightCommand)} or receiving notifications of changed properties with {@link #setNotificationListener(YeelightNotificationListener)}
 */
public class YeelightConnection implements AutoCloseable {
    private static final String THREAD_GROUP_NAME = "Yeelight-Connection-%s:%d";

    private static final Object[] NO_PARAMS = new Object[0];

    private String address;
    private int port;

    private SocketFactory socketFactory;
    private InetAddressFactory inetAddressFactory;

    private Gson gson = new Gson();

    private AtomicBoolean connectionOpen = new AtomicBoolean(false);

    private Socket socket;
    private Thread input;
    private Thread output;
    private ThreadGroup threadGroup;

    private AtomicInteger commandIdCounter = new AtomicInteger();
    private BlockingQueue<Map<String, Object>> commandQueue = new LinkedBlockingQueue<>();
    private CommandResponseRouter commandResponseRouter = new CommandResponseRouter();

    private YeelightNotificationListener notificationListener;

    /**
     * Helper function for connecting to a Yeelight device
     * @param socketFactory Factory for creating socket for the connection
     * @param inetAddressFactory Factory for generating InetAddress for the connection
     * @param yeelightDevice Yeelight device to connect to
     * @param notificationListener Notification listener for receiving notifications of changed properties
     * @return Opened YeelightConnection
     * @throws IOException If an IO error occurs
     */
    @NotNull
    public static YeelightConnection connect(@NotNull SocketFactory socketFactory, @NotNull InetAddressFactory inetAddressFactory, @NotNull YeelightDevice yeelightDevice, @Nullable YeelightNotificationListener notificationListener) throws IOException {
        YeelightConnection connection = new YeelightConnection(socketFactory, inetAddressFactory, yeelightDevice.getAddress(), yeelightDevice.getPort());
        if (notificationListener != null) {
            connection.setNotificationListener(notificationListener);
        }
        connection.open();

        return connection;
    }

    /**
     * Constructs a YeelightConnection that will connect to specified address and port.
     *
     * The connection is not opened initially.
     * @param socketFactory Factory for creating socket for the connection
     * @param inetAddressFactory Factory for generating InetAddress for the connection
     * @param address Address to connect to
     * @param port Port to connect to
     */
    public YeelightConnection(@NotNull SocketFactory socketFactory, @NotNull InetAddressFactory inetAddressFactory, @NotNull String address, int port) {
        this.socketFactory = socketFactory;
        this.inetAddressFactory = inetAddressFactory;
        this.address = address;
        this.port = port;
    }

    /**
     * Opens connection to the Yeelight device
     * @throws IOException If an IO error occurs
     * @throws IllegalStateException If the connection was already opened, see {@link YeelightConnection#isOpen()}
     */
    public void open() throws IOException {
        if (connectionOpen.get()) {
            throw new IllegalStateException("YeelightConnection was already opened");
        }

        socket = socketFactory.create();
        socket.connect(new InetSocketAddress(inetAddressFactory.create(address), port));

        threadGroup = new ThreadGroup(String.format(THREAD_GROUP_NAME, address, port)) {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                if (!connectionOpen.get()) {
                    //Ignore exceptions if the connection was already closed
                    return;
                }

                if (throwable.getCause() instanceof IOException) {
                    if (notificationListener != null) {
                        notificationListener.onError((IOException)throwable.getCause());
                    }

                    commandResponseRouter.sendError(throwable.getCause());

                    return;
                }

                super.uncaughtException(thread, throwable);
            }
        };

        input = new InputThread(threadGroup, socket, gson, notificationListener, commandResponseRouter);
        input.setDaemon(true);

        output = new OutputThread(threadGroup, socket, gson, commandQueue);
        output.setDaemon(true);

        connectionOpen.set(true);

        input.start();
        output.start();
    }

    /**
     * Checks if the connection is open
     * @return true if the connection is open, false otherwise
     */
    public boolean isOpen() {
        return connectionOpen.get();
    }

    /**
     * Closes the connection
     * @throws IOException If an IO error occurs
     */
    @Override
    public void close() throws IOException {
        connectionOpen.set(false);
        socket.close();
        ThreadUtils.quietlyInterruptAndJoin(input);
        ThreadUtils.quietlyInterruptAndJoin(output);
        threadGroup.destroy();
    }

    /**
     * Sends the command to Yeelight device and returns command result
     * @param command Command to send
     * @param <R> Result type
     * @return Command result
     * @throws IOException If an IO error occurs
     * @throws YeelightCommandException If the command was not run successfully
     */
    @Nullable
    public <R> R runCommand(@NotNull YeelightCommand<R> command) throws IOException, YeelightCommandException {
        int commandId = commandIdCounter.getAndIncrement();

        Map<String, Object> commandAsMap = new HashMap<>();
        commandAsMap.put("id", commandId);
        commandAsMap.put("method", command.getMethod());
        //If no parameters are given, use empty array as params as Yeelight protocol requires it
        commandAsMap.put("params", command.getParams() != null ? command.getParams() : NO_PARAMS);

        commandQueue.offer(commandAsMap);

        try {
            CommandResponseRouter.CommandResponse response = commandResponseRouter.waitFor(commandId);

            if (response.exception instanceof IOException) {
                throw (IOException)response.exception;
            }

            if (response.response.containsKey("error")) {
                Map<String, Object> error = (Map)response.response.get("error");
                throw new YeelightCommandException(doubleToInt((double)error.get("code")), (String)error.get("message"));
            }

            return (R)command.responseParser().apply((List) response.response.get("result"));
        } catch (InterruptedException e) {
            //Should not happen
            return null;
        }
    }

    /**
     * Sets a notification listener that will receive notifications when properties of a Yeelight device change
     * @param notificationListener Notification listener
     * @throws IllegalStateException If the connection was already opened
     */
    public void setNotificationListener(@NotNull YeelightNotificationListener notificationListener) {
        if (connectionOpen.get()) {
            throw new IllegalStateException("Cannot set notification listener after the connection is opened");
        }

        this.notificationListener = notificationListener;
    }

    /**
     * Listener for receiving notifications of changed properties of a Yeelight device
     */
    public interface YeelightNotificationListener {
        /**
         * Callback when Yeelight properties have changed
         * @param props Properties that have changed
         */
        void onNotification(@NotNull YeelightProps props);

        /**
         * Callback when Yeelight connection fails due to an IO error
         * @param exception IOException that caused the connection to fail
         */
        void onError(@NotNull IOException exception);
    }
}
