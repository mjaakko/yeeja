package xyz.malkki.yeeja;

/**
 * Constant values related to Yeelight
 */
public class YeelightConstants {
    private YeelightConstants() {}

    /**
     * Line break that is used by Yeelight protocol
     */
    public static final String LINE_BREAK = "\r\n";

    /**
     * Multicast address used by Yeelight device discovery
     */
    public static final String YEELIGHT_ADDRESS = "239.255.255.250";
    /**
     * Multicast port used by Yeelight device discovery
     */
    public static final int YEELIGHT_PORT = 1982;
}
