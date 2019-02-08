package xyz.malkki.yeeja.network;

import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class DefaultInetAddressFactoryTest {
    @Test
    public void testCreate() throws UnknownHostException {
        assertNotNull(new DefaultInetAddressFactory().create("127.0.01"));
    }
}
