package com.github.henriquemb.velocitycore.commands;

import com.github.henriquemb.velocitycore.Model;
import com.github.henriquemb.velocitycore.VelocityCore;
import com.github.henriquemb.velocitycore.enums.Placeholders;
import com.github.henriquemb.velocitycore.settings.Messages;
import com.github.henriquemb.velocitycore.settings.Permissions;
import com.github.henriquemb.velocitycore.settings.Settings;
import com.github.henriquemb.velocitycore.util.ProxyUtils;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class GlobalCommand implements SimpleCommand {
    private static final String PERMISSION = Permissions.GLOBAL.getPermission();

    public static void register() {
        CommandMeta globalCommandMeta = VelocityCore.getCommandManager().metaBuilder("global")
                .aliases("vg")
                .build();

        VelocityCore.getCommandManager().register(globalCommandMeta, new GlobalCommand());
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player))
            return;

        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        Player p = (Player) source;

        if (args.length == 0) {
            Model.sendMessage(p, Messages.GLOBAL_USAGE.asString());
            return;
        }

        String message = String.join(" ", args);
        String server = ProxyUtils.getServerName(p);

        String formattedMessage = Settings.GLOBAL_CHAT_FORMAT.asString()
                .replace(Placeholders.PLAYER_NAME.getPlaceholder(), p.getUsername())
                .replace(Placeholders.SERVER_NAME.getPlaceholder(), server)
                .replace(Placeholders.MESSAGE.getPlaceholder(), message);

        VelocityCore.getProxy().getAllPlayers().stream()
                .filter(player -> player.hasPermission(PERMISSION))
                .forEach(onlinePlayer -> Model.sendMessage(onlinePlayer, formattedMessage));
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(PERMISSION);
    }
}
