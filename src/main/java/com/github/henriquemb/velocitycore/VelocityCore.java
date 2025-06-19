package com.github.henriquemb.velocitycore;

import com.github.henriquemb.velocitycore.commands.RegisterCommands;
import com.github.henriquemb.velocitycore.events.RegisterListeners;
import com.github.henriquemb.velocitycore.settings.Messages;
import com.github.henriquemb.velocitycore.settings.Settings;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "velocitycore", name = "VelocityCore", version = "1.0-SNAPSHOT")
public class VelocityCore {

    @Getter @Setter(AccessLevel.PRIVATE)
    private static Logger logger;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static ProxyServer proxy;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static VelocityCore instance;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static CommandManager commandManager;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static EventManager eventManager;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static Model model;

    @Getter @Setter(AccessLevel.PRIVATE)
    private static Path dataDirectory;

    @Inject
    public VelocityCore(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        setProxy(proxy);
        setLogger(logger);
        setDataDirectory(dataDirectory);
        setInstance(this);

        setModel(new Model());
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        Messages.save();
        Settings.save();

        setCommandManager(proxy.getCommandManager());
        setEventManager(proxy.getEventManager());

        // Register commands
        RegisterCommands.register();

        // Register event listeners
        RegisterListeners.register();

        proxy.getConsoleCommandSource().sendMessage(
                Component.text("[VelocityCore] VelocityCore has been successfully loaded!", NamedTextColor.GREEN)
        );
    }
}
