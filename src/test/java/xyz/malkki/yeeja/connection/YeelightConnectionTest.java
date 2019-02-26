package xyz.malkki.yeeja.connection;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import xyz.malkki.yeeja.connection.commands.YeelightCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class YeelightConnectionTest {
    private static final byte[] ERROR_RESPONSE = "{\"id\":0, \"error\":{\"code\":-1, \"message\":\"unsupported method\"}}\r\n".getBytes(StandardCharsets.UTF_8);
    private static final byte[] COMMAND_RESPONSE = "{\"id\": 0, \"result\":[\"ok\"]}\r\n".getBytes(StandardCharsets.UTF_8);

    @Test
    public void testCanOpenAndCloseConnection() throws IOException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);
        yeelightConnection.open();

        assertTrue(yeelightConnection.isOpen());

        yeelightConnection.close();

        assertFalse(yeelightConnection.isOpen());
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotOpenConnectionTwice() throws IOException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);
        yeelightConnection.open();

        assertTrue(yeelightConnection.isOpen());

        yeelightConnection.open();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotSetNotificationListenerIfAlreadyConnected() throws IOException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);

        yeelightConnection.open();

        assertTrue(yeelightConnection.isOpen());

        yeelightConnection.setNotificationListener(new YeelightConnection.YeelightNotificationListener() {
            @Override
            public void onNotification(YeelightProps props) {

            }

            @Override
            public void onError(IOException exception) {

            }
        });
    }

    @Test
    public void testNotificationListenerReceivesError() throws IOException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(500);
                throw new IOException();
            }
        });
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);

        YeelightConnection.YeelightNotificationListener mockNotificationListener = mock(YeelightConnection.YeelightNotificationListener.class);
        yeelightConnection.setNotificationListener(mockNotificationListener);

        yeelightConnection.open();

        verify(mockNotificationListener, timeout(5000).times(1)).onError(any(IOException.class));
    }

    @Test(expected = IOException.class, timeout = 5000)
    public void testRunCommandThrowsErrorWhenIOErrorOccurs() throws IOException, YeelightCommandException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(invocation -> {
            Thread.sleep(500);
            throw new IOException();
        });
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);
        yeelightConnection.open();

        yeelightConnection.runCommand(new YeelightCommand<Object>() {
            @Override
            public String getMethod() {
                return "fake";
            }

            @Override
            public Object[] getParams() {
                return null;
            }

            @Override
            public Function<List<Object>, Object> responseParser() {
                return map -> null;
            }
        });
    }

    @Test(expected = YeelightCommandException.class, timeout = 5000)
    public void testRunCommandThrowsErrorWhenInvalidCommandIsRun() throws IOException, YeelightCommandException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read(any(byte[].class), anyInt(), anyInt())).thenAnswer(new Answer() {
            int index = 0;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                try {
                    byte[] output = invocation.getArgument(0);
                    output[(int)invocation.getArgument(1)] = ERROR_RESPONSE[index++];
                    return 1;
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new IOException();
                }
            }
        });
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);
        yeelightConnection.open();

        try {
            yeelightConnection.runCommand(new YeelightCommand<Object>() {
                @Override
                public String getMethod() {
                    return "fake";
                }

                @Override
                public Object[] getParams() {
                    return null;
                }

                @Override
                public Function<List<Object>, Object> responseParser() {
                    return map -> null;
                }
            });
        } catch (YeelightCommandException e) {
            assertEquals(-1, e.getCode());
            assertEquals("unsupported method", e.getMessage());

            throw e;
        }
    }

    @Test(timeout = 5000)
    public void testCommandReceivesResponse() throws IOException, YeelightCommandException {
        Socket mockSocket = mock(Socket.class);
        doAnswer(invocation -> {
            when(mockSocket.getPort()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getPort());
            when(mockSocket.getInetAddress()).thenReturn(((InetSocketAddress)invocation.getArgument(0)).getAddress());

            return null;
        }).when(mockSocket).connect(any(InetSocketAddress.class));

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
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);

        OutputStream mockOutputStream = mock(OutputStream.class);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        YeelightConnection yeelightConnection = new YeelightConnection(() -> mockSocket, address -> {
            InetAddress mockInetAddress = mock(InetAddress.class);
            when(mockInetAddress.getHostAddress()).thenReturn(address);

            return mockInetAddress;
        }, "1.1.1.1", 9000);
        yeelightConnection.open();

        List<Object> result = (List) yeelightConnection.runCommand(new YeelightCommand<Object>() {
            @Override
            public String getMethod() {
                return "toggle";
            }

            @Override
            public Object[] getParams() {
                return null;
            }

            @Override
            public Function<List<Object>, Object> responseParser() {
                return map -> map;
            }
        });
        assertEquals(1, result.size());
        assertEquals("ok", result.get(0));
    }
}
