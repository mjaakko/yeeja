package xyz.malkki.yeeja.discovery.internal;

import org.junit.Test;
import xyz.malkki.yeeja.YeelightConstants;
import xyz.malkki.yeeja.discovery.YeelightDevice;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YeelightParserTest {
    private static final String RESPONSE_DISCOVERY_FULL = String.join(YeelightConstants.LINE_BREAK,  "HTTP/1.1 200 OK",
            "Cache-Control: max-age=3600",
            "Date:",
            "Ext:",
            "Location: yeelight://192.168.1.239:55443",
            "Server: POSIX UPnP/1.0 YGLC/1",
            "id: 0x000000000015243f",
            "model: color",
            "fw_ver: 18",
            "support: get_prop set_default set_power toggle set_bright start_cf stop_cf set_scene cron_add cron_get cron_del set_ct_abx set_rgb",
            "power: on",
            "bright: 100",
            "color_mode: 2",
            "ct: 4000",
            "rgb: 16711680",
            "hue: 100",
            "sat: 35",
            "name: my_bulb",
            "HTTP/1.1 200 OK",
            "Cache-Control: max-age=3600",
            "Date:",
            "Ext:",
            "Location: yeelight://192.168.1.240:55443",
            "Server: POSIX UPnP/1.0 YGLC/1",
            "id: 0x000000000015346f",
            "model: bslamp",
            "fw_ver: 18",
            "support: get_prop set_default set_power toggle set_bright start_cf stop_cf set_scene cron_add cron_get cron_del set_ct_abx set_rgb",
            "power: on",
            "bright: 70",
            "color_mode: 2",
            "ct: 4000",
            "rgb: 16711680",
            "hue: 100",
            "sat: 35",
            "name: my_lamp",
            "");

    private static final String RESPONSE_DISCOVERY_SINGLE = String.join(YeelightConstants.LINE_BREAK,
            "HTTP/1.1 200 OK",
            "Cache-Control: max-age=3600",
            "Date:",
            "Ext:",
            "Location: yeelight://192.168.1.240:55443",
            "Server: POSIX UPnP/1.0 YGLC/1",
            "id: 0x000000000015346f",
            "model: bslamp",
            "fw_ver: 18",
            "support: get_prop set_default set_power toggle set_bright start_cf stop_cf set_scene cron_add cron_get cron_del set_ct_abx set_rgb",
            "power: on",
            "bright: 70",
            "color_mode: 2",
            "ct: 4000",
            "rgb: 16711680",
            "hue: 100",
            "sat: 35",
            "name: my_lamp",
            "");

    @Test
    public void testParseSingleDiscoveryMessageToMap() {
        Map<String, String> map = YeelightParser.parseSingleMessage(RESPONSE_DISCOVERY_SINGLE);

        assertEquals("yeelight://192.168.1.240:55443", map.get("Location"));
        assertEquals("0x000000000015346f", map.get("id"));
    }

    @Test
    public void testParseSingleDiscoveryMessageToYeelightDevice() {
        YeelightDevice yeelight = YeelightParser.parseMapToYeelightDevice(YeelightParser.parseSingleMessage(RESPONSE_DISCOVERY_SINGLE));

        assertEquals(13, yeelight.getSupportedFunctions().size());
        assertEquals(YeelightDevice.YeelightModel.BSLAMP, yeelight.getModel());
        assertEquals(YeelightDevice.ColorMode.COLOR_TEMPERATURE, yeelight.getColorMode());
        assertEquals(55443, yeelight.getPort());
    }

    @Test
    public void testParseDiscoveryResponse() {
        List<YeelightDevice> yeelights = YeelightParser.parseResponse(RESPONSE_DISCOVERY_FULL, "HTTP/1.1 200 OK".concat(YeelightConstants.LINE_BREAK));

        assertEquals(2, yeelights.size());

        assertTrue(yeelights.stream().anyMatch(yeelight -> yeelight.getColorMode().equals(YeelightDevice.ColorMode.COLOR_TEMPERATURE)));

        assertTrue(yeelights.stream().anyMatch(yeelight -> yeelight.getModel().equals(YeelightDevice.YeelightModel.BSLAMP)));

        assertTrue(yeelights.stream().anyMatch(yeelight -> yeelight.getAddress().equals("192.168.1.240")));
    }
}
