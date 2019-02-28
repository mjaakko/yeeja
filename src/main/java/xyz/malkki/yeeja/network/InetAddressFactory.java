package xyz.malkki.yeeja.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Interface for creating InetAddress for specified host
 */
public interface InetAddressFactory {
    /**
     * Implementations of this method should
     * @param host Host
     * @return An InetAddress for the specified host
     * @throws UnknownHostException If no IP address is found for the host
     */
    InetAddress create(String host) throws UnknownHostException;
}
