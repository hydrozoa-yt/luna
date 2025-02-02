package io.luna.plugin.listeners;

import io.luna.game.event.impl.LoginEvent;

public interface LoginListener extends EventListener<LoginEvent> {

    @Override
    default boolean onEvent(LoginEvent event) {
        onLogin(event);
        return false;
    }

    void onLogin(LoginEvent e);
}
