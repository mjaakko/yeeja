package xyz.malkki.yeeja.utils;

import java.util.function.Function;

public class CastFunction<T> implements Function<Object, T> {
    private Class<T> type;

    public CastFunction(Class<T> type) {
        this.type = type;
    }

    @Override
    public T apply(Object o) {
        return type.cast(o);
    }

    public static <T> CastFunction<T> forType(Class<T> type) {
        return new CastFunction<>(type);
    }
}
