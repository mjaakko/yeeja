package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;
import xyz.malkki.yeeja.connection.YeelightProps;

import java.util.Arrays;

import static org.junit.Assert.*;

public class GetPropTest {
    private GetProp getProp;

    @Before
    public void setup() {
        getProp = new GetProp("power", "not_exist", "bright");
    }

    @Test
    public void testMethod() {
        assertEquals("get_prop", getProp.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new String[] { "power", "not_exist", "bright" }, getProp.getParams());
    }

    @Test
    public void testResponseParser() {
        YeelightProps props = getProp.responseParser().apply(Arrays.asList("on", "", "100"));

        assertEquals(3, props.getAvailableProps().size());
        assertTrue(props.getProp("power"));
        assertEquals("", props.getProp("not_exist"));
        assertEquals(100, (int)props.getProp("bright"));
    }
}
