package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetBrightTest {
    private SetBright setBright;

    @Before
    public void setup() {
        setBright = new SetBright(0, Effect.SMOOTH, 100);
    }

    @Test
    public void testMethod() {
        assertEquals("set_bright", setBright.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, "smooth", 100 }, setBright.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setBright.responseParser().apply(Arrays.asList("ok")));
    }
}
