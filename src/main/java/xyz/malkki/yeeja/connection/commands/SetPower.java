package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetPower extends YeelightCommand<Void> {
    private boolean power;
    private Effect effect;
    private int duration;
    private PowerOnMode powerOnMode;

    public SetPower(boolean power, @NotNull Effect effect, int duration) {
        this.power = power;
        this.effect = effect;
        this.duration = duration;
    }

    public SetPower(boolean power, @NotNull Effect effect, int duration, @NotNull PowerOnMode powerOnMode) {
        this(power, effect, duration);
        this.powerOnMode = powerOnMode;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_power";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return powerOnMode == null ?
            new Object[] { power ? "on" : "off", effect.paramValue, duration } :
            new Object[] { power ? "on" : "off", effect.paramValue, duration, powerOnMode.paramValue };
    }

    @NotNull
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
