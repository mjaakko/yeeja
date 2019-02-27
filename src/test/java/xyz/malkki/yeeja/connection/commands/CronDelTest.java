package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CronDelTest {
    private CronDel cronDel;

    @Before
    public void setup() {
        cronDel = new CronDel(0);
    }

    @Test
    public void testMethod() {
        assertEquals("cron_del", cronDel.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[]{ 0 }, cronDel.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(cronDel.responseParser().apply(Arrays.asList("ok")));
    }
}
