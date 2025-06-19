package com.github.henriquemb.velocitycore.enums;

import lombok.Getter;

@Getter
public enum Placeholders {
    PLAYER_NAME("%player_name%"),
    TARGET_PLAYER_NAME("%target_player_name%"),
    SERVER_NAME("%server_name%"),
    TARGET_SERVER_NAME("%target_server_name%"),
    MESSAGE("%message%");

    private final String placeholder;

    Placeholders(String placeholder) {
        this.placeholder = placeholder;
    }
}
