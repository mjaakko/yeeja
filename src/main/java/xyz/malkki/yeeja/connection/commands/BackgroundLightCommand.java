package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class BackgroundLightCommand<R> extends YeelightCommand<R> {
    private YeelightCommand<R> command;

    /**
     * Creates command that will control background light of a Yeelight device.
     * Note that not all commands can be used with background lights.
     * @param command Command that will be used for controlling background light
     */
    public BackgroundLightCommand(@NotNull YeelightCommand<R> command) {
        this.command = command;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "bg_"+command.getMethod();
    }

    @Nullable
    @Override
    public Object[] getParams() {
        return command.getParams();
    }

    @NotNull
    @Override
    public Function<List<Object>, R> responseParser() {
        return command.responseParser();
    }
}
