package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class CronDel extends YeelightCommand<Void> {
    private int type;

    public CronDel(int type) {
        this.type = type;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "cron_del";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { type };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
