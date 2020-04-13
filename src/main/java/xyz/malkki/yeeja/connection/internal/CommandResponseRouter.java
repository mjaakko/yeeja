package xyz.malkki.yeeja.connection.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.malkki.yeeja.internal.InterthreadObjectTransmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandResponseRouter {
    private Map<Integer, InterthreadObjectTransmitter<CommandResponse>> transmitters = new ConcurrentHashMap<>(1);

    @NotNull
    public CommandResponse waitFor(int id) throws InterruptedException {
        CommandResponse response = transmitters.computeIfAbsent(id, key -> new InterthreadObjectTransmitter<>()).waitFor();
        transmitters.remove(id);
        return response;
    }

    public void sendResponse(int id, @NotNull Map<String, Object> response) {
        transmitters.computeIfAbsent(id, key -> new InterthreadObjectTransmitter<>()).transmit(CommandResponse.response(response));
    }

    public void sendError(@NotNull Throwable throwable) {
        transmitters.values().forEach(transmitter -> transmitter.transmit(CommandResponse.exception(throwable)));
    }

    public static class CommandResponse {
        public final Map<String, Object> response;
        public final Throwable exception;

        private CommandResponse(@Nullable Map<String, Object> response, @Nullable Throwable exception) {
            this.response = response;
            this.exception = exception;
        }

        @NotNull
        public static CommandResponse response(@NotNull Map<String, Object> response) {
            return new CommandResponse(response, null);
        }

        @NotNull
        public static CommandResponse exception(@NotNull Throwable throwable) {
            return new CommandResponse(null, throwable);
        }
    }
}