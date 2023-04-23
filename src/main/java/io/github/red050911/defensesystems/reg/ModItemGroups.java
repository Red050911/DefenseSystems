package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static final ItemGroup MOD_IG = FabricItemGroupBuilder.build(DefenseSystems.id("mod_group"), () -> new ItemStack(ModBlocks.DEFENSE_COMPUTER_BLOCK_ITEM));

}
