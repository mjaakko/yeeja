package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AdjustColorTest {
    private AdjustColor adjustColor;

    @Before
    public void setup() {
        adjustColor = new AdjustColor(0,100);
    }

    @Test
    public void testMethod() {
        assertEquals("adjust_color", adjustColor.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, 100 }, adjustColor.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(adjustColor.responseParser().apply(Arrays.asList("ok")));
    }
}
