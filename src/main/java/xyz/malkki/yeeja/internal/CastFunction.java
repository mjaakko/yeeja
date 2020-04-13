package xyz.malkki.yeeja.internal;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class CastFunction<T> implements Function<Object, T> {
    private Class<T> type;

    public CastFunction(@NotNull Class<T> type) {
        this.type = type;
    }

    @NotNull
    @Override
    public T apply(@NotNull Object o) {
        return type.cast(o);
    }

    @NotNull
    public static <T> CastFunction<T> forType(@NotNull Class<T> type) {
        return new CastFunction<>(type);
    }
}
