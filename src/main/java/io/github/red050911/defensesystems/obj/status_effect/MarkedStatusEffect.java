package io.github.red050911.defensesystems.obj.status_effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class MarkedStatusEffect extends StatusEffect {

    public MarkedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFF0000);
    }

}
