package io.luna.plugin.listeners;

import io.luna.game.event.impl.ChatEvent;

/**
 * @author hydrozoa
 */
public interface ChatListener extends EventListener<ChatEvent> {

    @Override
    default boolean onEvent(ChatEvent event) {
        return handleChat(event);
    }

    boolean handleChat(ChatEvent e);

}
