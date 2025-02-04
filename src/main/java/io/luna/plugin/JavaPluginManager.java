package io.luna.plugin;

import io.luna.LunaContext;
import io.luna.game.event.Event;
import io.luna.plugin.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all java plugins loaded and dispatches events to their listeners.
 * Eventually these would be loaded from a seperate directory.
 *
 * todo: validate that listeners have the correct parameter type when loading
 *
 * @author hydrozoa
 */
public class JavaPluginManager {

    private final LunaContext ctx;

    private Map<Class<? extends Event>, List<Method>> listeners = new HashMap<>();

    private Map<Method, Plugin> handlerInstances = new HashMap<>();

    private int listenersLoaded = 0;

    private int pluginsLoaded = 0;

    /**
     * The asynchronous logger.
     */
    private static final Logger logger = LogManager.getLogger();

    public JavaPluginManager(LunaContext ctx) {
        this.ctx = ctx;
        loadPlugins();
    }

    public void loadPlugins() {
        List<Plugin> plugins = List.of(
                new InitPlayerPlugin(ctx),
                new CommandPlugin(ctx),
                new ActionButtonPlugin(ctx),
                new AdvanceLevelPlugin(ctx),
                new EquipmentChangePlugin(ctx),
                new BankPlugin(ctx),
                new StarterPackagePlugin(ctx)
        );
        for (Plugin p : plugins) {
            loadPlugin(p);
        }
        logger.info("Loaded "+pluginsLoaded+" java plugins and "+listenersLoaded+" listeners");
    }

    private void loadPlugin(Plugin plugin) {
        for (Method m : plugin.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(EventListenerAnnotation.class)) {
                EventListenerAnnotation annotation = m.getAnnotation(EventListenerAnnotation.class);
                Class<? extends Event> eventType = annotation.value();
                addEventListener(m, plugin, eventType);
            }
        }
        pluginsLoaded++;
    }

    private void addEventListener(Method listener, Plugin plugin, Class<? extends Event> type) {
        List<Method> listenerList = listeners.get(type);

        if (listenerList == null) {
            List newList = new ArrayList<>();
            listeners.put(type, newList);
            listenerList = newList;
        }

        listenerList.add(listener);
        handlerInstances.put(listener, plugin);
        listenersLoaded++;
    }

    /**
     * Traverses the event across its designated pipeline.
     *
     * @param msg The event to post.
     */
    public <E extends Event> void post(E msg) {
        if (!listeners.containsKey(msg.getClass())) {
            return;
        }
        for (Method m : listeners.get(msg.getClass())) {
            Class<?> methodDeclaringClass = m.getDeclaringClass();
            Plugin p = handlerInstances.get(m);
            Object instanceToInvokeOn = methodDeclaringClass.cast(p); // Safe cast
            try {
                m.invoke(instanceToInvokeOn, msg);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}