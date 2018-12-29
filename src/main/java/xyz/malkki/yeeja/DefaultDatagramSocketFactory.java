package xyz.malkki.yeeja;

import java.net.DatagramSocket;
import java.net.SocketException;

public class DefaultDatagramSocketFactory implements DatagramSocketFactory {
    @Override
    public DatagramSocket create() throws SocketException {
        return new DatagramSocket();
    }
}
