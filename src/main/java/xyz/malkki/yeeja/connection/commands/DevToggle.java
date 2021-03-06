package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class DevToggle extends YeelightCommand<Void> {
    @NotNull
    @Override
    public String getMethod() {
        return "dev_toggle";
    }

    @Nullable
    @Override
    public Object[] getParams() {
        return null;
    }

    @NotNull
    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }
}
