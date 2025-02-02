package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.CommandEvent;
import io.luna.plugin.Plugin;
import io.luna.plugin.listeners.CommandListener;

public class CommandPlugin extends Plugin implements CommandListener {

    public CommandPlugin(LunaContext ctx) {
        super(ctx);
    }

    @Override
    public boolean handleCommand(CommandEvent e) {
        e.getPlr().sendMessage("I saw you do that.");
        return false;
    }
}
