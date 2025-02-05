package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.LoginEvent;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.attr.Attribute;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

/**
 * Ported from starterPackage.kts
 *
 * @author hydrozoa
 */
public class StarterPackagePlugin extends Plugin {

    private Attribute<Boolean> firstLoginAttribute = new Attribute<>(true).persist("first_login");

    public StarterPackagePlugin(LunaContext ctx) {
        super(ctx);
    }

    private void firstLogin(Player p) {
        p.sendMessage("This was the first login.");
        p.getAttributes().set(firstLoginAttribute, false);
    }

    @EventListener(type = LoginEvent.class)
    public void handleLogin(LoginEvent e) {
        if (!e.getPlr().getAttributes().has(firstLoginAttribute)) {
            e.getPlr().getAttributes().set(firstLoginAttribute, firstLoginAttribute.getInitialValue());
        }

        if (e.getPlr().getAttributes().get(firstLoginAttribute)) {
            firstLogin(e.getPlr());
        }
    }
}
