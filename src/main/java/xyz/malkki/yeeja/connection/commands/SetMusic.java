package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetMusic extends YeelightCommand<Void> {
    private boolean music;
    private String host;
    private int port;

    public SetMusic(boolean music, String host, int port) {
        this.music = music;
        this.host = host;
        this.port = port;
    }

    @Override
    public String getMethod() {
        return "set_music";
    }

    @Override
    public Object[] getParams() {
        return music ?
            new Object[] { 1, host, port } :
            new Object[] { 0 };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
