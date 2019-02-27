package xyz.malkki.yeeja.connection.commands;

import java.util.List;
import java.util.function.Function;

public class StartCf extends YeelightCommand<Void> {
    private int count;
    private ColorFlowStopAction colorFlowStopAction;
    private String flowExpression;

    public StartCf(int count, ColorFlowStopAction colorFlowStopAction, String flowExpression) {
        this.count = count;
        this.colorFlowStopAction = colorFlowStopAction;
        this.flowExpression = flowExpression;
    }

    @Override
    public String getMethod() {
        return "start_cf";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { count, colorFlowStopAction.paramValue, flowExpression };
    }

    @Override
    public Function<List<Object>, Void> responseParser() {
        return response -> null;
    }

    public enum ColorFlowStopAction {
        RESTORE_STATE(0), KEEP_STATE(1), TURN_OFF(2);

        private final int paramValue;

        ColorFlowStopAction(int paramValue) {
            this.paramValue = paramValue;
        }
    }
}
