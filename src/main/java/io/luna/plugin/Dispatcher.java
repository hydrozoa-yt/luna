package io.luna.plugin;

import io.luna.game.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Dispatcher delivers events from JavaPluginManager.java to listeners.
 * There is only one Dispatcher per JavaPluginManager.
 *
 * @author hydrozoa
 */
public class Dispatcher {

    /**
     * Types of events mapped to filters that can delegate events to handlers only subscribed to specific filtered events
     */
    private Map<Class<? extends Event>, FilteredDispatcher> eventFilters = new HashMap<>();

    /**
     * Types of events mapped to methods that subscribe to all events of a specific type
     */
    private Map<Class<? extends Event>, List<Method>> eventGlobalHandlers = new HashMap<>();

    /**
     * Methods mapped to the instance on which they are invoked
     */
    private Map<Method, Plugin> handlerInstances = new HashMap<>();

    /**
     * Traverses the event across its designated pipeline.
     *
     * @param msg The event to post.
     */
    public <E extends Event> void post(E msg) {
        if (eventFilters.containsKey(msg.getClass())) {
            FilteredDispatcher fp = eventFilters.get(msg.getClass());
            fp.post(msg);
        }

        if (eventGlobalHandlers.containsKey(msg.getClass())) {
            List<Method> methods = eventGlobalHandlers.get(msg.getClass());
            for (Method m : methods) {
                try {
                    m.invoke(handlerInstances.get(m), msg);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void loadPlugin(Plugin plugin) {
        for (Method m : plugin.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(EventListener.class)) {
                EventListener annotation = m.getAnnotation(EventListener.class);
                Class<? extends Event> eventType = annotation.type();
                if (!annotation.filter().isBlank()) {
                    String filter = annotation.filter();
                    addFilteredEventListener(m, plugin, eventType, filter);
                } else {
                    addGlobalEventListener(m, plugin, eventType);
                }
            }
        }
    }

    private void addGlobalEventListener(Method m, Plugin p, Class<? extends Event> type) {
        if (!eventGlobalHandlers.containsKey(type)) {
            eventGlobalHandlers.put(type, new ArrayList<Method>());
        }

        eventGlobalHandlers.get(type).add(m);
        handlerInstances.put(m, p);
    }

    private void addFilteredEventListener(Method m, Plugin p, Class<? extends Event> type, String filter) {
        if (!eventFilters.containsKey(type)) {
            eventFilters.put(type, new FilteredDispatcher(this));
        }

        eventFilters.get(type).addListener(m, filter);
        handlerInstances.put(m, p);
    }

    public Plugin getInstanceForMethod(Method m) {
        return handlerInstances.get(m);
    }
}
