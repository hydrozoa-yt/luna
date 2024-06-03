package io.luna.game.event.impl;

import io.luna.game.model.mob.Npc;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.controller.ControllableEvent;
import io.luna.game.model.object.GameObject;

/**
 * A use-item with something event. Not intended for interception.
 *
 * @author lare96
 */
public class UseItemEvent extends PlayerEvent implements ControllableEvent {

    /**
     * An event sent when a player uses an item on another item.
     *
     * @author lare96
     */
    public static final class ItemOnItemEvent extends UseItemEvent implements ControllableEvent {

        /**
         * The target item identifier.
         */
        private final int targetItemId;

        /**
         * The target item index.
         */
        private final int targetItemIndex;

        /**
         * The target interface identifier.
         */
        private final int targetItemInterface;

        /**
         * Creates a new {@link ItemOnItemEvent}.
         *
         * @param player The player.
         * @param usedId the used id
         * @param targetItemId The target item identifier.
         * @param usedIndex the used index
         * @param targetItemIndex The target item index.
         * @param usedInterfaceId the used interface id
         * @param targetItemInterface The target interface identifier.
         */
        public ItemOnItemEvent(Player player, int usedId, int targetItemId, int usedIndex, int targetItemIndex,
                               int usedInterfaceId, int targetItemInterface) {
            super(player, usedId, usedIndex, usedInterfaceId);
            this.targetItemId = targetItemId;
            this.targetItemIndex = targetItemIndex;
            this.targetItemInterface = targetItemInterface;
        }

        /**
         * Gets target item id.
         *
         * @return The target item identifier.
         */
        public int getTargetItemId() {
            return targetItemId;
        }

        /**
         * Gets target item index.
         *
         * @return The target item index.
         */
        public int getTargetItemIndex() {
            return targetItemIndex;
        }

        /**
         * Gets target item interface.
         *
         * @return The target interface identifier.
         */
        public int getTargetItemInterface() {
            return targetItemInterface;
        }
    }

    /**
     * An event sent when a player uses an item on a npc.
     *
     * @author lare96
     */
    public static final class ItemOnNpcEvent extends UseItemEvent implements ControllableEvent {

        /**
         * The target npc.
         */
        private final Npc targetNpc;

        /**
         * Creates a new {@link ItemOnNpcEvent}.
         *
         * @param player The player.
         * @param usedId The used item identifier.
         * @param usedIndex The used item index.
         * @param usedInterfaceId The used interface identifier.
         * @param targetNpc The target npc.
         */
        public ItemOnNpcEvent(Player player, int usedId, int usedIndex, int usedInterfaceId, Npc targetNpc) {
            super(player, usedId, usedIndex, usedInterfaceId);
            this.targetNpc = targetNpc;
        }

        /**
         * @return The target npc.
         */
        public Npc getTargetNpc() {
            return targetNpc;
        }
    }

    /**
     * An event sent when a player uses an item on an object.
     *
     * @author lare96
     */
    public static final class ItemOnObjectEvent extends UseItemEvent implements ControllableEvent {

        /**
         * The object.
         */
        private final GameObject gameObject;

        /**
         * Creates a new {@link ItemOnObjectEvent}.
         *
         * @param player The player.
         * @param itemId The item identifier.
         * @param itemIndex The item index.
         * @param itemInterfaceId The item interface identifier.
         * @param gameObject The object.
         */
        public ItemOnObjectEvent(Player player, int itemId, int itemIndex, int itemInterfaceId, GameObject gameObject) {
            super(player, itemId, itemIndex, itemInterfaceId);
            this.gameObject = gameObject;
        }

        /**
         * @return The object identifier.
         */
        public int getObjectId() {
            return gameObject.getId();
        }

        /**
         * @return The object.
         */
        public GameObject getGameObject() {
            return gameObject;
        }
    }

    /**
     * An event sent when a player uses an item on a player.
     *
     * @author lare96
     */
    public static final class ItemOnPlayerEvent extends UseItemEvent implements ControllableEvent {

        /**
         * The target player.
         */
        private final Player targetPlayer;

        /**
         * Creates a new {@link ItemOnPlayerEvent}.
         *
         * @param player The player.
         * @param usedId The used item identifier.
         * @param usedIndex The used item index.
         * @param usedInterfaceId The used interface identifier.
         * @param targetPlayer The target player.
         */
        public ItemOnPlayerEvent(Player player, int usedId, int usedIndex, int usedInterfaceId, Player targetPlayer) {
            super(player, usedId, usedIndex, usedInterfaceId);
            this.targetPlayer = targetPlayer;
        }

        /**
         * @return The target player.
         */
        public Player getTargetPlayer() {
            return targetPlayer;
        }
    }

    /**
     * The used item identifier.
     */
    private final int usedItemId;

    /**
     * The used item index.
     */
    private final int usedItemIndex;

    /**
     * The used item interface identifier.
     */
    private final int usedItemInterface;

    /**
     * Creates a new {@link UseItemEvent}.
     *
     * @param player the player
     * @param usedItemId The used item identifier.
     * @param usedItemIndex The used item index.
     * @param usedItemInterface The used item interface identifier.
     */
    private UseItemEvent(Player player, int usedItemId, int usedItemIndex, int usedItemInterface) {
        super(player);
        this.usedItemId = usedItemId;
        this.usedItemIndex = usedItemIndex;
        this.usedItemInterface = usedItemInterface;
    }

    /**
     * @return The used item identifier.
     */
    public int getUsedItemId() {
        return usedItemId;
    }

    /**
     * @return The used item index.
     */
    public int getUsedItemIndex() {
        return usedItemIndex;
    }

    /**
     * @return The used item interface identifier.
     */
    public int getUsedItemInterface() {
        return usedItemInterface;
    }

}
