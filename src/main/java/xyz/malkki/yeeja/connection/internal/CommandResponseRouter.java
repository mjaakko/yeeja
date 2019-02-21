package xyz.malkki.yeeja.connection.internal;

import xyz.malkki.yeeja.internal.InterthreadObjectTransmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandResponseRouter {
    private Map<Integer, InterthreadObjectTransmitter<CommandResponse>> transmitters = new ConcurrentHashMap<>(1);

    public CommandResponse waitFor(int id) throws InterruptedException {
        CommandResponse response = transmitters.computeIfAbsent(id, key -> new InterthreadObjectTransmitter<>()).waitFor();
        transmitters.remove(id);
        return response;
    }

    public void sendResponse(int id, Map<String, Object> response) {
        transmitters.computeIfAbsent(id, key -> new InterthreadObjectTransmitter<>()).transmit(CommandResponse.response(response));
    }

    public void sendError(Throwable throwable) {
        transmitters.values().forEach(transmitter -> transmitter.transmit(CommandResponse.exception(throwable)));
    }

    public static class CommandResponse {
        public final Map<String, Object> response;
        public final Throwable exception;

        private CommandResponse(Map<String, Object> response, Throwable exception) {
            this.response = response;
            this.exception = exception;
        }

        public static CommandResponse response(Map<String, Object> response) {
            return new CommandResponse(response, null);
        }

        public static CommandResponse exception(Throwable throwable) {
            return new CommandResponse(null, throwable);
        }
    }
}