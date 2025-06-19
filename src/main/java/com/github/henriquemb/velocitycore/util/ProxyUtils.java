package com.github.henriquemb.velocitycore.util;

import com.github.henriquemb.velocitycore.VelocityCore;
import com.github.henriquemb.velocitycore.settings.Permissions;
import com.velocitypowered.api.proxy.Player;

import java.util.List;

public class ProxyUtils {
    public static String getServerName(Player player) {
        return TextUtils.capitalize(player.getCurrentServer()
                .map(serverConnection -> serverConnection.getServerInfo().getName())
                .orElse("Desconhecido"));
    }

    public static List<String> suggestPlayers(String input) {
        return VelocityCore.getProxy().getAllPlayers().stream()
                .filter(player -> !player.hasPermission(Permissions.TELL_INVISIBLE.getPermission()) && player.isActive() && (input.isBlank() || player.getUsername().toLowerCase().startsWith(input.toLowerCase())))
                .map(Player::getUsername)
                .toList();
    }

    public static List<String> suggestSubcommands(String input) {
        List<String> subCommands = List.of("reload");

        return subCommands.stream()
                .filter(subcommand -> input.isBlank() || subcommand.toLowerCase().startsWith(input.toLowerCase()))
                .toList();
    }
}
