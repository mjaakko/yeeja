package xyz.malkki.yeeja.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultSocketFactoryTest {
    @Test
    public void testCreate() {
        assertNotNull(new DefaultSocketFactory().create());
    }
}
