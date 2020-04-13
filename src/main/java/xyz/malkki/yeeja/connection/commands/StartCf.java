package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public class StartCf extends YeelightCommand<Void> {
    private int count;
    private ColorFlowStopAction colorFlowStopAction;
    private String flowExpression;

    public StartCf(int count, @NotNull ColorFlowStopAction colorFlowStopAction, @NotNull String flowExpression) {
        this.count = count;
        this.colorFlowStopAction = colorFlowStopAction;
        this.flowExpression = flowExpression;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "start_cf";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return new Object[] { count, colorFlowStopAction.paramValue, flowExpression };
    }

    @NotNull
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
