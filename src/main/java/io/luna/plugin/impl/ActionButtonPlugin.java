package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.plugin.EventListenerAnnotation;
import io.luna.plugin.Plugin;

/**
 * @author hydrozoa
 */
public class ActionButtonPlugin extends Plugin {

    public ActionButtonPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListenerAnnotation(ButtonClickEvent.class)
    public boolean handleButton(ButtonClickEvent e) {
        switch (e.getId()) {
            case 2458:          // logout button
                e.getPlr().logout();
                return true;
        }
        return false;
    }
}
