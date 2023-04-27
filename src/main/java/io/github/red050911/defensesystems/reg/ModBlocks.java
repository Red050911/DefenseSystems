package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.block.*;
import net.minecraft.block.Block;
import io.github.red050911.defensesystems.util.TooltipBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

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
        DEFENSE_COMPUTER_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("defense_computer"), new DefenseComputerBlock());
        DEFENSE_COMPUTER_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("defense_computer"), new TooltipBlockItem(DEFENSE_COMPUTER_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(DEFENSE_COMPUTER_BLOCK_ITEM);

        CAMERA_BOX_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("camera_box"), new CameraBoxBlock(false, false, false));
        CAMERA_BOX_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("camera_box"), new TooltipBlockItem(CAMERA_BOX_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(CAMERA_BOX_BLOCK_ITEM);

        TELESCOPIC_CAMERA_BOX_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("telescopic_camera_box"), new CameraBoxBlock(true, false, false));
        TELESCOPIC_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("telescopic_camera_box"), new TooltipBlockItem(TELESCOPIC_CAMERA_BOX_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(TELESCOPIC_CAMERA_BOX_BLOCK_ITEM);

        IR_CAMERA_BOX_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("ir_camera_box"), new CameraBoxBlock(false, true, true));
        IR_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("ir_camera_box"), new TooltipBlockItem(IR_CAMERA_BOX_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(IR_CAMERA_BOX_BLOCK_ITEM);

        TELESCOPIC_IR_CAMERA_BOX_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("telescopic_ir_camera_box"), new CameraBoxBlock(true, true, true));
        TELESCOPIC_IR_CAMERA_BOX_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("telescopic_ir_camera_box"), new TooltipBlockItem(TELESCOPIC_IR_CAMERA_BOX_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(TELESCOPIC_IR_CAMERA_BOX_BLOCK_ITEM);

        ANVIL_LAUNCHER_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("anvil_launcher"), new AnvilLauncherBlock());
        ANVIL_LAUNCHER_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("anvil_launcher"), new TooltipBlockItem(ANVIL_LAUNCHER_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(ANVIL_LAUNCHER_BLOCK_ITEM);

        TURRET_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("turret"), new TurretBlock());
        TURRET_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("turret"), new TooltipBlockItem(TURRET_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(TURRET_BLOCK_ITEM);

        RADAR_BLOCK = Registry.register(Registries.BLOCK, DefenseSystems.id("radar"), new RadarBlock());
        RADAR_BLOCK_ITEM = Registry.register(Registries.ITEM, DefenseSystems.id("radar"), new TooltipBlockItem(RADAR_BLOCK, new FabricItemSettings()));
        ModItemGroups.registerItemForModIG(RADAR_BLOCK_ITEM);
    }

}
