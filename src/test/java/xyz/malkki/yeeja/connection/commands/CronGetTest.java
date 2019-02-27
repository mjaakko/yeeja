package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CronGetTest {
    private CronGet cronGet;

    @Before
    public void setup() {
        cronGet = new CronGet(0);
    }

    @Test
    public void testMethod() {
        assertEquals("cron_get", cronGet.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[]{ 0 }, cronGet.getParams());
    }

    @Test
    public void testResponseParser() {
        assertEquals(new CronGet.CronGetResponse(0, 15, 0), cronGet.responseParser().apply(Arrays.asList("{\"type\": 0, \"delay\": 15, \"mix\": 0}")));
    }
}
