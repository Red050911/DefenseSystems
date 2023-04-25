package io.github.red050911.defensesystems;

import io.github.red050911.defensesystems.obj.blockentity.DefenseComputerBlockEntity;
import io.github.red050911.defensesystems.reg.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.BlockEntityTickInvoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefenseSystems implements ModInitializer {

    public static final String MOD_ID = "defense_systems";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("DefenseSystems$onInitialize Invoked! Defense Systems is loading...");
        LOGGER.info("Object registration beginning...");
        // registration begin
        LOGGER.info("Go, ITEMS!");
        ModItems.register();
        LOGGER.info("Go, BLOCKS!");
        ModBlocks.register();
        LOGGER.info("Go, BLOCK ENTITY TYPES!");
        ModBlockEntityTypes.register();
        LOGGER.info("Go, STATUS EFFECTS!");
        ModStatusEffects.register();
        LOGGER.info("Go, ENTITY TYPES!");
        ModEntityTypes.register();
        LOGGER.info("Go, SOUND EVENTS!");
        ModSoundEvents.register();
        LOGGER.info("Go, ENCHANTMENTS!");
        ModEnchantments.register();
        // registration end
        LOGGER.info("Object registration complete! Proceeding to event listener registration...");
        LOGGER.info("Registering listener for ServerPlayConnectionEvents.JOIN");
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            for(ServerWorld sw : server.getWorlds()) {
                for(BlockEntityTickInvoker beTickInvoker : sw.blockEntityTickers) {
                    BlockEntity be = sw.getBlockEntity(beTickInvoker.getPos());
                    if(be instanceof DefenseComputerBlockEntity) {
                        ((DefenseComputerBlockEntity) be).onPlayerJoin(handler.player);
                    }
                }
            }
        });
        LOGGER.info("Event listener registration complete.");
        LOGGER.info("Defense Systems is GTG!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

}
