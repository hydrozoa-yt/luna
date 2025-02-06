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

    private Dispatcher dispatcher = new Dispatcher();

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
            dispatcher.loadPlugin(p);
        }
        logger.info("Loaded "+dispatcher.getPluginsLoaded()+" java plugins and "+dispatcher.getListenersLoaded()+" listeners");
    }

    /**
     * Send the event through to the listeners.
     *
     * @param msg The event to post.
     */
    public <E extends Event> void post(E msg) {
        dispatcher.post(msg);
    }
}