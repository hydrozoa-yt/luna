package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.CommandEvent;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.PlayerRights;
import io.luna.game.model.mob.Skill;
import io.luna.game.model.mob.SkillSet;
import io.luna.plugin.Plugin;
import io.luna.plugin.listeners.CommandListener;

public class CommandPlugin extends Plugin implements CommandListener {

    public CommandPlugin(LunaContext ctx) {
        super(ctx);
    }

    @Override
    public boolean handleCommand(CommandEvent e) {
        e.getPlr().sendMessage("I saw you do that.");

        Player p = e.getPlr();

        switch (e.getName()) {
            case "master":
                if (p.getRights() != PlayerRights.ADMINISTRATOR) {
                    return false;
                }

                p.getSkills().forEach(it -> it.setExperience(SkillSet.MAXIMUM_EXPERIENCE));
                p.getSkills().getSkill(Skill.HITPOINTS).setLevel(99);
                p.sendMessage("You have set all your skills to level 99.");
                return true;
        }
        return false;
    }
}
