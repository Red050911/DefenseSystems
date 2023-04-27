package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.enchantment.IRBlockingEnchantment;
import io.github.red050911.defensesystems.obj.enchantment.RadarBlockingEnchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantments {

    public static IRBlockingEnchantment IR_BLOCKING;
    public static RadarBlockingEnchantment RADAR_BLOCKING;

    public static void register() {
        IR_BLOCKING = Registry.register(Registries.ENCHANTMENT, DefenseSystems.id("ir_blocking"), new IRBlockingEnchantment());
        RADAR_BLOCKING = Registry.register(Registries.ENCHANTMENT, DefenseSystems.id("radar_blocking"), new RadarBlockingEnchantment());
    }

}
