package xyz.malkki.yeeja;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static xyz.malkki.yeeja.YeelightConstants.*;

class YeelightParser {
    private YeelightParser() {}

    static List<YeelightDevice> parseResponse(String response, String delimiter) {
        return Arrays.stream(response.split(delimiter))
                .map(String::trim)
                .filter(resp -> !resp.isEmpty())
                .map(YeelightParser::parseSingleMessage)
                .map(YeelightParser::parseMapToYeelightDevice)
                .distinct()
                .collect(Collectors.toList());
    }

    static Map<String, String> parseSingleMessage(String message) {
        return Arrays.stream(message.split(LINE_BREAK))
                .filter(line -> line.contains(":"))
                .collect(Collectors.toMap(line -> line.substring(0, line.indexOf(":")), line -> line.substring(line.indexOf(":")+1).trim()));
    }

    static YeelightDevice parseMapToYeelightDevice(Map<String, String> map) {
        URI location = URI.create(map.get("Location"));
        String address = location.getHost();
        int port = location.getPort();

        return new YeelightDevice(address,
                port,
                map.get("id"),
                YeelightDevice.YeelightModel.valueOf(map.get("model").toUpperCase(Locale.ROOT)),
                Integer.parseInt(map.get("fw_ver")),
                new HashSet<String>(Arrays.asList(map.get("support").split(" "))),
                "on".equals(map.get("power")),
                Integer.parseInt(map.get("bright")),
                YeelightDevice.ColorMode.findByMode(Integer.parseInt(map.get("color_mode"))),
                Integer.parseInt(map.get("ct")),
                Integer.parseInt(map.get("rgb")),
                Integer.parseInt(map.get("hue")),
                Integer.parseInt(map.get("sat")),
                map.get("name"));
    }
}
