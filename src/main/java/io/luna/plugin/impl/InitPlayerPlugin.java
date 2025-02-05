package io.luna.plugin.impl;

import io.luna.Luna;
import io.luna.LunaContext;
import io.luna.game.event.impl.LoginEvent;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.PlayerInteraction;
import io.luna.game.model.mob.PlayerRights;
import io.luna.net.msg.out.SkillUpdateMessageWriter;
import io.luna.net.msg.out.UpdateRunEnergyMessageWriter;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

/**
 * Ported from initPlayer.kts
 *
 * @author hydrozoa
 */
public class InitPlayerPlugin extends Plugin {

    public InitPlayerPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListener(type = LoginEvent.class)
    public void onLogin(LoginEvent e) {
        Player p = e.getPlr();

        p.getTabs().resetAll();

        p.getInteractions().show(PlayerInteraction.FOLLOW);
        p.getInteractions().show(PlayerInteraction.TRADE);

        p.getEquipment().loadBonuses();
        p.getInventory().refreshPrimary(p);
        p.getEquipment().refreshPrimary(p);

        p.queue(new UpdateRunEnergyMessageWriter());

        p.getSkills().forEach(it -> p.queue(new SkillUpdateMessageWriter(it.getId())));

        p.getVarpManager().sendAllValues();

        p.sendMessage("Welcome to Luna - the java version");
        if (Luna.settings().game().betaMode() || p.getRights().getClientValue() >= PlayerRights.ADMINISTRATOR.getClientValue()) {
            p.sendMessage("Server currently running in "+Luna.settings().game().runtimeMode()+" mode.");
        }
    }
}
