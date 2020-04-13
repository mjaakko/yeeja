package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class SetName extends YeelightCommand<Void> {
    private String name;

    public SetName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "set_name";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { name };
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
