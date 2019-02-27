package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetRgb extends YeelightCommand<Void> {
    private int rgb;
    private Effect effect;
    private int duration;

    public SetRgb(int rgb, Effect effect, int duration) {
        this.rgb = rgb;
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public String getMethod() {
        return "set_rgb";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { rgb, effect.paramValue, duration };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
