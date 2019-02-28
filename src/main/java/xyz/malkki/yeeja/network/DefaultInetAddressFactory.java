package xyz.malkki.yeeja.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * DefaultInetAddressFactory creates InetAddresses by calling <pre><code>InetAddress.getByName(host)</code></pre>
 */
public class DefaultInetAddressFactory implements InetAddressFactory {
    /**
     * Creates an InetAddress for the specified host
     * @param host Host
     * @return An InetAddress for the specified host
     * @throws UnknownHostException If no IP address is found for the host
     */
    @Override
    public InetAddress create(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }
}
