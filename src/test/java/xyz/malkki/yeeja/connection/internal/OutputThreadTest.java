package xyz.malkki.yeeja.connection.internal;

import com.google.gson.Gson;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OutputThreadTest {
    @Test
    public void testThreadName() {
        Socket socket = mock(Socket.class);
        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);
        when(socket.getPort()).thenReturn(9000);

        OutputThread outputThread = new OutputThread(new ThreadGroup("test"), socket, new Gson(), new LinkedBlockingQueue<>());
        assertEquals("Yeelight-Output-1.1.1.1:9000", outputThread.getName());
    }

    @Test
    public void testCommandIsWrittenToOutputStream() throws IOException {
        Socket socket = mock(Socket.class);

        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);

        when(socket.getPort()).thenReturn(9000);

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(socket.getOutputStream()).thenReturn(mockOutputStream);

        BlockingQueue<Map<String, Object>> commandQueue = new LinkedBlockingQueue<>();

        List<Throwable> exceptions = new ArrayList<>();
        ThreadGroup threadGroup = new ThreadGroup("test_thread_group") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                exceptions.add(e);
            }
        };
        OutputThread outputThread = new OutputThread(threadGroup, socket, new Gson(), commandQueue);
        outputThread.start();

        Map<String, Object> command = new HashMap<>();
        command.put("id", 1);
        command.put("method", "toggle");
        command.put("params", new Object[0]);
        commandQueue.offer(command);

        String commandAsString = new Gson().toJson(command) + "\r\n";

        //Verify that command is written to OutputStream
        verify(mockOutputStream, timeout(2000).times(1)).write(commandAsString.getBytes(StandardCharsets.UTF_8));

        //Assert that no exception was caught
        assertEquals(0, exceptions.size());
    }

    @Test
    public void testIOExceptionIsCaughtIfCommandCannotBeWritten() throws IOException {
        Socket socket = mock(Socket.class);

        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);

        when(socket.getPort()).thenReturn(9000);

        OutputStream mockOutputStream = mock(OutputStream.class);
        doThrow(new IOException()).when(mockOutputStream).write(any(byte[].class));
        when(socket.getOutputStream()).thenReturn(mockOutputStream);

        BlockingQueue<Map<String, Object>> commandQueue = new LinkedBlockingQueue<>();

        List<Throwable> exceptions = new ArrayList<>();
        ThreadGroup threadGroup = new ThreadGroup("test_thread_group") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                exceptions.add(e);
            }
        };
        OutputThread outputThread = new OutputThread(threadGroup, socket, new Gson(), commandQueue);
        outputThread.start();

        Map<String, Object> command = new HashMap<>();
        command.put("id", 1);
        command.put("method", "toggle");
        command.put("params", new Object[0]);
        commandQueue.offer(command);

        String commandAsString = new Gson().toJson(command) + "\r\n";

        //Verify that thread tries to write command to OutputStream
        verify(mockOutputStream, timeout(2000).times(1)).write(commandAsString.getBytes(StandardCharsets.UTF_8));

        //Assert that exception is caught
        assertEquals(1, exceptions.size());
        Throwable exception = exceptions.get(0);
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception.getCause() instanceof IOException);
    }

    @Test(timeout = 2000)
    public void testThatThreadCanBeStoppedByInterrupting() throws IOException, InterruptedException {
        Socket socket = mock(Socket.class);

        InetAddress mockAddress = mock(InetAddress.class);
        when(mockAddress.getHostAddress()).thenReturn("1.1.1.1");
        when(socket.getInetAddress()).thenReturn(mockAddress);

        when(socket.getPort()).thenReturn(9000);

        OutputStream mockOutputStream = mock(OutputStream.class);
        doThrow(new IOException()).when(mockOutputStream).write(any(byte[].class));
        when(socket.getOutputStream()).thenReturn(mockOutputStream);

        OutputThread outputThread = new OutputThread(new ThreadGroup("test"), socket, new Gson(), new LinkedBlockingQueue<>());
        outputThread.start();

        assertTrue(outputThread.isAlive());

        outputThread.interrupt();
        outputThread.join(500);

        assertFalse(outputThread.isAlive());
    }
}
