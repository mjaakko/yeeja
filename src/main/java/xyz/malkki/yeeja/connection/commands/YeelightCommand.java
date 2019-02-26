package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public abstract class YeelightCommand<R> {
    public abstract String getMethod();
    public abstract Object[] getParams();

    public abstract Function<List<Object>, R> responseParser();
}
