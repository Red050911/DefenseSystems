package io.github.red050911.defensesystems.util;

import io.github.red050911.defensesystems.obj.blockentity.DefenseComputerBlockEntity;
import net.minecraft.util.math.Box;

import java.util.UUID;

public interface ISurveillanceTickable {

    void tickFind(DefenseComputerBlockEntity be, Box securityZone);

    void initialize(int x, int y, int z);

    UUID getOwnerID();

    boolean isInitialized(int x, int y, int z);

    boolean isInitializedAtAll();

}
