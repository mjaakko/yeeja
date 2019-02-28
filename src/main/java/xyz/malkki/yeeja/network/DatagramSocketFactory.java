package xyz.malkki.yeeja.network;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Interface for creating datagram sockets
 */
public interface DatagramSocketFactory {
    /**
     * Implementations of this method should return a new datagram socket
     * @return A new datagram socket
     * @throws SocketException If the socket creation fails for some reason
     */
    DatagramSocket create() throws SocketException;
}
