package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.block.*;
import net.minecraft.block.Block;
import io.github.red050911.defensesystems.util.TooltipBlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static Block DEFENSE_COMPUTER_BLOCK;
    public static Item DEFENSE_COMPUTER_BLOCK_ITEM;

    public static Block CAMERA_BOX_BLOCK;
    public static Item CAMERA_BOX_BLOCK_ITEM;

    public static Block TELESCOPIC_CAMERA_BOX_BLOCK;
    public static Item TELESCOPIC_CAMERA_BOX_BLOCK_ITEM;

    public static Block IR_CAMERA_BOX_BLOCK;
    public static Item IR_CAMERA_BOX_BLOCK_ITEM;

    public static Block TELESCOPIC_IR_CAMERA_BOX_BLOCK;
    public static Item TELESCOPIC_IR_CAMERA_BOX_BLOCK_ITEM;

    public static Block ANVIL_LAUNCHER_BLOCK;
    public static Item ANVIL_LAUNCHER_BLOCK_ITEM;

    public static Block TURRET_BLOCK;
    public static Item TURRET_BLOCK_ITEM;

    public static Block RADAR_BLOCK;
    public static Item RADAR_BLOCK_ITEM;

    public static void register() {
        DEFENSE_COMPUTER_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("defense_computer"), new DefenseComputerBlock());
        DEFENSE_COMPUTER_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("defense_computer"), new TooltipBlockItem(DEFENSE_COMPUTER_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        CAMERA_BOX_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("camera_box"), new CameraBoxBlock(false, false, false));
        CAMERA_BOX_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("camera_box"), new TooltipBlockItem(CAMERA_BOX_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        TELESCOPIC_CAMERA_BOX_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("telescopic_camera_box"), new CameraBoxBlock(true, false, false));
        TELESCOPIC_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("telescopic_camera_box"), new TooltipBlockItem(TELESCOPIC_CAMERA_BOX_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        IR_CAMERA_BOX_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("ir_camera_box"), new CameraBoxBlock(false, true, true));
        IR_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("ir_camera_box"), new TooltipBlockItem(IR_CAMERA_BOX_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        TELESCOPIC_IR_CAMERA_BOX_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("telescopic_ir_camera_box"), new CameraBoxBlock(true, true, true));
        TELESCOPIC_IR_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("telescopic_ir_camera_box"), new TooltipBlockItem(TELESCOPIC_IR_CAMERA_BOX_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        ANVIL_LAUNCHER_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("anvil_launcher"), new AnvilLauncherBlock());
        ANVIL_LAUNCHER_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("anvil_launcher"), new TooltipBlockItem(ANVIL_LAUNCHER_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        TURRET_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("turret"), new TurretBlock());
        TURRET_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("turret"), new TooltipBlockItem(TURRET_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));

        RADAR_BLOCK = Registry.register(Registry.BLOCK, DefenseSystems.id("radar"), new RadarBlock());
        RADAR_BLOCK_ITEM = Registry.register(Registry.ITEM, DefenseSystems.id("radar"), new TooltipBlockItem(RADAR_BLOCK, new Item.Settings().group(ModItemGroups.MOD_IG)));
    }

}
