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

import java.util.Arrays;
import java.util.List;

public class TellCommand implements SimpleCommand {
    private static final String PERMISSION = Permissions.TELL.getPermission();

    public static void register() {
        CommandMeta tellCommandMeta = VelocityCore.getCommandManager().metaBuilder("tell")
                .aliases("msg", "whisper", "w", "reply", "r")
                .build();

        VelocityCore.getCommandManager().register(tellCommandMeta, new TellCommand());
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player))
            return;

        CommandSource source = invocation.source();
        List<String> args = Arrays.stream(invocation.arguments()).filter(msg -> !msg.isBlank()).toList();

        Player p = (Player) source;

        Player t = null;
        boolean isReply = false;

        if (invocation.alias().equalsIgnoreCase("reply") || invocation.alias().equalsIgnoreCase("r")) {
            t = VelocityCore.getModel().getTellReply().get(p);
            isReply = true;
        }

        if ((isReply && args.isEmpty()) || !isReply && args.size() <= 1) {
            Model.sendMessage(p, Messages.TELL_USAGE.asString());
            return;
        }

        if (!isReply)
            t = VelocityCore.getProxy().getPlayer(args.get(0)).orElse(null);

        if (t == null || !t.isActive()) {
            Model.sendMessage(p, Messages.PLAYER_NOT_FOUND.asString());
            return;
        }

        if (t.equals(p)) {
            Model.sendMessage(p, Messages.PLAYER_YOURSELF.asString());
            return;
        }

        String serverSender = ProxyUtils.getServerName(p);
        String serverReceiver = ProxyUtils.getServerName(t);

        String message = String.join(" ", isReply ? args : args.subList(1, args.size()));

        VelocityCore.getModel().getTellReply().put(t, p);

        String formattedMessage = Settings.TELL_CHAT_FORMAT.asString()
                .replace(Placeholders.PLAYER_NAME.getPlaceholder(), p.getUsername())
                .replace(Placeholders.TARGET_PLAYER_NAME.getPlaceholder(), t.getUsername())
                .replace(Placeholders.SERVER_NAME.getPlaceholder(), serverSender)
                .replace(Placeholders.TARGET_SERVER_NAME.getPlaceholder(), serverReceiver)
                .replace(Placeholders.MESSAGE.getPlaceholder(), message);

        String formattedSpyMessage = Settings.TELL_SPY_CHAT_FORMAT.asString()
                .replace(Placeholders.PLAYER_NAME.getPlaceholder(), p.getUsername())
                .replace(Placeholders.TARGET_PLAYER_NAME.getPlaceholder(), t.getUsername())
                .replace(Placeholders.SERVER_NAME.getPlaceholder(), serverSender)
                .replace(Placeholders.TARGET_SERVER_NAME.getPlaceholder(), serverReceiver)
                .replace(Placeholders.MESSAGE.getPlaceholder(), message);

        Model.sendMessage(p, formattedMessage);
        Model.sendMessage(t, formattedMessage);

        final Player target = t;
        VelocityCore.getModel().getTellSpy().stream()
                .filter(player -> !player.equals(p) && !player.equals(target))
                .forEach(player -> {
                    Model.sendMessage(player, formattedSpyMessage);
                });
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(PERMISSION);
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length > 1)
            return List.of();

        String input = invocation.arguments().length > 0 ? invocation.arguments()[0] : "";

        return ProxyUtils.suggestPlayers(input);
    }
}
