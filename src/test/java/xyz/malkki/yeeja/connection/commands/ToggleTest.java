package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ToggleTest {
    private Toggle toggle;

    @Before
    public void setup() {
        toggle = new Toggle();
    }

    @Test
    public void testMethod() {
        assertEquals("toggle", toggle.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(null, toggle.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(toggle.responseParser().apply(Arrays.asList("ok")));
    }
}
