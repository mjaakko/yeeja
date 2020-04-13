package xyz.malkki.yeeja.connection.commands;

import org.jetbrains.annotations.NotNull;

public enum Effect {
    SUDDEN("sudden"), SMOOTH("smooth");

    @NotNull final String paramValue;

    Effect(@NotNull String paramValue) {
        this.paramValue = paramValue;
    }
}
