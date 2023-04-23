package io.github.red050911.defensesystems.obj.entity;

import io.github.red050911.defensesystems.reg.ModEntityTypes;
import io.github.red050911.defensesystems.reg.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TurretEntity extends MobEntity {

    private int lastTickFromBlock = 0;
    private boolean fireThisTick = true;

    public TurretEntity(EntityType<TurretEntity> entityType, World world) {
        super(entityType, world);
        noClip = true;
    }

    public TurretEntity(World world, double x, double y, double z) {
        this(ModEntityTypes.TURRET, world);
        setPosition(x, y, z);
    }

    public static DefaultAttributeContainer.Builder createTurretAttributes() {
        return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10);
    }

    @Override
    public void tick() {
        super.tick();
        noClip = true;
        if(!world.isClient()) {
            if(lastTickFromBlock++ > 10) remove();
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.isOutOfWorld()) return super.damage(source, amount);
        else return false;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    public void tickTargeting(LivingEntity target) {
        lastTickFromBlock = 0;
        if(target != null) {
            getLookControl().lookAt(target, 360, 360);
            if(fireThisTick) {
                BulletProjectile e = new BulletProjectile(this, world);
                e.setItem(new ItemStack(ModItems.BULLET_ITEM));
                e.setProperties(this, this.pitch, this.headYaw, 0, 1.5f, 0);
                world.spawnEntity(e);
            }
            fireThisTick = !fireThisTick;
        }
    }

}
