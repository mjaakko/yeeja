package xyz.malkki.yeeja.connection;

import java.util.Objects;
import java.util.function.Function;

public class YeelightPropMeta<T> implements Function<Object, T> {
    private String name;
    private Function<Object, T> valueParser;

    private YeelightPropMeta(String name, Function<Object, T> valueParser) {
        this.name = name;
        this.valueParser = valueParser;
    }

    public String name() {
        return name;
    }

    @Override
    public T apply(Object o) {
        return valueParser.apply(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YeelightPropMeta<?> that = (YeelightPropMeta<?>) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static <T> YeelightPropMeta create(String name, Function<Object, T> valueParser) {
        return new YeelightPropMeta(name, valueParser);
    }
}
