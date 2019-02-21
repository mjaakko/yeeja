package xyz.malkki.yeeja.internal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterthreadObjectTransmitterTest {
    @Test(timeout = 2000)
    public void testTransmit() throws InterruptedException {
        InterthreadObjectTransmitter<String> transmitter = new InterthreadObjectTransmitter<>();

        new Thread(() -> {
            try {
                Thread.sleep(100);
                transmitter.transmit("hello");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        assertEquals("hello", transmitter.waitFor());
    }

    @Test
    public void testReturnImmediatelyIfAlreadyTransmitted() throws InterruptedException {
        InterthreadObjectTransmitter<String> transmitter = new InterthreadObjectTransmitter<>();
        transmitter.transmit("hello");

        assertEquals("hello", transmitter.waitFor());
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotTransmitTwice() {
        InterthreadObjectTransmitter transmitter = new InterthreadObjectTransmitter();

        transmitter.transmit(new Object());
        transmitter.transmit(new Object());
    }
}
