package xyz.malkki.yeeja;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ColorModeTest {
    @Test
    public void testFindColorModeReturnsValueWithCorrentModeInt() {
        assertEquals(ColorMode.COLOR, ColorMode.findByMode(1));
    }

    @Test
    public void testFindColorModeReturnsNullWithIncorrentModeInt() {
        assertNull(ColorMode.findByMode(-1));
    }
}
