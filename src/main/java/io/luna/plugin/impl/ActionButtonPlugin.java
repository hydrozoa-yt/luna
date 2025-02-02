package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.plugin.Plugin;
import io.luna.plugin.listeners.ButtonClickListener;

/**
 * @author hydrozoa
 */
public class ActionButtonPlugin extends Plugin implements ButtonClickListener {

    public ActionButtonPlugin(LunaContext ctx) {
        super(ctx);
    }

    @Override
    public boolean handleButton(ButtonClickEvent e) {
        System.out.println("made it click");
        switch (e.getId()) {
            case 2458:          // logout button
                e.getPlr().logout();
                return true;
        }
        return false;
    }
}
