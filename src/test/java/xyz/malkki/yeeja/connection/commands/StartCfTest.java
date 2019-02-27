package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StartCfTest {
    private StartCf startCf;

    @Before
    public void setup() {
        startCf = new StartCf(1, StartCf.ColorFlowStopAction.TURN_OFF, "100, 1, 0, 100");
    }

    @Test
    public void testMethod() {
        assertEquals("start_cf", startCf.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 1, 2, "100, 1, 0, 100" }, startCf.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(startCf.responseParser().apply(Arrays.asList("ok")));
    }
}
