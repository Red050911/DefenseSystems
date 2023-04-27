package io.github.red050911.defensesystems.obj.blockentity;

import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import io.github.red050911.defensesystems.reg.ModDamageTypes;
import io.github.red050911.defensesystems.reg.ModSoundEvents;
import io.github.red050911.defensesystems.util.IDefenseTickable;
import io.github.red050911.defensesystems.util.Util;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class AnvilLauncherBlockEntity extends BlockEntity implements IDefenseTickable {

    private int lastTickFromDefenseComputer;
    private int ticksToFire;
    private int dcX;
    private int dcY;
    private int dcZ;
    private UUID ownerID;

    public AnvilLauncherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        lastTickFromDefenseComputer = -1;
        dcX = -1;
        dcY = -1;
        dcZ = -1;
        ownerID = null;
        ticksToFire = 40;
    }

    public AnvilLauncherBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.ANVIL_LAUNCHER, pos, state);
    }

    @Override
    public void tickTarget(LivingEntity target, DefenseComputerBlockEntity be) {
        lastTickFromDefenseComputer = 0;
        if(world != null && !world.isClient()) {
            if(ticksToFire > 0) ticksToFire--;
            if(ticksToFire < 1 && target != null) {
                if(!world.getBlockState(new BlockPos(target.getBlockPos().getX(), pos.getY() + 100, target.getBlockPos().getZ())).isAir()) return;
                world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 1f);
                if(world instanceof ServerWorld) ((ServerWorld) world).spawnParticles(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 50, 0, 0, 0, 1);
                boolean fail = false;
                int velocity = 100;
                BlockPos origin = pos.up();
                for (int i = 0; i < 100; i++) {
                    BlockPos p = origin.up(i);
                    if (p.getY() <= world.getTopY() && !world.getBlockState(p).getCollisionShape(world, p).isEmpty()) {
                        BlockState state = world.getBlockState(p);
                        float hardness = state.getHardness(world, p);
                        int takeVelocity = hardness < 0 ? Integer.MAX_VALUE : Math.round(Math.max(1, state.getHardness(world, p)));
                        if (velocity < takeVelocity) {
                            BlockPos spawn = p.down();
                            if (world.getBlockState(spawn).getCollisionShape(world, spawn).isEmpty()) {
                                Util.spawnDestroyedOnLandingAnvil(new Vec3d(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5), world, velocity);
                            }
                            fail = true;
                            break;
                        } else {
                            world.breakBlock(p, true);
                            velocity -= takeVelocity;
                            if (velocity < 1) {
                                Util.spawnDestroyedOnLandingAnvil(new Vec3d(p.getX() + 0.5, p.getY(), p.getZ() + 0.5), world, 0);
                                fail = true;
                                break;
                            }
                        }
                    } else {
                        Box b = new Box(p);
                        List<Entity> fling = world.getEntitiesByClass(Entity.class, b, e -> true);
                        for(Entity e : fling) {
                            velocity -= 20;
                            if(velocity < 1) {
                                Util.spawnDestroyedOnLandingAnvil(new Vec3d(p.getX() + 0.5, p.getY(), p.getZ() + 0.5), world, 0);
                                break;
                            }
                            e.addVelocity(0, 100, 0);
                            e.damage(ModDamageTypes.getSource(world, ModDamageTypes.ANVIL_LAUNCHER), 19);
                        }
                        if(velocity < 1) break;
                    }
                }
                if(!fail) Util.spawnDestroyedOnLandingAnvil(new Vec3d(target.getPos().x, pos.getY() + 100, target.getPos().z), world, velocity);
                ticksToFire = 40;
            }
        }
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
        nbt.putInt("FireCooldown", ticksToFire);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if(tag.containsUuid("BlockOwner")) ownerID = tag.getUuid("BlockOwner");
        if(tag.contains("FireCooldown", NbtType.INT)) ticksToFire = tag.getInt("FireCooldown");
    }

    public static void tick(World world, BlockPos pos, BlockState state, AnvilLauncherBlockEntity be) {
        be.tick(pos);
    }

}
