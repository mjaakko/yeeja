package xyz.malkki.yeeja.connection.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SetSceneTest {
    private SetScene setScene;

    @Before
    public void setup() {
        setScene = new SetScene(SetScene.SceneClass.CF, 0, 0, "100, 1, 0, 100");
    }

    @Test
    public void testMethod() {
        assertEquals("set_scene", setScene.getMethod());
    }

    @Test
    public void testParams() {
        assertArrayEquals(new Object[] { "cf", 0, 0, "100, 1, 0, 100" }, setScene.getParams());
    }

    @Test
    public void testResponseParser() {
        assertNull(setScene.responseParser().apply(Arrays.asList("ok")));
    }
}
