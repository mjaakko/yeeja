package xyz.malkki.yeeja.discovery.internal;

import org.jetbrains.annotations.NotNull;
import xyz.malkki.yeeja.ColorMode;
import xyz.malkki.yeeja.discovery.YeelightDevice;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static xyz.malkki.yeeja.YeelightConstants.LINE_BREAK;

public class YeelightParser {
    private YeelightParser() {}

    @NotNull
    public static List<YeelightDevice> parseResponse(@NotNull String response, @NotNull String delimiter) {
        return Arrays.stream(response.split(delimiter))
                .map(String::trim)
                .filter(resp -> !resp.isEmpty())
                .map(YeelightParser::parseSingleMessage)
                .map(YeelightParser::parseMapToYeelightDevice)
                .distinct()
                .collect(Collectors.toList());
    }

    @NotNull
    public static Map<String, String> parseSingleMessage(@NotNull String message) {
        return Arrays.stream(message.split(LINE_BREAK))
                .filter(line -> line.contains(":"))
                .collect(Collectors.toMap(line -> line.substring(0, line.indexOf(":")), line -> line.substring(line.indexOf(":")+1).trim()));
    }

    @NotNull
    public static YeelightDevice parseMapToYeelightDevice(@NotNull Map<String, String> map) {
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
                ColorMode.findByMode(Integer.parseInt(map.get("color_mode"))),
                Integer.parseInt(map.get("ct")),
                Integer.parseInt(map.get("rgb")),
                Integer.parseInt(map.get("hue")),
                Integer.parseInt(map.get("sat")),
                map.get("name"));
    }
}
