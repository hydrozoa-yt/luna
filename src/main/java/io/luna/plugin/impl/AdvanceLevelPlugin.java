package io.luna.plugin.impl;

import io.luna.LunaContext;
import io.luna.game.event.impl.SkillChangeEvent;
import io.luna.game.model.mob.Player;
import io.luna.game.model.mob.Skill;
import io.luna.game.model.mob.block.Graphic;
import io.luna.game.model.mob.block.UpdateFlagSet;
import io.luna.game.model.mob.inter.DialogueInterface;
import io.luna.net.msg.out.SkillUpdateMessageWriter;
import io.luna.net.msg.out.WidgetAnimationMessageWriter;
import io.luna.net.msg.out.WidgetTextMessageWriter;
import io.luna.plugin.EventListener;
import io.luna.plugin.Plugin;
import io.luna.util.StringUtils;

import java.util.List;

/**
 * Ported from advanceLevel.kts
 *
 * @author hydrozoa
 */
public class AdvanceLevelPlugin extends Plugin {

    /**
     * Graphic played when a player advances a level.
     */
    private Graphic fireworksGraphic = new Graphic(199);

    private class LevelUpData {

        int firstLine, secondLine, inter;

        LevelUpData(int firstLine, int secondLine, int inter) {
            this.firstLine = firstLine;
            this.secondLine = secondLine;
            this.inter = inter;
        }
    }

    /**
     * A table holding data for the [LevelUpInterface].
     */
    private List<LevelUpData> levelUpTable = List.of(
            new LevelUpData(6248, 6249, 6247),
            new LevelUpData(6254, 6255, 6253),
            new LevelUpData(6207, 6208, 6206),
            new LevelUpData(6217, 6218, 6216),
            new LevelUpData(5453, 5454, 4443),
            new LevelUpData(6243, 6244, 6242),
            new LevelUpData(6212, 6213, 6211),
            new LevelUpData(6227, 6228, 6226),
            new LevelUpData(4273, 4274, 4272),
            new LevelUpData(6232, 6233, 6231),
            new LevelUpData(6259, 6260, 6258),
            new LevelUpData(4283, 4284, 4282),
            new LevelUpData(6264, 6265, 6263),
            new LevelUpData(6222, 6223, 6221),
            new LevelUpData(4417, 4438, 4416),
            new LevelUpData(6238, 6239, 6237),
            new LevelUpData(4278, 4279, 4277),
            new LevelUpData(4263, 4264, 4261),
            new LevelUpData(12123, 12124, 12122),
            new LevelUpData(4889, 4890, 4887),
            new LevelUpData(4268, 4269, 4267)
    );

    /**
     * Interface shown when leveling up
     */
    private class LevelUpInterface extends DialogueInterface {

        private int skill;
        private int newLevel;
        private AdvanceLevelPlugin.LevelUpData data;

        public LevelUpInterface(int skill, int newLevel, AdvanceLevelPlugin.LevelUpData levelUpData) {
            super(levelUpData.inter);
            this.skill = skill;
            this.newLevel = newLevel;
            this.data = levelUpData;
        }

        @Override
        public void onOpen(Player player) {
            String skillName = Skill.getName(skill);
            String lvlUpMessage = "Congratulations, you just advanced "+ StringUtils.addArticle(skillName) +" level!";

            player.sendMessage(lvlUpMessage);
            player.queue(new WidgetTextMessageWriter(lvlUpMessage, data.firstLine));
            player.queue(new WidgetTextMessageWriter("Your "+skillName+" level is now "+newLevel+".", data.secondLine));

            if(skill == Skill.FIREMAKING) {
                // Animates the flame in the chatbox.
                player.queue(new WidgetAnimationMessageWriter(4286, 475));
            }
        }
    }

    public AdvanceLevelPlugin(LunaContext ctx) {
        super(ctx);
    }

    private void advanceLevel(Player p, int skillId, int oldLevel) {
        Skill skill = p.getSkills().getSkill(skillId);
        int newLevel = skill.getStaticLevel();
        if (oldLevel < newLevel) {
            switch (skillId) {
                case Skill.HITPOINTS:
                    skill.setLevel(skill.getLevel()+1);
                    break;
                default:
                    skill.setLevel(newLevel);
                    break;
            }
            LevelUpData levelUpData = levelUpTable.get(skillId);
            p.getInterfaces().open(new LevelUpInterface(skillId, newLevel, levelUpData));
            p.graphic(fireworksGraphic);

            if (Skill.isCombatSkill(skillId)) {
                p.getSkills().resetCombatLevel();
                p.getFlags().flag(UpdateFlagSet.UpdateFlag.APPEARANCE);
            }
        }
    }

    @EventListener(type = SkillChangeEvent.class)
    public void onSkillChange(SkillChangeEvent event) {
        if (event.getMob() instanceof Player) {
            Player p = (Player) event.getMob();
            p.queue(new SkillUpdateMessageWriter(event.getId()));
            if (event.getOldStaticLvl() < 99) {
                advanceLevel(p, event.getId(), event.getOldStaticLvl());
            }
        }
    }
}