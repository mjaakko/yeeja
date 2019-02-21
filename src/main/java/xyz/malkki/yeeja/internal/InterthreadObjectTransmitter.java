package xyz.malkki.yeeja.internal;

import java.util.concurrent.CountDownLatch;

public class InterthreadObjectTransmitter<E> {
    private CountDownLatch latch = new CountDownLatch(1);
    private volatile E object;

    public E waitFor() throws InterruptedException {
        latch.await();
        return object;
    }

    public void transmit(E object) {
        if (latch.getCount() == 0) {
            throw new IllegalStateException("Transmitter was already used!");
        }

        this.object = object;
        latch.countDown();
    }
}
