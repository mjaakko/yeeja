package xyz.malkki.yeeja;

import xyz.malkki.yeeja.network.DatagramSocketFactory;
import xyz.malkki.yeeja.network.InetAddressFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static xyz.malkki.yeeja.YeelightConstants.*;

public class YeelightDiscovery {
    private static final String DISCOVERY_MESSAGE = String.join(LINE_BREAK, "M-SEARCH * HTTP/1.1", "MAN: \"ssdp:discover\"", "ST: wifi_bulb", "");

    private static final String RESPONSE_DELIMITER = "HTTP/1.1 200 OK".concat(LINE_BREAK);

    private static final int RESPONSE_BUFFER_SIZE = 1024;

    public static final int DISCOVERY_TIMEOUT_MILLIS = 5 * 1000;

    private DatagramSocketFactory datagramSocketFactory;
    private InetAddressFactory inetAddressFactory;

    public YeelightDiscovery(DatagramSocketFactory datagramSocketFactory, InetAddressFactory inetAddressFactory) {
        this.datagramSocketFactory = datagramSocketFactory;
        this.inetAddressFactory = inetAddressFactory;
    }

    public List<YeelightDevice> discover() throws IOException {
        return discover(DISCOVERY_TIMEOUT_MILLIS);
    }

    public List<YeelightDevice> discover(int timeoutMillis) throws IOException {
        try (DatagramSocket ds = datagramSocketFactory.create()) {
            ds.setSoTimeout(timeoutMillis);

            InetSocketAddress inetAddress = new InetSocketAddress(inetAddressFactory.create(YEELIGHT_ADDRESS), YEELIGHT_PORT);
            byte[] msg = DISCOVERY_MESSAGE.getBytes(StandardCharsets.UTF_8);
            DatagramPacket dp = new DatagramPacket(msg, msg.length, inetAddress);
            ds.send(dp);

            StringBuilder response = new StringBuilder();
            try {
                while(true) {
                    DatagramPacket received = new DatagramPacket(new byte[RESPONSE_BUFFER_SIZE], RESPONSE_BUFFER_SIZE);
                    ds.receive(received);

                    String receivedString = new String(received.getData(), 0, received.getLength(), "UTF-8");
                    response.append(receivedString);
                }
            } catch (SocketTimeoutException e) {
                //SocketTimeoutException is expected as it is thrown when the Yeelights have sent their responses
            }
            return YeelightParser.parseResponse(response.toString(), RESPONSE_DELIMITER);
        }
    }
}
