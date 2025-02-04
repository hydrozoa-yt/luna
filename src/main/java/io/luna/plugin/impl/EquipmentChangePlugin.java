package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.*;
import io.luna.game.model.mob.Player;
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
        Player p = e.getPlr();
        p.interruptAction();
        p.resetInteractingWith();
        p.getInterfaces().close();
        p.getEquipment().equip(e.getIndex());
    }

    @EventListenerAnnotation(WidgetItemClickEvent.WidgetItemFirstClickEvent.class)
    public void handleUnequipItem(WidgetItemClickEvent.WidgetItemFirstClickEvent e) {
        e.getPlr().getEquipment().unequip(e.getIndex());
    }
}
