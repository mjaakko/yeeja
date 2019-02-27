package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetPower extends YeelightCommand<Void> {
    private boolean power;
    private Effect effect;
    private int duration;
    private PowerOnMode powerOnMode;

    public SetPower(boolean power, Effect effect, int duration) {
        this(power, effect, duration, null);
    }

    public SetPower(boolean power, Effect effect, int duration, PowerOnMode powerOnMode) {
        this.power = power;
        this.effect = effect;
        this.duration = duration;
        this.powerOnMode = powerOnMode;
    }

    @Override
    public String getMethod() {
        return "set_power";
    }

    @Override
    public Object[] getParams() {
        return powerOnMode == null ?
            new Object[] { power ? "on" : "off", effect.paramValue, duration } :
            new Object[] { power ? "on" : "off", effect.paramValue, duration, powerOnMode.paramValue };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }

    public enum PowerOnMode {
        NORMAL(0), CT(1), RGB(2), HSV(3), COLOR_FLOW(4), NIGHT_LIGHT(5);

        private final int paramValue;

        PowerOnMode(int paramValue) {
            this.paramValue = paramValue;
        }
    }
}
