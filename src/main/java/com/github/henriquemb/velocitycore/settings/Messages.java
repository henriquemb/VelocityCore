package com.github.henriquemb.velocitycore.settings;

import com.github.henriquemb.velocitycore.util.FileManager;

import java.util.HashMap;
import java.util.Map;

public enum Messages {
    PERMISSION_NO_PERMISSION("permission.no_permission", "&cYou do not have permission to do that!"),

    PLAYER_NOT_FOUND("player.not_found", "&cPlayer not found!"),
    PLAYER_YOURSELF("player.yourself", "&cYou cannot send a message to yourself!"),

    GLOBAL_USAGE("global.usage", "&cUse: /global <message>"),
    TELL_USAGE("tell.usage", "&cUse: /tell <player> <message>"),
    TELL_SPY_ENABLED("tell.spy.enabled", "&aTell spy enabled!"),
    TELL_SPY_DISABLED("tell.spy.disabled", "&cTell spy disabled!"),

    RELOAD_SUCCESS("reload_success", "&aReloaded successfully!"),

    ;private final String key;
    private final String defaultValue;

    Messages(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private static final Map<String, Object> MESSAGES = new HashMap<>();

    private static final FileManager fileManager = new FileManager("messages");

    public static void save() {
        MESSAGES.clear();

        for (Messages message : Messages.values()) {
            if (fileManager.contains(message.key)) {
                Object value = fileManager.get(message.key);

                MESSAGES.put(message.key, value == null ? message.defaultValue : value);
            } else {
                MESSAGES.put(message.key, message.defaultValue);
                fileManager.set(message.key, message.defaultValue);
            }
        }

        fileManager.save();
    }

    public static void reload() {
        fileManager.reload();
        save();
    }

    public String asString() {
        return (String) MESSAGES.getOrDefault(this.key, this.defaultValue);
    }
}
