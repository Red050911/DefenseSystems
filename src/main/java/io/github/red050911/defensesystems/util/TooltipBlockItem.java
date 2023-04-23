package io.github.red050911.defensesystems.util;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class TooltipBlockItem extends BlockItem {

    private final Text tooltip;

    public TooltipBlockItem(Block block, Settings settings) {
        super(block, settings);
        tooltip = new TranslatableText(getTranslationKey().replace("block", "block_tooltip"));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        if(InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_LEFT_SHIFT) || InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_RIGHT_SHIFT)) tooltip.add(this.tooltip);
        else tooltip.add(new TranslatableText("tooltip.defense_systems.press_shift"));
    }

}
