package io.github.red050911.defensesystems;

import io.github.red050911.defensesystems.reg.ModStatusEffects;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class DefenseSystems implements ModInitializer {

    public static final String MOD_ID = "defense_systems";

    @Override
    public void onInitialize() {
        ModStatusEffects.register();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

}
