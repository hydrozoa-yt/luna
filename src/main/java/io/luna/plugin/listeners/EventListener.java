package io.luna.plugin.listeners;

import io.luna.game.event.Event;

/**
 * @author hydrozoa
 */
public interface EventListener<T extends Event> {

    /**
     * Called when an Event of this listeners type is received
     * @param event event that just happened
     * @return      true if the event was handled here
     */
    boolean onEvent(T event);
}
