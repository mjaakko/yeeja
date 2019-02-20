package xyz.malkki.yeeja.connection;

import java.util.Objects;

public class YeelightCommandException extends Exception {
    private int code;
    private String message;

    public YeelightCommandException(int code, String message) {
        super(message);

        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YeelightCommandException that = (YeelightCommandException) o;
        return code == that.code &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", message, code);
    }
}
