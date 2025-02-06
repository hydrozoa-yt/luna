package io.luna.plugin;

import io.luna.game.event.Event;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.game.event.impl.ObjectClickEvent;
import io.luna.game.event.impl.WidgetItemClickEvent;

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
        } else if (e instanceof WidgetItemClickEvent.WidgetItemFirstClickEvent) {
            WidgetItemClickEvent.WidgetItemFirstClickEvent wice = (WidgetItemClickEvent.WidgetItemFirstClickEvent) e;
            filter = String.valueOf(wice.getWidgetId());
        } else if (e instanceof WidgetItemClickEvent.WidgetItemSecondClickEvent) {
            WidgetItemClickEvent.WidgetItemSecondClickEvent wice = (WidgetItemClickEvent.WidgetItemSecondClickEvent) e;
            filter = String.valueOf(wice.getWidgetId());
        } else if (e instanceof WidgetItemClickEvent.WidgetItemThirdClickEvent) {
            WidgetItemClickEvent.WidgetItemThirdClickEvent wice = (WidgetItemClickEvent.WidgetItemThirdClickEvent) e;
            filter = String.valueOf(wice.getWidgetId());
        } else if (e instanceof WidgetItemClickEvent.WidgetItemFourthClickEvent) {
            WidgetItemClickEvent.WidgetItemFourthClickEvent wice = (WidgetItemClickEvent.WidgetItemFourthClickEvent) e;
            filter = String.valueOf(wice.getWidgetId());
        } else if (e instanceof WidgetItemClickEvent.WidgetItemFifthClickEvent) {
            WidgetItemClickEvent.WidgetItemFifthClickEvent wice = (WidgetItemClickEvent.WidgetItemFifthClickEvent) e;
            filter = String.valueOf(wice.getWidgetId());
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
