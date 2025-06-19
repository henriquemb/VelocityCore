package com.github.henriquemb.velocitycore.settings;

import lombok.Getter;

@Getter
public enum Permissions {
    GLOBAL("velocitycore.global"),
    TELL("velocitycore.tell"),
    TELL_INVISIBLE("velocitycore.tell.invisible"),
    TELL_SPY("velocitycore.tell.spy"),
    RELOAD("velocitycore.reload"),

    ;private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }
}
