package com.github.henriquemb.velocitycore.settings;

import com.github.henriquemb.velocitycore.util.FileManager;

import java.util.HashMap;
import java.util.Map;

public enum Settings {
    GLOBAL_CHAT_FORMAT("global.chat_format", "&d[%server_name%] %player_name%: %message%"),
    TELL_CHAT_FORMAT("tell.chat_format", "&2[<hover:show_text:'%server_name%'>%player_name% -> <hover:show_text:'%target_server_name%'>%target_player_name%] %message%"),
    TELL_SPY_CHAT_FORMAT("tell.spy_chat_format", "&8[<hover:show_text:'%server_name%'>%player_name% -> <hover:show_text:'%target_server_name%'>%target_player_name%] %message%"),

    ;private final String key;
    private final String defaultValue;

    Settings(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    private static final Map<String, Object> SETTINGS = new HashMap<>();

    private static final FileManager fileManager = new FileManager("config");

    public static void save() {
        SETTINGS.clear();

        for (Settings message : Settings.values()) {
            if (fileManager.contains(message.key)) {
                Object value = fileManager.get(message.key);

                SETTINGS.put(message.key, value == null ? message.defaultValue : value);
            } else {
                SETTINGS.put(message.key, message.defaultValue);
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
        return (String) SETTINGS.getOrDefault(this.key, this.defaultValue);
    }
}
