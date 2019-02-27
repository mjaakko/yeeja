package xyz.malkki.yeeja.connection.commands;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class CronGet extends YeelightCommand<CronGet.CronGetResponse> {
    private Gson gson = new Gson();
    private int type;

    public CronGet(int type) {
        this.type = type;
    }

    @Override
    public String getMethod() {
        return "cron_get";
    }

    @Override
    public Object[] getParams() {
        return new Object[] { type };
    }

    @Override
    public Function<List<Object>, CronGetResponse> responseParser() {
        return response -> gson.fromJson(response.get(0).toString(), CronGetResponse.class);
    }

    public static class CronGetResponse {
        private final int type;
        private final int delay;
        private final int mix;

        CronGetResponse(int type, int delay, int mix) {
            this.type = type;
            this.delay = delay;
            this.mix = mix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CronGetResponse that = (CronGetResponse) o;
            return type == that.type &&
                    delay == that.delay &&
                    mix == that.mix;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, delay, mix);
        }
    }
}
