package xyz.malkki.yeeja.connection.internal;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class CommandResponseRouterTest {
    private CommandResponseRouter commandResponseRouter;

    @Before
    public void setup() {
        commandResponseRouter = new CommandResponseRouter();
    }

    @Test(timeout = 500)
    public void testCanReceiveResponse() throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            commandResponseRouter.sendResponse(1, Collections.singletonMap("test", "value"));
        }).start();

        CommandResponseRouter.CommandResponse response = commandResponseRouter.waitFor(1);

        assertNotNull(response.response);
        assertNull(response.exception);
        assertEquals(1, response.response.size());
    }


    @Test(timeout = 500)
    public void testCanReceiveError() throws InterruptedException {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            commandResponseRouter.sendError(new Throwable("test"));
        }).start();

        CommandResponseRouter.CommandResponse response = commandResponseRouter.waitFor(1);

        assertNull(response.response);
        assertNotNull(response.exception);
        assertEquals("test", response.exception.getMessage());
    }

    @Test(timeout = 500)
    public void testMultipleListenersCanReceiveSameError() throws InterruptedException, ExecutionException {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            commandResponseRouter.sendError(new Throwable("test"));
        }).start();

        ExecutorService executor =  Executors.newCachedThreadPool();

        Future<CommandResponseRouter.CommandResponse> futureResponse1 = executor.submit(() -> commandResponseRouter.waitFor(1));
        Future<CommandResponseRouter.CommandResponse> futureResponse2 = executor.submit(() -> commandResponseRouter.waitFor(2));

        CommandResponseRouter.CommandResponse response1 = futureResponse1.get();
        CommandResponseRouter.CommandResponse response2 = futureResponse2.get();

        assertNull(response1.response);
        assertNotNull(response1.exception);
        assertEquals("test", response1.exception.getMessage());

        assertNull(response2.response);
        assertNotNull(response2.exception);
        assertEquals("test", response2.exception.getMessage());
    }
}
