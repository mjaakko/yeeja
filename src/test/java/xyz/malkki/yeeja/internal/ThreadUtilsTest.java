package xyz.malkki.yeeja.internal;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThreadUtilsTest {
    @Test(timeout = 1000)
    public void testQuietlyInterruptAndJoin() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        });

        thread.start();

        assertTrue(thread.isAlive());
        ThreadUtils.quietlyInterruptAndJoin(thread);
        assertFalse(thread.isAlive());
    }
}
