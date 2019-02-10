package xyz.malkki.yeeja.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathUtilsTest {
    @Test
    public void testDoubleToInt() {
        double d = 1.0;

        assertEquals(1, MathUtils.doubleToInt(d));
    }
}
