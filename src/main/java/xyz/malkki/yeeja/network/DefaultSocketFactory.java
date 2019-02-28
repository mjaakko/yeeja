package xyz.malkki.yeeja.network;

import java.net.Socket;

/**
 * DefaultSocketFactory creates sockets by simply calling <pre><code>new Socket()</code></pre>
 */
public class DefaultSocketFactory implements SocketFactory {
    /**
     * Creates a new unconnected socket
     * @return A new unconnected socket
     */
    @Override
    public Socket create() {
        return new Socket();
    }
}
