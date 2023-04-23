package io.github.red050911.defensesystems.obj.blockentity;

import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import io.github.red050911.defensesystems.reg.ModEnchantments;
import io.github.red050911.defensesystems.reg.ModSoundEvents;
import io.github.red050911.defensesystems.reg.ModStatusEffects;
import io.github.red050911.defensesystems.util.BiomeColorMap;
import io.github.red050911.defensesystems.util.ISurveillanceTickable;
import io.github.red050911.defensesystems.util.Util;
import io.github.red050911.defensesystems.util.compat.ReflectivePehkuiInterface;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CameraBoxBlockEntity extends BlockEntity implements ISurveillanceTickable, Tickable {

    private int lastTickFromDefenseComputer;
    private int dcX;
    private int dcY;
    private int dcZ;
    private UUID ownerID;

    private boolean telescopic;
    private boolean seesInvisPlayers;
    private boolean seesInDark;

    public CameraBoxBlockEntity(BlockEntityType<?> type) {
        super(type);
        lastTickFromDefenseComputer = -1;
        dcX = -1;
        dcY = -1;
        dcZ = -1;
        ownerID = null;
    }

    public CameraBoxBlockEntity() {
        this(ModBlockEntityTypes.CAMERA_BOX);
    }

    @Override
    public void tickFind(DefenseComputerBlockEntity be, Box defenseZone) {
        lastTickFromDefenseComputer = 0;
        if(world != null && !world.isClient()) {
            Box zone = new Box(pos).expand(telescopic ? 20 : 10);
            List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, zone, e -> {
                if(e instanceof PlayerEntity) return e.getBoundingBox().intersects(defenseZone) && !be.isWhitelisted(e.getUuid());
                return e.getBoundingBox().intersects(defenseZone) && e.getType().getSpawnGroup() == SpawnGroup.MONSTER;
            });
            for(LivingEntity le : targets) {
                boolean scalePass;
                if(!telescopic) scalePass = ReflectivePehkuiInterface.getPehkuiScale(le) > 0.125f;
                else scalePass = ReflectivePehkuiInterface.getPehkuiScale(le) > 0.125f || le.getPos().distanceTo(new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5)) <= 10;
                boolean invisPass;
                if(!seesInvisPlayers) {
                    int camo = BiomeColorMap.toCamoColor(world.getBiome(le.getBlockPos()).getCategory());
                    boolean dyedArmor = true;
                    for(ItemStack armor : le.getArmorItems()) {
                        if(!(armor.getItem() instanceof DyeableArmorItem && ((DyeableArmorItem) armor.getItem()).getColor(armor) == camo)) {
                            dyedArmor = false;
                            break;
                        }
                    }
                    invisPass = !dyedArmor && !le.hasStatusEffect(StatusEffects.INVISIBILITY);
                } else {
                    boolean irBlocking = true;
                    String irBlockingId = Objects.requireNonNull(Registry.ENCHANTMENT.getId(ModEnchantments.IR_BLOCKING)).toString();
                    for(ItemStack armor : le.getArmorItems()) {
                        if(armor.getEnchantments().stream().noneMatch(e -> e instanceof NbtCompound && ((NbtCompound) e).contains("id", NbtType.STRING) && ((NbtCompound) e).getString("id").equals(irBlockingId))) {
                            irBlocking = false;
                            break;
                        }
                    }
                    invisPass = !irBlocking;
                }
                boolean lightPass = true;
                if(!seesInDark) lightPass = world.getLightLevel(le.getBlockPos()) > 7;
                if(invisPass && lightPass && scalePass && (Util.noBlocksInTheWay(le.getPos(), pos, world) || Util.noBlocksInTheWay(new Vec3d(le.getPos().x, le.getEyeY(), le.getPos().z), pos, world))) {
                    le.addStatusEffect(new StatusEffectInstance(ModStatusEffects.MARKED, 600, 0, false, false, true));
                }
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

    @Override
    public void tick() {
        if(isInitializedAtAll()) if(lastTickFromDefenseComputer++ >= 10) {
            lastTickFromDefenseComputer = -1;
            if(world instanceof ServerWorld) world.playSound(null, pos, ModSoundEvents.GENERIC_SHUTDOWN, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    public void setOwnerID(UUID uuid) {
        ownerID = uuid;
        markDirty();
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putUuid("BlockOwner", ownerID);
        return nbt;
    }

    @Override
    public void fromTag(BlockState state, NbtCompound tag) {
        super.fromTag(state, tag);
        if(tag.containsUuid("BlockOwner")) {
            ownerID = tag.getUuid("BlockOwner");
        }
    }

    public void setTelescopic(boolean telescopic) {
        this.telescopic = telescopic;
    }

    public void setSeesInvisPlayers(boolean seesInvisPlayers) {
        this.seesInvisPlayers = seesInvisPlayers;
    }

    public void setSeesInDark(boolean seesInDark) {
        this.seesInDark = seesInDark;
    }

}
