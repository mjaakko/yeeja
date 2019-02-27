package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetDefaultTest {
    private SetDefault setDefault;

    @Before
    public void setup() {
        setDefault = new SetDefault();
    }

    @Test
    public void testMethod() {
        assertEquals("set_default", setDefault.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(null, setDefault.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setDefault.responseParser().apply(Arrays.asList("ok")));
    }
}
