package xyz.malkki.yeeja;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class YeelightDiscoveryTest {
    private static final byte[] RESPONSE_DISCOVERY_FULL = String.join(YeelightConstants.LINE_BREAK,  "HTTP/1.1 200 OK",
            "Cache-Control: max-age=3600",
            "Date:",
            "Ext:",
            "Location: yeelight://192.168.1.239:55443",
            "Server: POSIX UPnP/1.0 YGLC/1",
            "id: 0x000000000015243f",
            "model: color",
            "fw_ver: 18",
            "support: get_prop set_default set_power toggle set_bright start_cf stop_cf set_scene cron_add cron_get cron_del set_ct_abx set_rgb",
            "power: on",
            "bright: 100",
            "color_mode: 2",
            "ct: 4000",
            "rgb: 16711680",
            "hue: 100",
            "sat: 35",
            "name: my_bulb",
            "HTTP/1.1 200 OK",
            "Cache-Control: max-age=3600",
            "Date:",
            "Ext:",
            "Location: yeelight://192.168.1.240:55443",
            "Server: POSIX UPnP/1.0 YGLC/1",
            "id: 0x000000000015346f",
            "model: bslamp",
            "fw_ver: 18",
            "support: get_prop set_default set_power toggle set_bright start_cf stop_cf set_scene cron_add cron_get cron_del set_ct_abx set_rgb",
            "power: on",
            "bright: 70",
            "color_mode: 2",
            "ct: 4000",
            "rgb: 16711680",
            "hue: 100",
            "sat: 35",
            "name: my_lamp",
            "").getBytes(StandardCharsets.UTF_8);

    private DatagramSocketFactory mockDatagramSocketFactory;
    private InetAddressFactory mockInetAddressFactory;
    private YeelightDiscovery yeelightDiscovery;

    @Before
    public void setup() {
        mockDatagramSocketFactory = mock(DatagramSocketFactory.class);
        mockInetAddressFactory = mock(InetAddressFactory.class);

        yeelightDiscovery = new YeelightDiscovery(mockDatagramSocketFactory, mockInetAddressFactory);
    }

    @Test
    public void testSetTimeout() throws IOException {
        DatagramSocket mockDatagramSocket = mock(DatagramSocket.class);
        doThrow(new SocketTimeoutException()).when(mockDatagramSocket).receive(any(DatagramPacket.class));

        when(mockDatagramSocketFactory.create()).thenReturn(mockDatagramSocket);

        yeelightDiscovery.discover(1000);

        verify(mockDatagramSocket, times(1)).setSoTimeout(1000);
    }

    @Test
    public void testDiscovery() throws IOException {
        DatagramSocket mockDatagramSocket = mock(DatagramSocket.class);
        doAnswer(new Answer() {
            private int count = 0;

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                DatagramPacket datagramPacket = invocation.getArgument(0);

                if (count * datagramPacket.getLength() >= RESPONSE_DISCOVERY_FULL.length) {
                    throw new SocketTimeoutException();
                }

                byte[] data = Arrays.copyOfRange(RESPONSE_DISCOVERY_FULL, count * datagramPacket.getLength(), Math.min((count + 1) * datagramPacket.getLength(), RESPONSE_DISCOVERY_FULL.length));
                datagramPacket.setData(data);
                datagramPacket.setLength(data.length);

                count++;

                return null;
            }
        }).when(mockDatagramSocket).receive(any(DatagramPacket.class));

        when(mockDatagramSocketFactory.create()).thenReturn(mockDatagramSocket);

        List<YeelightDevice> yeelights = yeelightDiscovery.discover();
        assertEquals(2, yeelights.size());
    }
}
