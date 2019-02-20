package xyz.malkki.yeeja.internal;

public class MathUtils {
    private MathUtils() {}

    /**
     * Converts a double value to an int value by rounding
     * @param d
     * @return
     */
    public static int doubleToInt(double d) {
        return (int)Math.round(d);
    }
}
