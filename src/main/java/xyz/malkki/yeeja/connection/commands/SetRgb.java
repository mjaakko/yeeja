package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetRgb extends YeelightCommand<Void> {
    private int rgb;
    private Effect effect;
    private int duration;

    public SetRgb(int rgb, @NotNull Effect effect, int duration) {
        this.rgb = rgb;
        this.effect = effect;
        this.duration = duration;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_rgb";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { rgb, effect.paramValue, duration };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
