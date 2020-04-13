package xyz.malkki.yeeja.internal;

import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CountDownLatch;

public class InterthreadObjectTransmitter<E> {
    private CountDownLatch latch = new CountDownLatch(1);
    private volatile E object;

    @Nullable
    public E waitFor() throws InterruptedException {
        latch.await();
        return object;
    }

    public void transmit(@Nullable E object) {
        if (latch.getCount() == 0) {
            throw new IllegalStateException("Transmitter was already used!");
        }

        this.object = object;
        latch.countDown();
    }
}
