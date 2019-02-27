package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class AdjustBright extends YeelightCommand<Void> {
    private int percentage;
    private int duration;

    public AdjustBright(int percentage, int duration) {
        this.percentage = percentage;
        this.duration = duration;
    }

    @Override
    public String getMethod() {
        return "adjust_bright";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { percentage, duration };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
