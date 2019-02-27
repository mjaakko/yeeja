package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetPowerTest {
    private SetPower setPowerWithMode;
    private SetPower setPowerWithoutMode;

    @Before
    public void setup() {
        setPowerWithMode = new SetPower(true, Effect.SMOOTH, 100, SetPower.PowerOnMode.RGB);
        setPowerWithoutMode = new SetPower(true, Effect.SMOOTH, 100);
    }

    @Test
    public void testMethod() {
        assertEquals("set_power", setPowerWithMode.getMethod());
    }

    @Test
    public void testParamsWithoutMode() {
        assertArrayEquals(new Object[] { "on", "smooth", 100 }, setPowerWithoutMode.getParams());
    }

    @Test
    public void testParamsWithMode() {
        assertArrayEquals(new Object[] { "on", "smooth", 100, 2 }, setPowerWithMode.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setPowerWithoutMode.responseParser().apply(Arrays.asList("ok")));
    }
}
