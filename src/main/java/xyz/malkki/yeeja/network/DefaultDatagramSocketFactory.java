package xyz.malkki.yeeja.network;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * DefaultDatagramSocketFactory creates datagram sockets by simply calling <pre><code>new DatagramSocket()</code></pre>
 */
public class DefaultDatagramSocketFactory implements DatagramSocketFactory {
    /**
     * Creates a new datagram socket
     * @return A new datagram socket
     * @throws SocketException If the socket creation fails for some reason
     */
    @Override
    public DatagramSocket create() throws SocketException {
        return new DatagramSocket();
    }
}
