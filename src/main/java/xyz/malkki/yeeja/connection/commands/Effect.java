package xyz.malkki.yeeja.connection.commands;

public enum Effect {
    SUDDEN("sudden"), SMOOTH("smooth");

    final String paramValue;

    Effect(String paramValue) {
        this.paramValue = paramValue;
    }
}
