package com.github.henriquemb.velocitycore.commands;

import com.github.henriquemb.velocitycore.Model;
import com.github.henriquemb.velocitycore.VelocityCore;
import com.github.henriquemb.velocitycore.settings.Messages;
import com.github.henriquemb.velocitycore.settings.Permissions;
import com.github.henriquemb.velocitycore.settings.Settings;
import com.github.henriquemb.velocitycore.util.ProxyUtils;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.List;

public class VelocityCoreCommand implements SimpleCommand {
    public static void register() {
        CommandMeta velocityCoreCommandMeta = VelocityCore.getCommandManager().metaBuilder("velocitycore")
                .aliases("vc")
                .build();

        VelocityCore.getCommandManager().register(velocityCoreCommandMeta, new VelocityCoreCommand());
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player))
            return;

        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        Player p = (Player) source;

        if (args.length == 0)
            return;

        if (args[0].equalsIgnoreCase("reload")) {
            if (!source.hasPermission(Permissions.RELOAD.getPermission())) {
                Model.sendMessage(p, Messages.PERMISSION_NO_PERMISSION.asString());
                return;
            }

            Messages.reload();
            Settings.reload();

            Model.sendMessage(p, Messages.RELOAD_SUCCESS.asString());
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        if (invocation.arguments().length > 1)
            return List.of();

        String input = invocation.arguments().length > 0 ? invocation.arguments()[0] : "";

        return ProxyUtils.suggestSubcommands(input);
    }
}
