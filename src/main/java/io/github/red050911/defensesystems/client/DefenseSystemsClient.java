package io.github.red050911.defensesystems.client;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.client.model.TurretModel;
import io.github.red050911.defensesystems.client.render.TurretEntityRenderer;
import io.github.red050911.defensesystems.reg.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class DefenseSystemsClient implements ClientModInitializer {

    public static final EntityModelLayer TURRET_LAYER = new EntityModelLayer(DefenseSystems.id("turret"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.TURRET, TurretEntityRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.BULLET, FlyingItemEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(TURRET_LAYER, TurretModel::getTexturedModelData);
    }

}
