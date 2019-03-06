package xyz.malkki.yeeja.discovery;

import org.junit.Test;
import xyz.malkki.yeeja.ColorMode;

import java.util.Collections;

import static org.junit.Assert.*;
import static xyz.malkki.yeeja.discovery.YeelightDevice.YeelightModel.COLOR;

public class YeelightDeviceTest {
    @Test
    public void testEqualsAndHashcodeMatch() {
        YeelightDevice yeelight1 = new YeelightDevice("mock_address", 1000, "mock_id", COLOR, 10, Collections.emptySet(), true, 100, ColorMode.COLOR, 0, 0, 0, 0, "mock_name");
        YeelightDevice yeelight2 = new YeelightDevice("mock_address", 1000, "mock_id", COLOR, 10, Collections.emptySet(), true, 100, ColorMode.COLOR, 0, 0, 0, 0, "mock_name");

        assertTrue(yeelight1.equals(yeelight2));
        assertEquals(yeelight1.hashCode(), yeelight2.hashCode());
    }

    @Test
    public void testEqualsAndHashcodeDontMatch() {
        YeelightDevice yeelight1 = new YeelightDevice("mock_address", 1000, "mock_id", COLOR, 10, Collections.emptySet(), true, 100, ColorMode.COLOR, 0, 0, 0, 0, "mock_name");
        YeelightDevice yeelight2 = new YeelightDevice("mock_address", 1000, "mock_id", YeelightDevice.YeelightModel.STRIPE, 10, Collections.emptySet(), true, 100, ColorMode.COLOR, 0, 0, 0, 0, "mock_name");

        assertFalse(yeelight1.equals(yeelight2));
        assertNotEquals(yeelight1.hashCode(), yeelight2.hashCode());
    }

    @Test
    public void testGetters() {
        YeelightDevice yeelight = new YeelightDevice("mock_address", 1000, "mock_id", COLOR, 10, Collections.emptySet(), true, 100, ColorMode.COLOR, 0, 0, 0, 0, "mock_name");

        assertEquals("mock_address", yeelight.getAddress());
        assertEquals(1000, yeelight.getPort());
        assertEquals("mock_id", yeelight.getId());
        assertEquals(COLOR, yeelight.getModel());
        assertEquals(10, yeelight.getFirmwareVersion());
        assertTrue(yeelight.getSupportedFunctions().isEmpty());
        assertTrue(yeelight.isPower());
        assertEquals(100, yeelight.getBrightness());
        assertEquals(ColorMode.COLOR, yeelight.getColorMode());
        assertEquals(0, yeelight.getColorTemperature());
        assertEquals(0, yeelight.getHue());
        assertEquals(0, yeelight.getSaturation());
        assertEquals(0, yeelight.getRgb());
        assertEquals("mock_name", yeelight.getName());
    }
}
