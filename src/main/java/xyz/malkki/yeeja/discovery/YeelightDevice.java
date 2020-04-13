package xyz.malkki.yeeja.discovery;

import org.jetbrains.annotations.NotNull;
import xyz.malkki.yeeja.ColorMode;
import xyz.malkki.yeeja.connection.YeelightConnection;
import xyz.malkki.yeeja.connection.commands.YeelightCommand;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a Yeelight device.
 *
 * Note that the values in this class are not updated if the Yeelight properties change. To get updated values, either run Yeelight discovery again or open {@link xyz.malkki.yeeja.connection.YeelightConnection} with {@link xyz.malkki.yeeja.connection.YeelightConnection#setNotificationListener(YeelightConnection.YeelightNotificationListener)} to this device
 */
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

    public YeelightDevice(@NotNull String address, int port, @NotNull String id, @NotNull YeelightModel model, int firmwareVersion, @NotNull Set<@NotNull String> supportedFunctions, boolean power, int brightness, @NotNull ColorMode colorMode, int colorTemperature, int rgb, int hue, int saturation, @NotNull String name) {
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

    /**
     * Gets the Yeelight host address
     * @return Yeelight host address
     */
    @NotNull
    public String getAddress() {
        return address;
    }

    /**
     * Gets the Yeelight connection port
     * @return Yeelight connection port
     */
    @NotNull
    public int getPort() {
        return port;
    }

    /**
     * Gets the Yeelight device ID
     * @return Yeelight device ID
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Gets the Yeelight model
     * @return Yeelight model
     */
    @NotNull
    public YeelightModel getModel() {
        return model;
    }

    /**
     * Gets the firmware version of the Yeelight device
     * @return Firmware version of the Yeelight device
     */
    public int getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * Gets a list of functions that can be used with the Yeelight device.
     * @return List of functions that can be used with the Yeelight device
     * @see YeelightCommand#getMethod()
     */
    @NotNull
    public Set<@NotNull String> getSupportedFunctions() {
        return supportedFunctions;
    }

    /**
     * Gets the power status of the Yeelight device
     * @return true if Yeelight is powered on, false otherwise
     */
    public boolean isPower() {
        return power;
    }

    /**
     * Gets brightness of the Yeelight
     * @return Brightness, 0-100
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     * Gets the color mode that the Yeelight is using
     * @return Color mode
     */
    @NotNull
    public ColorMode getColorMode() {
        return colorMode;
    }

    /**
     * Gets the color temperature
     * @return Color temperature
     */
    public int getColorTemperature() {
        return colorTemperature;
    }

    /**
     * Gets the RGB color
     * @return RGB color
     */
    public int getRgb() {
        return rgb;
    }

    /**
     * Gets the hue
     * @return Hue
     */
    public int getHue() {
        return hue;
    }

    /**
     * Gets the saturation
     * @return Saturation
     */
    public int getSaturation() {
        return saturation;
    }

    /**
     * Gets the name of the Yeelight device
     * @return Name of the Yeelight device
     */
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

    /**
     * Describes the model of a Yeelight device
     */
    public enum YeelightModel {
        /**
         * Represents a device that only supports brightness adjustment
         */
        MONO,
        /**
         * Represents a device that supports both color and color temperature adjustment
         */
        COLOR,
        /**
         * Represents a Yeelight LED strip
         */
        STRIPE,
        /**
         * Represents a Yeelight ceiling lamp
         */
        CEILING,
        /**
         * Represents a Yeelight bedside lamp
         */
        BSLAMP
    }
}