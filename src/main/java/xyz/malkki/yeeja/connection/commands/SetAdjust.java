package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class SetAdjust extends YeelightCommand<Void> {
    private Action action;
    private Prop prop;

    public SetAdjust(Action action, Prop prop) {
        this.action = action;
        this.prop = prop;
    }

    @Override
    public String getMethod() {
        return "set_adjust";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { action.paramValue, prop.paramValue };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }

    public enum Action {
        INCREASE("increase"), DECREASE("decrease"), CIRCLE("circle");

        private final String paramValue;

        Action(String paramValue) {
            this.paramValue = paramValue;
        }
    }

    public enum Prop {
        BRIGHT("bright"), CT("ct"), COLOR("color");

        private final String paramValue;

        Prop(String paramValue) {
            this.paramValue = paramValue;
        }
    }
}
