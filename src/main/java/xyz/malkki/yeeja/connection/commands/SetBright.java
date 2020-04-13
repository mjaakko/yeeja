package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetBright extends YeelightCommand<Void> {
    private int brightness;
    private Effect effect;
    private int duration;

    public SetBright(int brightness, @NotNull Effect effect, int duration) {
        this.brightness = brightness;
        this.effect = effect;
        this.duration = duration;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_bright";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { brightness, effect.paramValue, duration };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
