package io.github.red050911.defensesystems.client.model;

import io.github.red050911.defensesystems.obj.entity.TurretEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.7.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class TurretModel extends EntityModel<TurretEntity> {

	private final ModelPart turret;

	public TurretModel(ModelPart root) {
		this.turret = root.getChild("turret");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData turret = modelPartData.addChild("turret", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData post = turret.addChild("post", ModelPartBuilder.create().uv(12, 15).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(-2.0F, -10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.0F, -8.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = turret.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -15.0F, -3.0F, 6.0F, 5.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 19).cuboid(-1.0F, -13.0F, -8.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(TurretEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		turret.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}