package io.luna.plugin;

import io.luna.LunaContext;
import io.luna.game.event.Event;
import io.luna.game.event.impl.ButtonClickEvent;
import io.luna.game.event.impl.CommandEvent;
import io.luna.game.event.impl.LoginEvent;
import io.luna.game.event.impl.SkillChangeEvent;
import io.luna.plugin.impl.ActionButtonPlugin;
import io.luna.plugin.impl.AdvanceLevelPlugin;
import io.luna.plugin.impl.CommandPlugin;
import io.luna.plugin.impl.InitPlayerPlugin;
import io.luna.plugin.listeners.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages all java plugins loaded and dispatches events to their listeners.
 * Eventually these would be loaded from a seperate directory.
 *
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
        List<Plugin> plugins = List.of(
                new InitPlayerPlugin(ctx),
                new CommandPlugin(ctx),
                new ActionButtonPlugin(ctx),
                new AdvanceLevelPlugin(ctx)
        );
        for (Plugin p : plugins) {
            loadPlugin(p);
        }
    }

    private void loadPlugin(Plugin plugin) {
        Class pluginClass = plugin.getClass();
        if (LoginListener.class.isAssignableFrom(pluginClass)) {
            LoginListener loginListener = (LoginListener) plugin; // Safe cast here
            addEventListener(loginListener, LoginEvent.class);
        }
        if (CommandListener.class.isAssignableFrom(pluginClass)) {
            CommandListener commandListener = (CommandListener) plugin; // Safe cast here
            addEventListener(commandListener, CommandEvent.class);
        }
        if (ButtonClickListener.class.isAssignableFrom(pluginClass)) {
            ButtonClickListener buttonListener = (ButtonClickListener) plugin; // Safe cast here
            addEventListener(buttonListener, ButtonClickEvent.class);
        }
        if (SkillChangeListener.class.isAssignableFrom(pluginClass)) {
            SkillChangeListener skillChangeListener = (SkillChangeListener) plugin; // Safe cast here
            addEventListener(skillChangeListener, SkillChangeEvent.class);
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
