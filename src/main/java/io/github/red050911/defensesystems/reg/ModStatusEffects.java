package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.status_effect.MarkedStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {

    public static StatusEffect MARKED;

    public static void register() {
        MARKED = Registry.register(Registry.STATUS_EFFECT, DefenseSystems.id("marked"), new MarkedStatusEffect());
    }

}
