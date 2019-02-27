package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetBright extends YeelightCommand<Void> {
    private int brightness;
    private Effect effect;
    private int duration;

    public SetBright(int brightness, Effect effect, int duration) {
        this.brightness = brightness;
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public String getMethod() {
        return "set_bright";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { brightness, effect.paramValue, duration };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
