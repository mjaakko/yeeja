package xyz.malkki.yeeja.connection.internal;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import xyz.malkki.yeeja.YeelightConstants;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class OutputThread extends Thread {
    private static final String THREAD_NAME_FORMAT = "Yeelight-Output-%s:%d";

    private Gson gson;
    private Socket socket;

    private BlockingQueue<Map<String, Object>> commandQueue;

    public OutputThread(@NotNull ThreadGroup threadGroup, @NotNull Socket socket, @NotNull Gson gson, @NotNull BlockingQueue<Map<String, Object>> commandQueue) {
        super(threadGroup, String.format(THREAD_NAME_FORMAT, socket.getInetAddress().getHostAddress(), socket.getPort()));

        this.socket = socket;
        this.gson = gson;
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Map<String, Object> commandAsMap = commandQueue.take();

                String command = gson.toJson(commandAsMap) + YeelightConstants.LINE_BREAK;
                socket.getOutputStream().write(command.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException ioe) {
            //Wrap exceptions RuntimeExceptions so the thread will be stopped and the exception can be caught with UncaughtExceptionHandler outside the thread
            throw new RuntimeException(ioe);
        } catch (InterruptedException ie) {
            //Thread will only be interrupted when the connection is closed and thus we don't care about this exception
        }
    }
}
