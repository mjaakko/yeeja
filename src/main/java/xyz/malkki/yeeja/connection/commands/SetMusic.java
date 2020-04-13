package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetMusic extends YeelightCommand<Void> {
    private boolean music;
    private String host;
    private int port;

    public SetMusic(boolean music, @NotNull String host, int port) {
        this.music = music;
        this.host = host;
        this.port = port;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_music";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return music ?
            new Object[] { 1, host, port } :
            new Object[] { 0 };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
