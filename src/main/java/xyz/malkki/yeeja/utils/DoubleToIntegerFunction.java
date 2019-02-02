package xyz.malkki.yeeja.utils;

import java.util.function.Function;

public class DoubleToIntegerFunction implements Function<Double, Integer> {
    private DoubleToIntegerFunction() {}

    @Override
    public Integer apply(Double d) {
        return (int)Math.round(d);
    }

    public static final DoubleToIntegerFunction INSTANCE = new DoubleToIntegerFunction();
}
