package io.luna.plugin.impl.skill.woodcutting;

import io.luna.LunaContext;
import io.luna.game.event.impl.ObjectClickEvent;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

/**
 * Ported from cutTree.kt
 *
 * @author lare96
 * @author hydrozoa
 */
public class WoodcuttingPlugin extends Plugin {

    public WoodcuttingPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListener(type = ObjectClickEvent.ObjectFirstClickEvent.class)
    public void handleChopTree() {

    }
}
