package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModItemGroups {

    public static final ItemGroup MOD_IG = FabricItemGroup.builder(DefenseSystems.id("mod_group"))
            .icon(() -> new ItemStack(ModBlocks.DEFENSE_COMPUTER_BLOCK_ITEM))
            .entries((displayContext, entries) -> entries.addAll(ModItemGroups.MOD_IG_ITEMS.stream().map(ItemStack::new).collect(Collectors.toList())))
            .build();

    private static final List<Item> MOD_IG_ITEMS = new ArrayList<>();

    public static void registerItemForModIG(Item i) {
        MOD_IG_ITEMS.add(i);
    }

}
