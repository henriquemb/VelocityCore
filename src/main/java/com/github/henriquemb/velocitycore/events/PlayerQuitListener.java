package com.github.henriquemb.velocitycore.events;

import com.github.henriquemb.velocitycore.Model;
import com.github.henriquemb.velocitycore.VelocityCore;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;

public class PlayerQuitListener {
    private static final Model model = VelocityCore.getModel();

    @Subscribe
    public void onQuit(DisconnectEvent e) {
        Player player = e.getPlayer();

        model.getTellReply().remove(player);
        model.getTellSpy().remove(player);
    }
}
