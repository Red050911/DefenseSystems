package io.github.red050911.defensesystems.obj.entity;

import io.github.red050911.defensesystems.reg.ModEntityTypes;
import io.github.red050911.defensesystems.reg.ModItems;
import io.github.red050911.defensesystems.util.ModDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BulletProjectile extends ThrownItemEntity {

    public BulletProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BulletProjectile(double d, double e, double f, World world) {
        super(ModEntityTypes.BULLET, d, e, f, world);
    }

    public BulletProjectile(LivingEntity livingEntity, World world) {
        super(ModEntityTypes.BULLET, livingEntity, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity e = entityHitResult.getEntity();
        e.damage(new ModDamageSource("defense_systems_bullet"), 3);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if(!world.isClient()) kill();
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BULLET_ITEM;
    }
}
