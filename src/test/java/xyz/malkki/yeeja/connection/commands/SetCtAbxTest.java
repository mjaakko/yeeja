package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetCtAbxTest {
    private SetCtAbx setCtAbx;

    @Before
    public void setup() {
        setCtAbx = new SetCtAbx(2000,Effect.SMOOTH, 100);
    }

    @Test
    public void testMethod() {
        assertEquals("set_ct_abx", setCtAbx.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 2000, "smooth", 100 }, setCtAbx.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setCtAbx.responseParser().apply(Arrays.asList("ok")));
    }
}
