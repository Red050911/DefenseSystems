package io.github.red050911.defensesystems.client.render;

import io.github.red050911.defensesystems.DefenseSystems;
import io.github.red050911.defensesystems.client.model.TurretModel;
import io.github.red050911.defensesystems.obj.entity.TurretEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class TurretEntityRenderer extends MobEntityRenderer<TurretEntity, TurretModel> {

    public TurretEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, TurretModel entityModel, float f) {
        super(entityRenderDispatcher, entityModel, f);
    }

    @Override
    public Identifier getTexture(TurretEntity entity) {
        return DefenseSystems.id("textures/entity/turret.png");
    }

}
