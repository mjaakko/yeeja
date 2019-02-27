package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class CronDel extends YeelightCommand<Void> {
    private int type;

    public CronDel(int type) {
        this.type = type;
    }

    @Override
    public String getMethod() {
        return "cron_del";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { type };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
