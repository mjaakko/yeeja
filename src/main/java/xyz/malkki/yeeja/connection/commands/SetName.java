package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetName extends YeelightCommand<Void> {
    private String name;

    public SetName(String name) {
        this.name = name;
    }

    @Override
    public String getMethod() {
        return "set_name";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { name };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
