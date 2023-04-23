package io.github.red050911.defensesystems.util;

import io.github.red050911.defensesystems.obj.blockentity.DefenseComputerBlockEntity;
import net.minecraft.entity.LivingEntity;

import java.util.UUID;

public interface IDefenseTickable {

    void tickTarget(LivingEntity target, DefenseComputerBlockEntity be);

    void initialize(int x, int y, int z);

    UUID getOwnerID();

    boolean isInitialized(int x, int y, int z);

    boolean isInitializedAtAll();

}
