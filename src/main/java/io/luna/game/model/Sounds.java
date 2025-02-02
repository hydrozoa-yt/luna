package io.luna.game.model;

public enum Sounds {
    PICK_LOCK(37),
    TELEGRAB(200),
    TELEPORT(202),
    SUPERHEAT(217),
    TELEOTHER(219),
    LOW_ALCHEMY(224),
    BONES_TO_ITEMS(227),
    HIGH_ALCHEMY(223),
    SMELTING(352),
    PICKUP_ITEM(356),
    COOK_FOOD(357),
    BURN_LOG(374),
    LIGHT_FIRE(375),
    DROP_ITEM(376),
    FISH(379),
    BURY_BONE(380),
    UNARMED_BLOCK(406),
    PICKPOCKET_FAILED(458),
    SMITHING(468),
    CUT_TREE_1(471),
    CUT_TREE_2(472),
    TREE_FALLEN(473),
    CRAFT_RUNES(481),
    MINE_ROCK(432),
    PROSPECT_ORE(431),
    MINING_COMPLETED(429),
    TAKE_DAMAGE(816),
    TAKE_DAMAGE_2(822),
    TAKE_DAMAGE_3(823),
    TAKE_DAMAGE_4(824),
    STRING_BOW(1311),
    BURN_LOG_QUICK(2584),
    ;

    private int id;
    private Sounds(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }
}