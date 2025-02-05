package io.luna.plugin;

import io.luna.game.event.Event;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.game.event.impl.ObjectClickEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class dispatches a certain type of events to listeners that subscribe to a specific subset of this subclass of Event.
 * It could be a specific object id, or npc id.
 * There is up to one FilteredDispatcher per Event type.
 *
 * @author hydrozoa
 */
public class FilteredDispatcher {

    /**
     * Listeners mapped by their filters, could be "obj_first_click 1"
     */
    private Map<String, List<Method>> listeners = new HashMap<>();

    private Dispatcher parent;

    public FilteredDispatcher(Dispatcher parent) {
        this.parent = parent;
    }

    public void addListener(Method m, String filter) {
        if (!listeners.containsKey(filter)) {
            listeners.put(filter, new ArrayList<>());
        }

        listeners.get(filter).add(m);
    }

    public <E extends Event> void post(E e) {
        String filter = "";
        if (e instanceof ObjectClickEvent) {
            ObjectClickEvent oce = (ObjectClickEvent) e;
            filter = String.valueOf(oce.getId());
        } else if (e instanceof ButtonClickEvent) {
            ButtonClickEvent bce = (ButtonClickEvent) e;
            filter = String.valueOf(bce.getId());
        }

        if (listeners.containsKey(filter)) {
            for (Method m : listeners.get(filter)) {
                try {
                    m.invoke(parent.getInstanceForMethod(m), e);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
