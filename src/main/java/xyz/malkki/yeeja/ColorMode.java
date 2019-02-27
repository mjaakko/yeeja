package xyz.malkki.yeeja;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ColorMode {
    COLOR(1), COLOR_TEMPERATURE(2), HSV(3);

    private int mode;

    ColorMode(int mode) {
        this.mode = mode;
    }

    private static Map<Integer, ColorMode> BY_MODE_INT = Arrays.stream(ColorMode.values()).collect(Collectors.toMap(cm -> cm.mode, Function.identity()));

    public static ColorMode findByMode(int mode) {
        return BY_MODE_INT.get(mode);
    }
}