package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.*;
import io.luna.plugin.EventListenerAnnotation;
import io.luna.plugin.Plugin;

/**
 * Ported from equipmentChange.kts
 *
 * @author hydrozoa
 */
public class EquipmentChangePlugin extends Plugin {

    public EquipmentChangePlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListenerAnnotation(EquipItemEvent.class)
    public void handleEquipItem(EquipItemEvent e) {
        e.getPlr().sendMessage("Tried to equip item.");
    }

    @EventListenerAnnotation(WidgetItemClickEvent.WidgetItemFirstClickEvent.class)
    public void handleUnequip(WidgetItemClickEvent.WidgetItemFirstClickEvent e) {
        e.getPlr().sendMessage("Tried to unequip item.");
    }
}
