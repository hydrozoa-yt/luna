package io.luna.plugin.impl.skill.woodcutting;

import api.predef.*;
import io.luna.game.model.item.Equipment;
import io.luna.game.model.item.Item;
import io.luna.game.model.mob.Skill;
import io.luna.game.model.mob.block.Animation;
import io.luna.game.model.mob.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumerated type representing all axes that can be used to cut [Tree]s.
 * Ported from Axe.kt.
 *
 * @author laree96
 * @author hydrozoa
 */
public enum Axe {
    BRONZE(1351, 1, new Animation(879), 1),
    IRON(1349, 1, new Animation(877), 2),
    STEEL(1353, 6, new Animation(875), 4),
    BLACK(1361, 6, new Animation(875), 4),
    MITHRIL(1355, 21, new Animation(871), 6),
    ADAMANT(1357, 31, new Animation(869), 7),
    RUNE(1359, 41, new Animation(867), 8),
    DRAGON(6739, 61, new Animation(2846), 10);

    private static final Map<Integer, Axe> idToAxe;

    private int id, level, strength;
    private Animation animation;

    Axe (int id, int level, Animation animation, int strength){
        this.id = id;
        this.level = level;
        this.animation = animation;
        this.strength = strength;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public Animation getAnimation() {
        return animation;
    }

    static {
        idToAxe = new HashMap<>();
        for (Axe a : Axe.values()) {
            idToAxe.put(a.id, a);
        }
    }

    /**
     * Determines which axe to use (based on equipment and inventory).
     */
    public static Axe computeAxeType(Player p) {
        Axe a = null;
        Item equippedWeapon = p.getEquipment().get(Equipment.WEAPON);
        if (equippedWeapon != null) {
            Axe equippedAxe = idToAxe.get(equippedWeapon.getId());
            if (equippedAxe != null && p.getSkills().getSkill(Skill.WOODCUTTING).getLevel() >= equippedAxe.level) {
                a = equippedAxe;
            }
        }

        for (Item invItem : p.getInventory()) {
            if (invItem == null) {
                continue;
            }
            Axe invAxe = idToAxe.get(invItem.getId());
            if (invAxe != null
                    && p.getSkills().getSkill(Skill.WOODCUTTING).getLevel() >= invAxe.level
                    && (a == null || invAxe.strength > a.strength)) {
                a = invAxe;
            }
        }
        return a;
    }

    /**
     * Determines if the player has an axe.
     */
    private boolean hasAxe(Player p, Axe axe) {
        boolean equippedAxe = p.getEquipment().nonNullGet(Equipment.WEAPON).map(item -> item.getId() == axe.id).orElse(false);
        boolean inventoryAxe = p.getInventory().contains(axe.id);

        return equippedAxe || inventoryAxe;
    }
}