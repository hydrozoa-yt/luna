package io.luna.plugin.listeners;

import io.luna.game.event.impl.CommandEvent;

/**
 * @author hydrozoa
 */
public interface CommandListener extends EventListener<CommandEvent> {

    @Override
    default boolean onEvent(CommandEvent event) {
        return handleCommand(event);
    }

    boolean handleCommand(CommandEvent e);

}
