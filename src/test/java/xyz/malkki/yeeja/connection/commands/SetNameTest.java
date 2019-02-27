package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetNameTest {
    private SetName setName;

    @Before
    public void setup() {
        setName = new SetName("test");
    }

    @Test
    public void testMethod() {
        assertEquals("set_name", setName.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { "test" }, setName.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setName.responseParser().apply(Arrays.asList("ok")));
    }
}
