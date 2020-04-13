package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;
import xyz.malkki.yeeja.connection.YeelightProps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GetProp extends YeelightCommand<YeelightProps> {
    private String[] props;

    /**
     * @param props List of props that will be retrieved, see constants in {@link YeelightProps}
     */
    public GetProp(@NotNull String... props) {
        this.props = props;
    }

    @NotNull
    @Override
    public String getMethod() {
        return "get_prop";
    }

    @NotNull
    @Override
    public Object[] getParams() {
        return props;
    }

    @NotNull
    @Override
    public Function<List<Object>, YeelightProps> responseParser() {
        return response -> {
            Map<String, Object> propsAsMap = new HashMap<>(props.length);
            for (int i = 0; i < props.length; i++) {
                String propName = props[i];
                String responseValue = response.get(i).toString();
                try {
                    //Ugly hack to fix types in response
                    //This is required to be able to parse props with 'YeelightProps.parseKnownPropsFromMap'
                    double responseValueAsDouble = Double.parseDouble(responseValue);

                    propsAsMap.put(propName, responseValueAsDouble);
                } catch (NumberFormatException e) {
                    propsAsMap.put(propName, responseValue);
                }
            }

            return YeelightProps.parseKnownPropsFromMap(propsAsMap);
        };
    }
}
