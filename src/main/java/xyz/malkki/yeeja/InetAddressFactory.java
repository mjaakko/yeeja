package xyz.malkki.yeeja;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface InetAddressFactory {
    InetAddress create(String host) throws UnknownHostException;
}
