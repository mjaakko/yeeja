package xyz.malkki.yeeja.discovery;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class YeelightDeviceTest {
    @Test
    public void testFindColorModeReturnsValueWithCorrentModeInt() {
        assertEquals(YeelightDevice.ColorMode.COLOR, YeelightDevice.ColorMode.findByMode(1));
    }

    @Test
    public void testFindColorModeReturnsNullWithIncorrentModeInt() {
        assertNull(YeelightDevice.ColorMode.findByMode(-1));
    }

    @Test
    public void testEqualsAndHashcodeMatch() {
        YeelightDevice yeelight1 = new YeelightDevice("mock_address", 1000, "mock_id", YeelightDevice.YeelightModel.COLOR, 10, Collections.emptySet(), true, 100, YeelightDevice.ColorMode.COLOR, 0, 0, 0, 0, "mock_name");
        YeelightDevice yeelight2 = new YeelightDevice("mock_address", 1000, "mock_id", YeelightDevice.YeelightModel.COLOR, 10, Collections.emptySet(), true, 100, YeelightDevice.ColorMode.COLOR, 0, 0, 0, 0, "mock_name");

        assertTrue(yeelight1.equals(yeelight2));
        assertEquals(yeelight1.hashCode(), yeelight2.hashCode());
    }

    @Test
    public void testEqualsAndHashcodeDontMatch() {
        YeelightDevice yeelight1 = new YeelightDevice("mock_address", 1000, "mock_id", YeelightDevice.YeelightModel.COLOR, 10, Collections.emptySet(), true, 100, YeelightDevice.ColorMode.COLOR, 0, 0, 0, 0, "mock_name");
        YeelightDevice yeelight2 = new YeelightDevice("mock_address", 1000, "mock_id", YeelightDevice.YeelightModel.STRIPE, 10, Collections.emptySet(), true, 100, YeelightDevice.ColorMode.COLOR, 0, 0, 0, 0, "mock_name");

        assertFalse(yeelight1.equals(yeelight2));
        assertNotEquals(yeelight1.hashCode(), yeelight2.hashCode());
    }
}
