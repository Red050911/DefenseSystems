package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.blockentity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class ModBlockEntityTypes {

    public static BlockEntityType<DefenseComputerBlockEntity> DEFENSE_COMPUTER;
    public static BlockEntityType<CameraBoxBlockEntity> CAMERA_BOX;
    public static BlockEntityType<AnvilLauncherBlockEntity> ANVIL_LAUNCHER;
    public static BlockEntityType<TurretBlockEntity> TURRET;
    public static BlockEntityType<RadarBlockEntity> RADAR;

    public static void register() {
        DEFENSE_COMPUTER = Registry.register(Registry.BLOCK_ENTITY_TYPE, DefenseSystems.id("defense_computer"), FabricBlockEntityTypeBuilder.create(
                DefenseComputerBlockEntity::new,
                ModBlocks.DEFENSE_COMPUTER_BLOCK
        ).build(null));
        CAMERA_BOX = Registry.register(Registry.BLOCK_ENTITY_TYPE, DefenseSystems.id("camera_box"), FabricBlockEntityTypeBuilder.create(
                CameraBoxBlockEntity::new,
                ModBlocks.CAMERA_BOX_BLOCK,
                ModBlocks.IR_CAMERA_BOX_BLOCK,
                ModBlocks.TELESCOPIC_CAMERA_BOX_BLOCK,
                ModBlocks.TELESCOPIC_IR_CAMERA_BOX_BLOCK
        ).build(null));
        ANVIL_LAUNCHER = Registry.register(Registry.BLOCK_ENTITY_TYPE, DefenseSystems.id("anvil_launcher"), FabricBlockEntityTypeBuilder.create(
                AnvilLauncherBlockEntity::new,
                ModBlocks.ANVIL_LAUNCHER_BLOCK
        ).build(null));
        TURRET = Registry.register(Registry.BLOCK_ENTITY_TYPE, DefenseSystems.id("turret"), FabricBlockEntityTypeBuilder.create(
                TurretBlockEntity::new,
                ModBlocks.TURRET_BLOCK
        ).build(null));
        RADAR = Registry.register(Registry.BLOCK_ENTITY_TYPE, DefenseSystems.id("radar"), FabricBlockEntityTypeBuilder.create(
                RadarBlockEntity::new,
                ModBlocks.RADAR_BLOCK
        ).build(null));
    }

}
