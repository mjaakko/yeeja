package xyz.malkki.yeeja.connection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a property type that Yeelight can have
 * @param <T> Type of property value
 */
public class YeelightPropMeta<T> implements Function<Object, T> {
    private String name;
    private Function<Object, T> valueParser;

    private YeelightPropMeta(@NotNull String name, @NotNull Function<Object, T> valueParser) {
        this.name = name;
        this.valueParser = valueParser;
    }

    /**
     * Name of the property, i.e. property key
     * @return Property key
     */
    @NotNull
    public String name() {
        return name;
    }

    @Override
    @Nullable
    public T apply(@Nullable Object o) {
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

    @NotNull
    public static <T> YeelightPropMeta create(@NotNull String name, @NotNull Function<Object, T> valueParser) {
        return new YeelightPropMeta(name, valueParser);
    }
}
