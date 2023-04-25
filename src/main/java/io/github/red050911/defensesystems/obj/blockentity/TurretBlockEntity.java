package io.github.red050911.defensesystems.obj.blockentity;

import io.github.red050911.defensesystems.obj.entity.TurretEntity;
import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import io.github.red050911.defensesystems.reg.ModSoundEvents;
import io.github.red050911.defensesystems.util.IDefenseTickable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class TurretBlockEntity extends BlockEntity implements IDefenseTickable {

    private int lastTickFromDefenseComputer;
    private int dcX;
    private int dcY;
    private int dcZ;
    private UUID ownerID;
    private UUID entityID;

    public TurretBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        lastTickFromDefenseComputer = -1;
        dcX = -1;
        dcY = -1;
        dcZ = -1;
        ownerID = null;
        entityID = null;
    }

    public TurretBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.TURRET, pos, state);
    }

    @Override
    public void tickTarget(LivingEntity target, DefenseComputerBlockEntity be) {
        lastTickFromDefenseComputer = 0;
        if(world != null && !world.isClient() && world instanceof ServerWorld) {
            boolean shouldBeUp = target != null && world.getBlockState(pos.up()).isAir();
            boolean isUp = entityID != null;
            if(shouldBeUp != isUp) {
                if(isUp) {
                    Entity e = ((ServerWorld) world).getEntity(entityID);
                    if(e instanceof TurretEntity) e.remove(Entity.RemovalReason.DISCARDED);
                    entityID = null;
                    world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 1, 1);
                } else {
                    TurretEntity e = new TurretEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                    entityID = e.getUuid();
                    world.spawnEntity(e);
                    world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 1, 1);
                }
            } else {
                Entity e = ((ServerWorld) world).getEntity(entityID);
                if(e instanceof TurretEntity) {
                    TurretEntity te = (TurretEntity) e;
                    te.tickTargeting(target);
                }
            }
        }
    }

    @Override
    public void markRemoved() {
        if(world instanceof ServerWorld && entityID != null) {
            Entity e = ((ServerWorld) world).getEntity(entityID);
            if(e instanceof TurretEntity) e.remove(Entity.RemovalReason.DISCARDED);
            entityID = null;
        }
        super.markRemoved();
    }

    @Override
    public void initialize(int x, int y, int z) {
        lastTickFromDefenseComputer = 0;
        dcX = x;
        dcY = y;
        dcZ = z;
    }

    @Override
    public UUID getOwnerID() {
        return ownerID;
    }

    @Override
    public boolean isInitialized(int x, int y, int z) {
        if(!isInitializedAtAll()) return false;
        return x == dcX && y == dcY && z == dcZ;
    }

    @Override
    public boolean isInitializedAtAll() {
        return lastTickFromDefenseComputer > -1;
    }

    public void tick(BlockPos pos) {
        if(isInitializedAtAll()) if(lastTickFromDefenseComputer++ >= 10) {
            lastTickFromDefenseComputer = -1;
            if(world instanceof ServerWorld && entityID != null) {
                Entity e = ((ServerWorld) world).getEntity(entityID);
                if(e instanceof TurretEntity) e.remove(Entity.RemovalReason.DISCARDED);
                entityID = null;
            }
            if(world instanceof ServerWorld) world.playSound(null, pos, ModSoundEvents.GENERIC_SHUTDOWN, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    public void setOwnerID(UUID uuid) {
        ownerID = uuid;
        markDirty();
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putUuid("BlockOwner", ownerID);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if(tag.containsUuid("BlockOwner")) ownerID = tag.getUuid("BlockOwner");
    }

    public static void tick(World world, BlockPos pos, BlockState state, TurretBlockEntity be) {
        be.tick(pos);
    }

}
