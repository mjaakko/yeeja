package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class CronAdd extends YeelightCommand<Void> {
    private int type;
    private int timeInMinutes;

    public CronAdd(int type, int timeInMinutes) {
        this.type = type;
        this.timeInMinutes = timeInMinutes;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "cron_add";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { type, timeInMinutes };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
