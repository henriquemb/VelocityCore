package com.github.henriquemb.velocitycore.events;

import com.github.henriquemb.velocitycore.VelocityCore;
import com.velocitypowered.api.event.EventManager;

public class RegisterListeners {
    private static final VelocityCore instance = VelocityCore.getInstance();
    private static final EventManager eventManager = VelocityCore.getEventManager();

    public static void register() {
        eventManager.register(instance, new PlayerQuitListener());
    }
}
