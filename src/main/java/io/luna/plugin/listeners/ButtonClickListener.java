package io.luna.plugin.listeners;

import io.luna.game.event.impl.ButtonClickEvent;

public interface ButtonClickListener extends EventListener<ButtonClickEvent> {

    @Override
    default boolean onEvent(ButtonClickEvent event) {
        return handleButton(event);
    }

    boolean handleButton(ButtonClickEvent e);
}
