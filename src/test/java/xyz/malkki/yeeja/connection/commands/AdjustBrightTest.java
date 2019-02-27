package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AdjustBrightTest {
    private AdjustBright adjustBright;

    @Before
    public void setup() {
        adjustBright = new AdjustBright(0,100);
    }

    @Test
    public void testMethod() {
        assertEquals("adjust_bright", adjustBright.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, 100 }, adjustBright.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(adjustBright.responseParser().apply(Arrays.asList("ok")));
    }
}
