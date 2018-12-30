package xyz.malkki.yeeja;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DefaultInetAddressFactory implements InetAddressFactory {
    @Override
    public InetAddress create(String host) throws UnknownHostException {
        return InetAddress.getByName(host);
    }
}
