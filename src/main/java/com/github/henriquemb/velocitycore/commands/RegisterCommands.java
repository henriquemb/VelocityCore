package com.github.henriquemb.velocitycore.commands;

public class RegisterCommands {
    public static void register() {
        GlobalCommand.register();
        TellCommand.register();
        TellSpyCommand.register();
        VelocityCoreCommand.register();
    }
}
