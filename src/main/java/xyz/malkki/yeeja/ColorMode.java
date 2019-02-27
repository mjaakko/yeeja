package xyz.malkki.yeeja;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Describes a color mode
 */
public enum ColorMode {
    /**
     * In COLOR mode, color is controlled with RGB values
     */
    COLOR(1),
    /**
     * In COLOR_TEMPERATURE mode, color is controlled with color temperature values
     */
    COLOR_TEMPERATURE(2),
    /**
     * In HSV mode, color is controlled with hue and saturation values
     */
    HSV(3);

    private int mode;

    ColorMode(int mode) {
        this.mode = mode;
    }

    private static Map<Integer, ColorMode> BY_MODE_INT = Arrays.stream(ColorMode.values()).collect(Collectors.toMap(cm -> cm.mode, Function.identity()));

    public static ColorMode findByMode(int mode) {
        return BY_MODE_INT.get(mode);
    }
}