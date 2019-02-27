package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StopCfTest {
    private StopCf stopCf;

    @Before
    public void setup() {
        stopCf = new StopCf();
    }

    @Test
    public void testMethod() {
        assertEquals("stop_cf", stopCf.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(null, stopCf.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(stopCf.responseParser().apply(Arrays.asList("ok")));
    }
}
