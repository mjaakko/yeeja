package xyz.malkki.yeeja.network;

import java.net.Socket;

/**
 * Interface for creating sockets
 */
public interface SocketFactory {
    /**
     * Implementations of this method should return a new unconnected socket
     * @return A new unconnected socket
     */
    Socket create();
}
