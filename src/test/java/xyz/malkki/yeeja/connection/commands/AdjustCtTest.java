package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AdjustCtTest {
    private AdjustCt adjustCt;

    @Before
    public void setup() {
        adjustCt = new AdjustCt(0,100);
    }

    @Test
    public void testMethod() {
        assertEquals("adjust_ct", adjustCt.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { 0, 100 }, adjustCt.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(adjustCt.responseParser().apply(Arrays.asList("ok")));
    }
}
