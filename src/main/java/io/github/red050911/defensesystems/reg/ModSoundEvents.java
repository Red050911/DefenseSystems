package io.github.red050911.defensesystems.reg;

import io.github.red050911.defensesystems.DefenseSystems;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {

    public static SoundEvent GENERIC_BOOT_UP;
    public static SoundEvent GENERIC_SHUTDOWN;
    public static SoundEvent COMPUTER_TARGET;
    public static SoundEvent COMPUTER_LOSE_TARGET;

    public static void register() {
        Identifier genericBootUp = DefenseSystems.id("block.defense_systems_generic.bootup");
        GENERIC_BOOT_UP = Registry.register(Registry.SOUND_EVENT, genericBootUp, new SoundEvent(genericBootUp));
        Identifier genericShutdown = DefenseSystems.id("block.defense_systems_generic.shutdown");
        GENERIC_SHUTDOWN = Registry.register(Registry.SOUND_EVENT, genericShutdown, new SoundEvent(genericShutdown));
        Identifier computerTarget = DefenseSystems.id("block.defense_computer.target");
        COMPUTER_TARGET = Registry.register(Registry.SOUND_EVENT, computerTarget, new SoundEvent(computerTarget));
        Identifier computerLoseTarget = DefenseSystems.id("block.defense_computer.lose_target");
        COMPUTER_LOSE_TARGET = Registry.register(Registry.SOUND_EVENT, computerLoseTarget, new SoundEvent(computerLoseTarget));
    }

}
