package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetRgbTest {
    private SetRgb setRgb;

    @Before
    public void setup() {
        setRgb = new SetRgb(0, Effect.SMOOTH, 100);
    }

    @Test
    public void testMethod() {
        assertEquals("set_rgb", setRgb.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, "smooth", 100 }, setRgb.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setRgb.responseParser().apply(Arrays.asList("ok")));
    }
}
