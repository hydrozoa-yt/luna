package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.CommandEvent;
import io.luna.game.model.Position;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.PlayerRights;
import io.luna.game.model.mob.Skill;
import io.luna.game.model.mob.SkillSet;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;

/**
 * Ported from adminCmd.kts
 *
 * @author hydrozoa
 */
public class CommandPlugin extends Plugin {

    public CommandPlugin(LunaContext ctx) {
        super(ctx);
    }

    @EventListener(CommandEvent.class)
    public boolean handleCommand(CommandEvent e) {
        Player p = e.getPlr();

        switch (e.getName()) {
            case "master":
                if (p.getRights().getClientValue() < PlayerRights.ADMINISTRATOR.getClientValue()) {
                    return false;
                }

                p.getSkills().forEach(it -> it.setExperience(SkillSet.MAXIMUM_EXPERIENCE));
                p.getSkills().getSkill(Skill.HITPOINTS).setLevel(99);
                p.sendMessage("You have set all your skills to level 99.");
                return true;
            case "mypos":
                if (p.getRights().getClientValue() < PlayerRights.ADMINISTRATOR.getClientValue()) {
                    return false;
                }

                p.sendMessage(p.getPosition());
                p.sendMessage(p.getChunk());
                p.sendMessage(p.getPosition().getRegion());
                break;
            case "bank":
                if (p.getRights().getClientValue() < PlayerRights.ADMINISTRATOR.getClientValue()) {
                    return false;
                }

                p.getBank().open();
                break;
            case "move":
                String[] args = e.getArgs();
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = args.length == 3 ? Integer.parseInt(args[2]) : p.getZ();
                p.move(new Position(x, y, z));
                break;
        }
        return false;
    }
}