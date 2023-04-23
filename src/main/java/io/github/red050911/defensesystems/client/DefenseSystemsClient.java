package io.github.red050911.defensesystems.client;

import io.github.red050911.defensesystems.client.model.TurretModel;
import io.github.red050911.defensesystems.client.render.TurretEntityRenderer;
import io.github.red050911.defensesystems.obj.entity.BulletProjectile;
import io.github.red050911.defensesystems.reg.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class DefenseSystemsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TURRET, (manager, context) -> new TurretEntityRenderer(manager, new TurretModel(), 0));
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.BULLET, (manager, context) -> new FlyingItemEntityRenderer<BulletProjectile>(manager, MinecraftClient.getInstance().getItemRenderer()));
    }

}
