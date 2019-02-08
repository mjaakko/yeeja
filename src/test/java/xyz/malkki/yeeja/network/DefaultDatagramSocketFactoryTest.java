package xyz.malkki.yeeja.network;

import org.junit.Test;

import java.net.SocketException;

import static org.junit.Assert.*;

public class DefaultDatagramSocketFactoryTest {
    @Test
    public void testCreate() throws SocketException {
        assertNotNull(new DefaultDatagramSocketFactory().create());
    }
}
