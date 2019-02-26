package xyz.malkki.yeeja.connection;

import com.google.gson.Gson;
import xyz.malkki.yeeja.YeelightDevice;
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

    public static YeelightConnection connect(SocketFactory socketFactory, InetAddressFactory inetAddressFactory, YeelightDevice yeelightDevice, YeelightNotificationListener notificationListener) throws IOException {
        YeelightConnection connection = new YeelightConnection(socketFactory, inetAddressFactory, yeelightDevice.getAddress(), yeelightDevice.getPort());
        connection.setNotificationListener(notificationListener);
        connection.open();

        return connection;
    }

    public YeelightConnection(SocketFactory socketFactory, InetAddressFactory inetAddressFactory, String address, int port) {
        this.socketFactory = socketFactory;
        this.inetAddressFactory = inetAddressFactory;
        this.address = address;
        this.port = port;
    }

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

    public boolean isOpen() {
        return connectionOpen.get();
    }

    @Override
    public void close() throws IOException {
        connectionOpen.set(false);
        socket.close();
        ThreadUtils.quietlyInterruptAndJoin(input);
        ThreadUtils.quietlyInterruptAndJoin(output);
        threadGroup.destroy();
    }

    public <R> R runCommand(YeelightCommand<R> command) throws IOException, YeelightCommandException {
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

    public void setNotificationListener(YeelightNotificationListener notificationListener) {
        if (connectionOpen.get()) {
            throw new IllegalStateException("Cannot set notification listener after the connection is opened");
        }

        this.notificationListener = notificationListener;
    }

    public interface YeelightNotificationListener {
        void onNotification(YeelightProps props);
        void onError(IOException exception);
    }
}
