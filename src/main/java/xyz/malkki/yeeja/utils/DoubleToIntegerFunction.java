package xyz.malkki.yeeja.utils;

import java.util.function.Function;

import static xyz.malkki.yeeja.utils.MathUtils.doubleToInt;

public class DoubleToIntegerFunction implements Function<Double, Integer> {
    private DoubleToIntegerFunction() {}

    @Override
    public Integer apply(Double d) {
        return doubleToInt(d);
    }

    public static final DoubleToIntegerFunction INSTANCE = new DoubleToIntegerFunction();
}
