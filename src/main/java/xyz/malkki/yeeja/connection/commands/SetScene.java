package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetScene extends YeelightCommand<Void> {
    private SceneClass sceneClass;
    private Object[] values;

    public SetScene(SceneClass sceneClass, Object... values) {
        this.sceneClass = sceneClass;
        this.values = values;
    }

    @Override
    public String getMethod() {
        return "set_scene";
    }

    @Override
    public Object[] getParams() {
        Object[] params = new Object[1 + values.length];
        params[0] = sceneClass.paramValue;
        System.arraycopy(values, 0, params, 1, values.length);

        return params;
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }

    public enum SceneClass {
        COLOR("color"), HSV("hsv"), CT("ct"), CF("cf"), AUTO_DELAY_OFF("auto_delay_off");

        private final String paramValue;

        SceneClass(String paramValue) {
            this.paramValue = paramValue;
        }
    }
}
