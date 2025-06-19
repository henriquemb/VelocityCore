package com.github.henriquemb.velocitycore.commands;

import com.github.henriquemb.velocitycore.Model;
import com.github.henriquemb.velocitycore.VelocityCore;
import com.github.henriquemb.velocitycore.settings.Messages;
import com.github.henriquemb.velocitycore.settings.Permissions;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class TellSpyCommand implements SimpleCommand {
    private static final String PERMISSION = Permissions.TELL_SPY.getPermission();

    public static void register() {
        CommandMeta tellspyCommandMeta = VelocityCore.getCommandManager().metaBuilder("tellspy")
                .build();

        VelocityCore.getCommandManager().register(tellspyCommandMeta, new TellSpyCommand());
    }

    @Override
    public void execute(Invocation invocation) {
        if (!(invocation.source() instanceof Player))
            return;

        CommandSource source = invocation.source();

        Player p = (Player) source;

        if (VelocityCore.getModel().getTellSpy().contains(p)) {
            VelocityCore.getModel().getTellSpy().remove(p);
            Model.sendMessage(p, Messages.TELL_SPY_DISABLED.asString());
        } else {
            VelocityCore.getModel().getTellSpy().add(p);
            Model.sendMessage(p, Messages.TELL_SPY_ENABLED.asString());
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(PERMISSION);
    }
}
