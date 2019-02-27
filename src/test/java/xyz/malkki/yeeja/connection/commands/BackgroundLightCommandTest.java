package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BackgroundLightCommandTest {
    private BackgroundLightCommand<Void> bgToggle;

    @Before
    public void setup() {
        bgToggle = new BackgroundLightCommand<>(new Toggle());
    }

    @Test
    public void testMethod() {
        assertEquals("bg_toggle", bgToggle.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(null, bgToggle.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(bgToggle.responseParser().apply(Arrays.asList("ok")));
    }
}
