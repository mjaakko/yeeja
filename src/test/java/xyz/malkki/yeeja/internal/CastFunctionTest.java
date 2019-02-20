package xyz.malkki.yeeja.internal;

import org.junit.Test;
import xyz.malkki.yeeja.internal.CastFunction;


import static org.junit.Assert.*;

public class CastFunctionTest {
    @Test
    public void testCanCastIntegerToInteger() {
        CastFunction<Integer> cast = CastFunction.forType(Integer.class);

        int value = 3;
        Object integer = value;

        assertEquals(Integer.valueOf(value), cast.apply(integer));
    }

    @Test(expected = ClassCastException.class)
    public void testCannotCastIntegerToBoolean() {
        CastFunction<Boolean> cast = CastFunction.forType(Boolean.class);

        Object integer = 3;

        cast.apply(integer);
    }
}
