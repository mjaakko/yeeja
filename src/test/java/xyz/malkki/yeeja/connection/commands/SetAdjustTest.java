package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetAdjustTest {
    private SetAdjust setAdjust;

    @Before
    public void setup() {
        setAdjust = new SetAdjust(SetAdjust.Action.CIRCLE, SetAdjust.Prop.COLOR);
    }

    @Test
    public void testMethod() {
        assertEquals("set_adjust", setAdjust.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { "circle", "color" }, setAdjust.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setAdjust.responseParser().apply(Arrays.asList("ok")));
    }
}
