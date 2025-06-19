package com.github.henriquemb.velocitycore;

import com.github.henriquemb.velocitycore.enums.ColorCodeLegacy;
import com.velocitypowered.api.proxy.Player;
import lombok.Data;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Model {
    private final Map<Player, Player> tellReply = new HashMap<>();
    private final Set<Player> tellSpy = new HashSet<>();

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void sendMessage(Player player, String message) {
        message = ColorCodeLegacy.translateLegacyCodes(message);

        player.sendMessage(miniMessage.deserialize(message));
    }
}
