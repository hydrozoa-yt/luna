package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.game.model.mob.Player;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

/**
 * Ported from toggle.kts and clickLogout.kts
 *
 * @author hydrozoa
 */
public class ActionButtonPlugin extends Plugin {

    public ActionButtonPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListener(value = ButtonClickEvent.class)
    public void handleButton(ButtonClickEvent e) {
        Player p = e.getPlr();
        switch (e.getId()) {
            case 2458:          // logout button
                p.logout();
                break;
            case 152:           // disable running
                p.setRunning(false);
                break;
            case 153:           // enable running
                p.setRunning(true);
                break;
        }
    }
}
