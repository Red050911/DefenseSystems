package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static Item BULLET_ITEM;

    public static void register() {
        BULLET_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("bullet"), new Item(new Item.Settings()));
    }

}
