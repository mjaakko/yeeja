package xyz.malkki.yeeja.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface InetAddressFactory {
    InetAddress create(String host) throws UnknownHostException;
}
