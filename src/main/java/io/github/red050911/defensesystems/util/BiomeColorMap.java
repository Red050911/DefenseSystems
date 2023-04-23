package io.github.red050911.defensesystems.util;

import net.minecraft.util.DyeColor;
import net.minecraft.world.biome.Biome;

public class BiomeColorMap {

    public static int toCamoColor(Biome.Category category) {
        switch(category) {
            case NONE:
                return -1;
            case TAIGA:
                return 0xF9FFFE;
            case EXTREME_HILLS:
                return 0x9D9D97;
            case JUNGLE:
            case PLAINS:
            case FOREST:
                return 8439583;
            case MESA:
                return 16351261;
            case SAVANNA:
            case THEEND:
            case BEACH:
            case DESERT:
                return 16701501;
            case ICY:
            case RIVER:
                return 3847130;
            case OCEAN:
                return 3949738;
            case SWAMP:
                return 6192150;
            case MUSHROOM:
                return 4673362;
            case NETHER:
                return 11546150;
        }
        return -1;
    }

}
