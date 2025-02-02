package io.luna.plugin;

import io.luna.LunaContext;
import io.luna.game.event.Event;
import io.luna.game.event.impl.CommandEvent;
import io.luna.plugin.impl.CommandPlugin;
import io.luna.plugin.listeners.CommandListener;
import io.luna.plugin.listeners.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hydrozoa
 */
public class JavaPluginManager {

    private final LunaContext ctx;

    private Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

    public JavaPluginManager(LunaContext ctx) {
        this.ctx = ctx;
        loadPlugins();
    }

    public void loadPlugins() {
        loadPlugin(new CommandPlugin(ctx));
    }

    private void loadPlugin(Plugin plugin) {
        Class pluginClass = plugin.getClass();
        if (CommandListener.class.isAssignableFrom(pluginClass)) {
            CommandListener commandListener = (CommandListener) plugin; // Safe cast here
            addEventListener(commandListener, CommandEvent.class);
        }
    }

    private void addEventListener(EventListener listener, Class<? extends Event> type) {
        List<EventListener> listenerList = listeners.get(type);

        if (listenerList == null) {
            List newList = new ArrayList<>();
            listeners.put(type, newList);
            listenerList = newList;
        }

        listenerList.add(listener);
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
        for (EventListener e : listeners.get(msg.getClass())) {
            e.onEvent(msg);
        }
    }
}
