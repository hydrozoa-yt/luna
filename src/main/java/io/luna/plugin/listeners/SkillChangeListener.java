package io.luna.plugin.listeners;

import io.luna.game.event.impl.SkillChangeEvent;

/**
 * @author hydrozoa
 */
public interface SkillChangeListener extends EventListener<SkillChangeEvent> {

    @Override
    default boolean onEvent(SkillChangeEvent event) {
        onSkillChange(event);
        return false;
    }

    void onSkillChange(SkillChangeEvent event);
}
