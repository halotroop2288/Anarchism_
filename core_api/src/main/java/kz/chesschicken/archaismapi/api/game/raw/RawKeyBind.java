package kz.chesschicken.archaismapi.api.game.raw;

import org.jetbrains.annotations.NotNull;

public class RawKeyBind {
    public final String name;
    public final int bind;

    public RawKeyBind(@NotNull String name, int bind) {
        this.name = name;
        this.bind = bind;
    }
}
