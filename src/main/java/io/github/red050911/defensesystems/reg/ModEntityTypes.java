package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.obj.entity.BulletProjectile;
import io.github.red050911.defensesystems.obj.entity.TurretEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityTypes {

    public static EntityType<TurretEntity> TURRET;
    public static EntityType<BulletProjectile> BULLET;

    public static void register() {
        TURRET = Registry.register(Registries.ENTITY_TYPE, DefenseSystems.id("turret"), FabricEntityTypeBuilder.<TurretEntity>create(SpawnGroup.MISC, TurretEntity::new)
                .dimensions(EntityDimensions.fixed(1, 1))
                .fireImmune()
                .build()
        );
        FabricDefaultAttributeRegistry.register(TURRET, TurretEntity.createTurretAttributes());
        BULLET = Registry.register(Registries.ENTITY_TYPE, DefenseSystems.id("bullet"), FabricEntityTypeBuilder.<BulletProjectile>create(SpawnGroup.MISC, BulletProjectile::new)
                .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                .trackRangeBlocks(4)
                .trackedUpdateRate(10)
                .build()
        );
    }

}
