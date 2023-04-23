package io.github.red050911.defensesystems.mixin;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {

    // Patch out the weird anvil bug
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void defense_systems$fixAnvilBugRead(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("DestroyedOnLanding", NbtType.BYTE)) {
            ((FallingBlockEntity)(Object)this).destroyedOnLanding = nbt.getBoolean("DestroyedOnLanding");
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void defense_systems$fixAnvilBugWrite(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("DestroyedOnLanding", ((FallingBlockEntity)(Object)this).destroyedOnLanding);
    }
    // End anvil patch

}
