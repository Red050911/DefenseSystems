package io.github.red050911.defensesystems.obj.blockentity;

import io.github.red050911.defensesystems.obj.block.DefenseComputerBlock;
import io.github.red050911.defensesystems.reg.ModBlockEntityTypes;
import io.github.red050911.defensesystems.reg.ModSoundEvents;
import io.github.red050911.defensesystems.reg.ModStatusEffects;
import io.github.red050911.defensesystems.util.IDefenseTickable;
import io.github.red050911.defensesystems.util.ISurveillanceTickable;
import io.github.red050911.defensesystems.util.Util;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class DefenseComputerBlockEntity extends BlockEntity {

    private UUID ownerID;
    private final List<StoredPlayerData> whitelist;
    private boolean hasCheckedLKU;
    private TargetingType targeting;

    public DefenseComputerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        whitelist = new ArrayList<>();
        hasCheckedLKU = false;
        targeting = TargetingType.BASIC_MODULO;
    }

    public DefenseComputerBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.DEFENSE_COMPUTER, pos, state);
    }

    public void tick(BlockPos pos, BlockState state, World world) {
        if(world != null && !world.isClient()) {
            if(!hasCheckedLKU) {
                hasCheckedLKU = true;
                boolean sMD = false;
                for(ServerPlayerEntity spe : Objects.requireNonNull(world.getServer()).getPlayerManager().getPlayerList()) for(StoredPlayerData spd : whitelist) if(spe.getUuid().equals(spd.getId())) {
                    spd.setlKU(spe.getName().asString());
                    sMD = true;
                }
                if(sMD) markDirty();
            }
            Box zone = new Box(pos).expand(128);
            zone = new Box(zone.minX, world.getBottomY(), zone.minZ, zone.maxX, world.getTopY(), zone.maxZ);
            List<BlockEntity> surveillance = Util.getBlockEntities(world, zone, be -> be instanceof ISurveillanceTickable);
            for(BlockEntity be : surveillance) {
                ISurveillanceTickable tickable = (ISurveillanceTickable) be;
                if(!tickable.isInitializedAtAll() && Objects.equals(tickable.getOwnerID(), ownerID)) {
                    tickable.initialize(pos.getX(), pos.getY(), pos.getZ());
                    world.playSound(null, be.getPos(), ModSoundEvents.GENERIC_BOOT_UP, SoundCategory.BLOCKS, 1f, 1f);
                }
                else if(!tickable.isInitialized(pos.getX(), pos.getY(), pos.getZ())) continue;
                tickable.tickFind(this, zone);
            }
            List<LivingEntity> targets = world.getEntitiesByClass(LivingEntity.class, zone, e -> e.hasStatusEffect(ModStatusEffects.MARKED) && !(e instanceof PlayerEntity && isWhitelisted(e.getUuid())));
            if(state.get(DefenseComputerBlock.TARGETING) == targets.isEmpty()) {
                world.setBlockState(pos, state.with(DefenseComputerBlock.TARGETING, !targets.isEmpty()));
                world.playSound(null, pos, targets.isEmpty() ? ModSoundEvents.COMPUTER_LOSE_TARGET : ModSoundEvents.COMPUTER_TARGET, SoundCategory.BLOCKS, 1f, 1f);
            }
            List<BlockEntity> atk = Util.getBlockEntities(world, zone, be -> be instanceof IDefenseTickable);
            int tI = 0;
            for(BlockEntity be : atk) {
                IDefenseTickable tickable = (IDefenseTickable) be;
                if(!tickable.isInitializedAtAll() && Objects.equals(tickable.getOwnerID(), ownerID)) {
                    tickable.initialize(pos.getX(), pos.getY(), pos.getZ());
                    world.playSound(null, be.getPos(), ModSoundEvents.GENERIC_BOOT_UP, SoundCategory.BLOCKS, 1f, 1f);
                }
                else if(!tickable.isInitialized(pos.getX(), pos.getY(), pos.getZ())) continue;
                tickable.tickTarget(targets.isEmpty() ? null : targeting.findTarget(tI, targets, be.getPos()), this);
                tI++;
            }
        }
    }

    public boolean isWhitelisted(UUID uuid) {
        if(uuid.equals(ownerID)) return true;
        for(StoredPlayerData spd : whitelist) if(spd.getId().equals(uuid)) return true;
        return false;
    }

    public void onPlayerJoin(ServerPlayerEntity spe) {
        for(StoredPlayerData spd : whitelist) if(spe.getUuid().equals(spd.getId())) {
            spd.setlKU(spe.getName().asString());
            markDirty();
        }
    }

    public void onClicked(PlayerEntity p) {
        if(!p.world.isClient()) {
            if(p.getUuid().equals(ownerID)) {
                targeting = targeting.next();
                p.sendMessage(new TranslatableText(targeting.getTranslationKey()).styled(s -> s.withColor(TextColor.parse("green"))), true);
            } else {
                p.sendMessage(new TranslatableText("msg.defense_systems.not_block_owner").styled(s -> s.withColor(TextColor.parse("red"))), true);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtList wl = new NbtList();
        for(StoredPlayerData spd : whitelist) {
            NbtCompound nc = new NbtCompound();
            spd.writeToNBT(nc);
            wl.add(nc);
        }
        nbt.put("BlockWhitelist", wl);
        nbt.putUuid("BlockOwner", ownerID);
        nbt.putInt("Targeting", targeting.ordinal());
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if(tag.contains("BlockWhitelist", NbtType.LIST)) {
            this.whitelist.clear();
            NbtList nL = tag.getList("BlockWhitelist" , NbtType.COMPOUND);
            for(NbtElement e : nL) {
                StoredPlayerData spd = StoredPlayerData.readFromNBT((NbtCompound) e);
                this.whitelist.add(spd);
            }
        }
        if(tag.containsUuid("BlockOwner")) {
            ownerID = tag.getUuid("BlockOwner");
        }
        if(tag.contains("Targeting", NbtType.INT)) {
            int ordinal = tag.getInt("Targeting");
            if(ordinal < TargetingType.values().length && ordinal >= 0) {
                targeting = TargetingType.values()[ordinal];
            }
        }
    }

    public void onClickedWithCustomName(PlayerEntity p, String cn) {
        if(!p.world.isClient() && p instanceof ServerPlayerEntity) {
            if(p.getUuid().equals(ownerID)) {
                if (!cn.startsWith("!")) {
                    PlayerEntity target = ((ServerPlayerEntity) p).server.getPlayerManager().getPlayer(cn);
                    if (target != null && !isWhitelisted(target.getUuid())) {
                        this.whitelist.add(new StoredPlayerData(target.getUuid(), target.getName().asString()));
                        p.sendMessage(new TranslatableText("msg.defense_systems.added_to_wl", cn).styled(s -> s.withColor(TextColor.parse("green"))), true);
                        markDirty();
                    } else {
                        p.sendMessage(new TranslatableText("msg.defense_systems.not_online_or_already_wl").styled(s -> s.withColor(TextColor.parse("red"))), true);
                    }
                } else if (cn.length() > 1) {
                    String withoutExclamation = cn.substring(1);
                    int i = 0;
                    for (StoredPlayerData spd : whitelist) {
                        if (spd.getlKU().equalsIgnoreCase(withoutExclamation)) {
                            this.whitelist.remove(i);
                            p.sendMessage(new TranslatableText("msg.defense_systems.removed_from_wl", cn).styled(s -> s.withColor(TextColor.parse("green"))), true);
                            markDirty();
                            break;
                        }
                        i++;
                    }
                    p.sendMessage(new TranslatableText("msg.defense_systems.owner_or_not_wl").styled(s -> s.withColor(TextColor.parse("red"))), true);
                }
            } else {
                p.sendMessage(new TranslatableText("msg.defense_systems.not_block_owner").styled(s -> s.withColor(TextColor.parse("red"))), true);
            }
        }
    }

    public void setOwnerID(UUID ownerID) {
        this.ownerID = ownerID;
        markDirty();
    }

    public static void tick(World world, BlockPos pos, BlockState state, DefenseComputerBlockEntity be) {
        be.tick(pos, state, world);
    }
    
    public enum TargetingType {
        
        BASIC_MODULO("targeting_type.defense_systems.modulo", (targetingIndex, targets, pos) -> targets.get(targetingIndex % targets.size())),
        PLAYERS_BEFORE_ENTITIES("targeting_type.defense_systems.pbe", (targetingIndex, targets, pos) -> {
            if(targets.stream().anyMatch(l -> l instanceof PlayerEntity)) {
                List<LivingEntity> nTargeting = targets.stream().filter(l -> l instanceof PlayerEntity).collect(Collectors.toList());
                return nTargeting.get(targetingIndex % nTargeting.size());
            } else return targets.get(targetingIndex % targets.size());
        }),
        ENTITIES_BEFORE_PLAYERS("targeting_type.defense_systems.ebp", (targetingIndex, targets, pos) -> {
            if(targets.stream().anyMatch(l -> !(l instanceof PlayerEntity))) {
                List<LivingEntity> nTargeting = targets.stream().filter(l -> !(l instanceof PlayerEntity)).collect(Collectors.toList());
                return nTargeting.get(targetingIndex % nTargeting.size());
            } else return targets.get(targetingIndex % targets.size());
        }),
        CLOSEST("targeting_type.defense_systems.close", (targetingIndex, targets, pos) -> {
            LivingEntity closest = null;
            double dist = 0;
            for(LivingEntity le : targets) {
                double distToLE = le.getPos().distanceTo(new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
                if(closest == null || distToLE < dist) {
                    closest = le;
                    dist = distToLE;
                }
            }
            return closest;
        }),
        FARTHEST("targeting_type.defense_systems.far", (targetingIndex, targets, pos) -> {
            LivingEntity farthest = null;
            double dist = 0;
            for(LivingEntity le : targets) {
                double distToLE = le.getPos().distanceTo(new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
                if(farthest == null || distToLE > dist) {
                    farthest = le;
                    dist = distToLE;
                }
            }
            return farthest;
        }),
        STRONGEST("targeting_type.defense_systems.strong", (targetingIndex, targets, pos) -> {
            LivingEntity target = null;
            float hp = 0;
            for(LivingEntity le : targets) {
                float hp1 = le.getMaxHealth();
                if(target == null || hp1 > hp) {
                    target = le;
                    hp = hp1;
                }
            }
            return target;
        }),
        WEAKEST("targeting_type.defense_systems.weak", (targetingIndex, targets, pos) -> {
            LivingEntity target = null;
            float hp = 0;
            for(LivingEntity le : targets) {
                float hp1 = le.getMaxHealth();
                if(target == null || hp1 < hp) {
                    target = le;
                    hp = hp1;
                }
            }
            return target;
        }),
        HEALED("targeting_type.defense_systems.heal", (targetingIndex, targets, pos) -> {
            LivingEntity target = null;
            float hp = 0;
            for(LivingEntity le : targets) {
                float hp1 = le.getHealth();
                if(target == null || hp1 > hp) {
                    target = le;
                    hp = hp1;
                }
            }
            return target;
        }),
        DAMAGED("targeting_type.defense_systems.dmg", (targetingIndex, targets, pos) -> {
            LivingEntity target = null;
            float hp = 0;
            for(LivingEntity le : targets) {
                float hp1 = le.getHealth();
                if(target == null || hp1 < hp) {
                    target = le;
                    hp = hp1;
                }
            }
            return target;
        }),
        DEFENSE_OFF("targeting_type.defense_systems.off", (targetingIndex, targets, pos) -> null);

        private final String translationKey;
        private final TargetingFunction tf;

        TargetingType(String translationKey, TargetingFunction tf) {
            this.translationKey = translationKey;
            this.tf = tf;
        }

        public String getTranslationKey() {
            return translationKey;
        }

        public LivingEntity findTarget(int targetingIndex, List<LivingEntity> targets, BlockPos pos) {
            return tf.findTarget(targetingIndex, targets, pos);
        }

        public TargetingType next() {
            int nOrdinal = ordinal() + 1;
            if(nOrdinal >= values().length) nOrdinal = 0;
            return values()[nOrdinal];
        }

    }

    @FunctionalInterface
    public interface TargetingFunction {

        LivingEntity findTarget(int targetingIndex, List<LivingEntity> targets, BlockPos pos);

    }

    private static class StoredPlayerData {

        private final UUID id;
        private String lKU;

        private StoredPlayerData(UUID id, String lKU) {
            this.id = id;
            this.lKU = lKU;
        }

        public UUID getId() {
            return id;
        }

        public String getlKU() {
            return lKU;
        }

        public void setlKU(String lKU) {
            this.lKU = lKU;
        }

        public void writeToNBT(NbtCompound c) {
            c.putUuid("PlayerID", id);
            c.putString("PlayerLKU", lKU);
        }

        public static StoredPlayerData readFromNBT(NbtCompound c) {
            UUID id = c.getUuid("PlayerID");
            String lKU = c.getString("PlayerLKU");
            return new StoredPlayerData(id, lKU);
        }

    }

}
