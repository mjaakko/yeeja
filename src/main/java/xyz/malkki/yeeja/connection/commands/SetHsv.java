package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetHsv extends YeelightCommand<Void> {
    private int hue;
    private int saturation;
    private Effect effect;
    private int duration;

    public SetHsv(int hue, int saturation, @NotNull Effect effect, int duration) {
        this.hue = hue;
        this.saturation = saturation;
        this.effect = effect;
        this.duration = duration;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_hsv";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { hue, saturation, effect.paramValue, duration };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
