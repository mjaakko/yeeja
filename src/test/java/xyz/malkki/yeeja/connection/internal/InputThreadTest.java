package xyz.malkki.yeeja.connection.internal;

import com.google.gson.Gson;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import xyz.malkki.yeeja.connection.YeelightConnection;
import xyz.malkki.yeeja.connection.YeelightProps;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InputThreadTest {
    private static final byte[] NOTIFICATION = "{\"method\":\"props\",\"params\":{\"power\":\"on\", \"bright\": 10.0}}\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] COMMAND_RESPONSE = "{\"id\": 1, \"result\":[\"ok\"]}\r\n".getBytes(StandardCharsets.UTF_8);

    @Test
    public void testThreadName() {
        Socket socket = mock(Socket.class);
        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);
        when(socket.getPort()).thenReturn(9000);

        YeelightConnection.YeelightNotificationListener mockNotificationListener = mock(YeelightConnection.YeelightNotificationListener.class);
        CommandResponseRouter mockResponseRouter = mock(CommandResponseRouter.class);

        InputThread inputThread = new InputThread(new ThreadGroup("test"), socket, new Gson(), mockNotificationListener, mockResponseRouter);
        assertEquals("Yeelight-Input-1.1.1.1:9000",  inputThread.getName());
    }

    @Test(timeout = 2000)
    public void testNotificationListenerReceivesNotifications() throws IOException, InterruptedException {
        Socket socket = mock(Socket.class);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(new Answer() {
            int index = 0;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                try {
                    byte[] output = invocation.getArgument(0);
                    output[(int)invocation.getArgument(1)] = NOTIFICATION[index++];
                    return 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IOException();
                }
            }
        });
        when(socket.getInputStream()).thenReturn(mockInputStream);

        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);

        when(socket.getPort()).thenReturn(9000);

        YeelightConnection.YeelightNotificationListener mockNotificationListener = mock(YeelightConnection.YeelightNotificationListener.class);

        CommandResponseRouter mockResponseRouter = mock(CommandResponseRouter.class);

        List<Throwable> exceptions = new ArrayList<>();

        ThreadGroup threadGroup = new ThreadGroup("test_thread_group") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                exceptions.add(e);
            }
        };
        InputThread inputThread = new InputThread(threadGroup, socket, new Gson(), mockNotificationListener, mockResponseRouter);
        inputThread.start();
        inputThread.join();

        Map<String, Object> props = new HashMap<>();
        props.put("power", true);
        props.put("bright", 10);

        //Verify that 'onNotification' is called once with correct value
        verify(mockNotificationListener, times(1)).onNotification(new YeelightProps(props));

        //Verify that command response router does not receive the notification
        verifyZeroInteractions(mockResponseRouter);

        //Assert that one IOException was caught
        assertEquals(1, exceptions.size());
        Throwable throwable = exceptions.get(0);
        assertTrue(throwable instanceof RuntimeException);
        assertTrue(throwable.getCause() instanceof IOException);
    }

    @Test(timeout = 2000)
    public void testCommandResponseRouterReceivesResponse() throws IOException, InterruptedException {
        Socket socket = mock(Socket.class);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(new Answer() {
            int index = 0;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                try {
                    byte[] output = invocation.getArgument(0);
                    output[(int)invocation.getArgument(1)] = COMMAND_RESPONSE[index++];
                    return 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IOException();
                }
            }
        });
        when(socket.getInputStream()).thenReturn(mockInputStream);

        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);

        when(socket.getPort()).thenReturn(9000);

        YeelightConnection.YeelightNotificationListener mockNotificationListener = mock(YeelightConnection.YeelightNotificationListener.class);

        CommandResponseRouter mockResponseRouter = mock(CommandResponseRouter.class);

        List<Throwable> exceptions = new ArrayList<>();

        ThreadGroup threadGroup = new ThreadGroup("test_thread_group") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                exceptions.add(e);
            }
        };
        InputThread inputThread = new InputThread(threadGroup, socket, new Gson(), mockNotificationListener, mockResponseRouter);
        inputThread.start();
        inputThread.join();

        //Verify that command response router receives correct response
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1.0);
        ArrayList<String> result = new ArrayList<>();
        result.add("ok");
        map.put("result", result);
        verify(mockResponseRouter, times(1)).sendResponse(1, map);

        //Verify notification listener does not receive the command response
        verifyZeroInteractions(mockNotificationListener);

        //Assert that one IOException was caught
        assertEquals(1, exceptions.size());
        Throwable throwable = exceptions.get(0);
        assertTrue(throwable instanceof RuntimeException);
        assertTrue(throwable.getCause() instanceof IOException);
    }
}
