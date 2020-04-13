package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public abstract class YeelightCommand<R> {
    /**
     * Gets the method name.
     * See Yeelight Inter-Operation Spec for list of possible methods.
     * @return Method name
     */
    @NotNull
    public abstract String getMethod();

    /**
     * Gets the command parameters as an array
     * @return Array of parameters for the command
     */
    @Nullable
    public abstract Object[] getParams();

    /**
     * @return Function that parses Yeelight command response to a Java object
     */
    @NotNull
    public abstract Function<List<Object>, R> responseParser();
}
