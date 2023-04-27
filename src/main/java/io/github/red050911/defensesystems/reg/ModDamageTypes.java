package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> ANVIL_LAUNCHER = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, DefenseSystems.id("anvil_launcher_fling"));
    public static final RegistryKey<DamageType> BULLET = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, DefenseSystems.id("bullet"));

    public static DamageSource getSource(World w, RegistryKey<DamageType> key) {
        return w.getDamageSources().create(key);
    }

}
