package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.LoginEvent;
import io.luna.game.model.item.Item;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.PlayerAppearance;
import io.luna.game.model.mob.attr.Attribute;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

import java.util.List;

/**
 * Ported from starterPackage.kts
 *
 * @author hydrozoa
 */
public class StarterPackagePlugin extends Plugin {

    private List<Item> inventoryStarter = List.of(
        new Item(995, 10000), // Coins
        new Item(556, 250), // Air runes
        new Item(555, 250), // Water runes
        new Item(554, 250), // Fire runes
        new Item(557, 250), // Earth runes
        new Item(558, 500), // Mind runes
        new Item(841) // Shortbow
    );

    private List<Item> equipmentStarter = List.of(
        new Item(1153), // Iron full helm
        new Item(1115), // Iron platebody
        new Item(1067), // Iron platelegs
        new Item(1323), // Iron scimitar
        new Item(1191), // Iron kiteshield
        new Item(1731), // Amulet of power
        new Item(4121), // Iron boots
        new Item(1063), // Leather vambraces
        new Item(2570), // Ring of life
        new Item(1019), // Black cape
        new Item(882, 750) // Bronze arrows
    );

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
        Player p = e.getPlr();
        if (!p.getAttributes().has(firstLoginAttribute)) {
            p.getAttributes().set(firstLoginAttribute, firstLoginAttribute.getInitialValue());
            p.getInventory().addAll(inventoryStarter);
            p.getEquipment().addAll(equipmentStarter);
            p.getInterfaces().open(new PlayerAppearance.DesignPlayerInterface());
        }

        if (e.getPlr().getAttributes().get(firstLoginAttribute)) {
            firstLogin(e.getPlr());
        }
    }
}
