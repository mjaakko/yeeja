package xyz.malkki.yeeja.internal;

import org.junit.Test;
import xyz.malkki.yeeja.internal.MathUtils;

import static org.junit.Assert.*;

public class MathUtilsTest {
    @Test
    public void testDoubleToInt() {
        double d = 1.0;

        assertEquals(1, MathUtils.doubleToInt(d));
    }
}
