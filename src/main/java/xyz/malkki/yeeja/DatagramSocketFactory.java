package xyz.malkki.yeeja;

import java.net.DatagramSocket;
import java.net.SocketException;

public interface DatagramSocketFactory {
    DatagramSocket create() throws SocketException;
}
