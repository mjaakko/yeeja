package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class Toggle extends YeelightCommand<Void> {
    @Override
    public String getMethod() {
        return "toggle";
    }

    @Override
    public Object[] getParams() {
        return null;
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}