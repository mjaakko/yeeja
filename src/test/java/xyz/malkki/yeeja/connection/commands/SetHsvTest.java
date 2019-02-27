package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetHsvTest {
    private SetHsv setHsv;

    @Before
    public void setup() {
        setHsv = new SetHsv(0, 0, Effect.SMOOTH, 100);
    }

    @Test
    public void testMethod() {
        assertEquals("set_hsv", setHsv.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, 0, "smooth", 100 }, setHsv.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setHsv.responseParser().apply(Arrays.asList("ok")));
    }
}
