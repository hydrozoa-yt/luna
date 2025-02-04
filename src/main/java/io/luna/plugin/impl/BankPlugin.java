package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.ObjectClickEvent;
import io.luna.plugin.EventListenerAnnotation;
import io.luna.plugin.Plugin;

import java.util.Set;

/**
 * Ported from openBank.kts and openDepositBox.kts
 *
 * @author hydrozoa
 */
public class BankPlugin extends Plugin {

    private Set<Integer> depositBoxes = Set.of(9398);

    private Set<Integer> bankObjects = Set.of(3193, 2213, 3095);

    public BankPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListenerAnnotation(ObjectClickEvent.ObjectFirstClickEvent.class)
    public void handleObjectFirstClick(ObjectClickEvent.ObjectFirstClickEvent e) {
        if (!bankObjects.contains(e.getId())) {
            return;
        }

        e.getPlr().getBank().open();
    }
}
