package xyz.malkki.yeeja.connection;

import xyz.malkki.yeeja.discovery.YeelightDevice;
import xyz.malkki.yeeja.internal.CastFunction;
import xyz.malkki.yeeja.internal.MathUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class YeelightProps {
    public static final YeelightPropMeta POWER = YeelightPropMeta.create("power", power -> "on".equals(power));
    public static final YeelightPropMeta BRIGHTNESS = YeelightPropMeta.create("bright", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta COLOR_TEMPERATURE = YeelightPropMeta.create("ct", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta RGB = YeelightPropMeta.create("rgb", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta HUE = YeelightPropMeta.create("hue", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta SATURATION = YeelightPropMeta.create("sat", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta COLOR_MODE = YeelightPropMeta.create("color_mode", cm -> YeelightDevice.ColorMode.findByMode((int)Math.round((double)cm)));
    public static final YeelightPropMeta FLOWING = YeelightPropMeta.create("flowing", flowing -> (double)flowing == 1);
    public static final YeelightPropMeta DELAY_OFF = YeelightPropMeta.create("delay_off", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta FLOW_PARAMS = YeelightPropMeta.create("flow_params", CastFunction.forType(String.class));
    public static final YeelightPropMeta MUSIC_ON = YeelightPropMeta.create("music_on", musicOn -> (double)musicOn == 1);
    public static final YeelightPropMeta NAME = YeelightPropMeta.create("name", CastFunction.forType(String.class));
    public static final YeelightPropMeta BACKGROUND_POWER = YeelightPropMeta.create("bg_power", power -> "on".equals(power));
    public static final YeelightPropMeta BACKGROUND_FLOWING = YeelightPropMeta.create("bg_flowing", flowing -> (double)flowing == 1);
    public static final YeelightPropMeta BACKGROUND_FLOW_PARAMS = YeelightPropMeta.create("bg_flow_params", CastFunction.forType(String.class));
    public static final YeelightPropMeta BACKGROUND_COLOR_TEMPERATURE = YeelightPropMeta.create("bg_ct", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta BACKGROUND_COLOR_MODE = YeelightPropMeta.create("bg_lmode", cm -> YeelightDevice.ColorMode.findByMode((int)Math.round((double)cm)));
    public static final YeelightPropMeta BACKGROUND_BRIGHTNESS = YeelightPropMeta.create("bg_bright", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta BACKGROUND_RGB = YeelightPropMeta.create("bg_rgb", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta BACKGROUND_HUE = YeelightPropMeta.create("bg_hue", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta BACKGROUND_SATURATION = YeelightPropMeta.create("bg_sat", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta NIGHTLIGHT_BRIGHTNESS = YeelightPropMeta.create("nl_br", CastFunction.forType(Double.class).andThen(MathUtils::doubleToInt));
    public static final YeelightPropMeta DAYLIGHT_MODE = YeelightPropMeta.create("active_mode", active -> (double)active == 0);

    public static final Map<String, YeelightPropMeta> KNOWN_PROPS = Arrays.asList(POWER,
            BRIGHTNESS,
            COLOR_TEMPERATURE,
            RGB,
            HUE,
            SATURATION,
            COLOR_MODE,
            FLOWING,
            DELAY_OFF,
            FLOW_PARAMS,
            MUSIC_ON,
            NAME,
            BACKGROUND_POWER,
            BACKGROUND_FLOWING,
            BACKGROUND_FLOW_PARAMS,
            BACKGROUND_COLOR_TEMPERATURE,
            BACKGROUND_COLOR_MODE,
            BACKGROUND_BRIGHTNESS,
            BACKGROUND_RGB,
            BACKGROUND_HUE,
            BACKGROUND_SATURATION,
            NIGHTLIGHT_BRIGHTNESS,
            DAYLIGHT_MODE)
            .stream()
            .collect(Collectors.toMap(YeelightPropMeta::name, Function.identity()));

    private Map<String, Object> props;

    public YeelightProps(Map<String, Object> props) {
        this.props = Collections.unmodifiableMap(props);
    }

    public boolean hasProp(YeelightPropMeta propMeta) {
        return hasProp(propMeta.name());
    }

    public boolean hasProp(String key) {
        return props.containsKey(key);
    }

    public Set<String> getAvailableProps() {
        return props.keySet();
    }

    public <T> T getProp(YeelightPropMeta<T> propMeta) {
        return getProp(propMeta.name());
    }

    public <T> T getProp(String key) {
        return (T)props.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YeelightProps that = (YeelightProps) o;
        return Objects.equals(props, that.props);
    }

    @Override
    public int hashCode() {
        return Objects.hash(props);
    }

    @Override
    public String toString() {
        return "YeelightProps:\n\t" +
                String.join("\n\t", getAvailableProps().stream().map(key -> key+": "+getProp(key).toString()).collect(Collectors.toList()));
    }

    public static YeelightProps parseKnownPropsFromMap(Map<String, Object> map) {
        return new YeelightProps(map.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), e -> {
          return KNOWN_PROPS.containsKey(e.getKey()) ?
                  KNOWN_PROPS.get(e.getKey()).apply(e.getValue()) :
                  e.getValue().toString(); //Parse unknown props to String
        })));
    }
}
