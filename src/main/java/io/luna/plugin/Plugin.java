package io.luna.plugin;

import io.luna.LunaContext;

public abstract class Plugin {

    private LunaContext ctx;

    public Plugin(LunaContext ctx) {
        this.ctx = ctx;
    }


}
