package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetCtAbx extends YeelightCommand<Void> {
    private int colorTemperature;
    private Effect effect;
    private int duration;

    public SetCtAbx(int colorTemperature, @NotNull Effect effect, int duration) {
        this.colorTemperature = colorTemperature;
        this.effect = effect;
        this.duration = duration;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_ct_abx";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { colorTemperature, effect.paramValue, duration };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
