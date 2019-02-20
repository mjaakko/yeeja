package xyz.malkki.yeeja.internal;

public class ThreadUtils {
    private ThreadUtils() {}

    /**
     * Interrupts the thread and waits for it to die
     * Note that if {@link InterruptedException} is thrown, it is quietly ignored
     * @param thread
     */
    public static void quietlyInterruptAndJoin(Thread thread) {
        try {
            thread.interrupt();
            thread.join();
        } catch (InterruptedException e) {
        }
    }
}
