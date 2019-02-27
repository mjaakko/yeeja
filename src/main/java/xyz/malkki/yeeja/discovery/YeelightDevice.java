package xyz.malkki.yeeja.discovery;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class YeelightDevice {
    private String address;
    private int port;
    private String id;
    private YeelightModel model;
    private int firmwareVersion;
    private Set<String> supportedFunctions;
    private boolean power;
    private int brightness;
    private ColorMode colorMode;
    private int colorTemperature;
    private int rgb;
    private int hue;
    private int saturation;
    private String name;

    public YeelightDevice(String address, int port, String id, YeelightModel model, int firmwareVersion, Set<String> supportedFunctions, boolean power, int brightness, ColorMode colorMode, int colorTemperature, int rgb, int hue, int saturation, String name) {
        this.address = address;
        this.port = port;
        this.id = id;
        this.model = model;
        this.firmwareVersion = firmwareVersion;
        this.supportedFunctions = supportedFunctions;
        this.power = power;
        this.brightness = brightness;
        this.colorMode = colorMode;
        this.colorTemperature = colorTemperature;
        this.rgb = rgb;
        this.hue = hue;
        this.saturation = saturation;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getId() {
        return id;
    }

    public YeelightModel getModel() {
        return model;
    }

    public int getFirmwareVersion() {
        return firmwareVersion;
    }

    public Set<String> getSupportedFunctions() {
        return supportedFunctions;
    }

    public boolean isPower() {
        return power;
    }

    public int getBrightness() {
        return brightness;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public int getColorTemperature() {
        return colorTemperature;
    }

    public int getRgb() {
        return rgb;
    }

    public int getHue() {
        return hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YeelightDevice that = (YeelightDevice) o;
        return port == that.port &&
                firmwareVersion == that.firmwareVersion &&
                power == that.power &&
                brightness == that.brightness &&
                colorTemperature == that.colorTemperature &&
                rgb == that.rgb &&
                hue == that.hue &&
                saturation == that.saturation &&
                Objects.equals(address, that.address) &&
                Objects.equals(id, that.id) &&
                model == that.model &&
                Objects.equals(supportedFunctions, that.supportedFunctions) &&
                colorMode == that.colorMode &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port, id, model, firmwareVersion, supportedFunctions, power, brightness, colorMode, colorTemperature, rgb, hue, saturation, name);
    }

    public enum YeelightModel {
        MONO, COLOR, STRIPE, CEILING, BSLAMP
    }

    public enum ColorMode {
        COLOR(1), COLOR_TEMPERATURE(2), HSV(3);

        private int mode;

        ColorMode(int mode) {
            this.mode = mode;
        }

        private static Map<Integer, ColorMode> BY_MODE_INT = Arrays.stream(ColorMode.values()).collect(Collectors.toMap(cm -> cm.mode, Function.identity()));

        public static ColorMode findByMode(int mode) {
            return BY_MODE_INT.get(mode);
        }
    }
}