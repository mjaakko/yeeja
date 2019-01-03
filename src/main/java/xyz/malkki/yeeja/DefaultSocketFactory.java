package xyz.malkki.yeeja;

import java.net.Socket;

public class DefaultSocketFactory implements SocketFactory {
    @Override
    public Socket create() {
        return new Socket();
    }
}
