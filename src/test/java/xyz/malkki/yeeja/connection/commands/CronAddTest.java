package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CronAddTest {
    private CronAdd cronAdd;

    @Before
    public void setup() {
        cronAdd = new CronAdd(0,15);
    }

    @Test
    public void testMethod() {
        assertEquals("cron_add", cronAdd.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[]{ 0, 15 }, cronAdd.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(cronAdd.responseParser().apply(Arrays.asList("ok")));
    }
}
