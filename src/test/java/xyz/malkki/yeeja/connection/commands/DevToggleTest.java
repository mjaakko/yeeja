package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DevToggleTest {
    private DevToggle devToggle;

    @Before
    public void setup() {
        devToggle = new DevToggle();
    }

    @Test
    public void testMethod() {
        assertEquals("dev_toggle", devToggle.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(null, devToggle.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(devToggle.responseParser().apply(Arrays.asList("ok")));
    }
}
