package xyz.malkki.yeeja.connection.internal;

import com.google.gson.Gson;
import xyz.malkki.yeeja.connection.YeelightConnection;
import xyz.malkki.yeeja.connection.YeelightProps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static xyz.malkki.yeeja.internal.MathUtils.doubleToInt;

public class InputThread extends Thread {
    private static final String THREAD_NAME_FORMAT = "Yeelight-Input-%s:%d";

    private Gson gson;
    private Socket socket;
    private YeelightConnection.YeelightNotificationListener notificationListener;
    private CommandResponseRouter responseRouter;

    public InputThread(ThreadGroup threadGroup, Socket socket, Gson gson, YeelightConnection.YeelightNotificationListener notificationListener, CommandResponseRouter responseRouter) {
        super(threadGroup, String.format(THREAD_NAME_FORMAT, socket.getInetAddress().getHostAddress(), socket.getPort()));

        this.socket = socket;
        this.gson = gson;
        this.notificationListener = notificationListener;
        this.responseRouter = responseRouter;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            char prev = 0;

            StringBuilder sb = new StringBuilder();

            while (true) {
                char c = (char) reader.read();

                sb.append(c);
                if (prev == '\r' && c == '\n') {
                    Map<String, Object> map = gson.fromJson(sb.toString(), Map.class);
                    if (notificationListener != null && isNotification(map)) {
                            notificationListener.onNotification(YeelightProps.parseKnownPropsFromMap((Map)map.get("params")));
                    } else if (isCommandResponse(map)) {
                        int id = doubleToInt((double)map.get("id"));
                        responseRouter.sendResponse(id, map);
                    }

                    sb = new StringBuilder();
                }
                prev = c;
            }
        } catch (IOException ioe) {
            //Wrap exceptions RuntimeExceptions so the thread will be stopped and the exception can be caught with UncaughtExceptionHandler outside the thread
            throw new RuntimeException(ioe);
        }
    }

    private static boolean isNotification(Map<String, Object> map) {
        return map.containsKey("method");
    }

    private static boolean isCommandResponse(Map<String, Object> map) {
        return map.containsKey("id");
    }
}
