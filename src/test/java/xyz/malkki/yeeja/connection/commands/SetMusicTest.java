package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetMusicTest {
    private SetMusic setMusicOn;
    private SetMusic setMusicOff;

    @Before
    public void setup() {
        setMusicOn = new SetMusic(true, "1.1.1.1", 9000);
        setMusicOff = new SetMusic(false, null, 0);
    }

    @Test
    public void testMethod() {
        assertEquals("set_music", setMusicOn.getMethod());
    }

    @Test
    public void testParamsWithMusicOn() {
        assertArrayEquals(new Object[] { 1, "1.1.1.1", 9000}, setMusicOn.getParams());
    }

    @Test
    public void testParamsWithMusicOff() {
        assertArrayEquals(new Object[] { 0 }, setMusicOff.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setMusicOn.responseParser().apply(Arrays.asList("ok")));
    }
}
