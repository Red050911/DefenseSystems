// Made with Blockbench 4.7.0
// Exported for Minecraft version 1.15 - 1.16
// Paste this class into your mod and generate all required imports

package io.github.red050911.defensesystems.client.model;

import io.github.red050911.defensesystems.obj.entity.TurretEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Turret entity model made in Blockbench
public class TurretModel extends EntityModel<TurretEntity> {
    private final ModelPart turret;
    private final ModelPart post;
    private final ModelPart head;

    public TurretModel() {
        textureWidth = 32;
        textureHeight = 32;
        turret = new ModelPart(this);
        turret.setPivot(0.0F, 24.0F, 0.0F);

        post = new ModelPart(this);
        post.setPivot(0.0F, 0.0F, 0.0F);
        turret.addChild(post);
        post.setTextureOffset(12, 15).addCuboid(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        post.setTextureOffset(0, 13).addCuboid(-2.0F, -10.0F, -2.0F, 4.0F, 2.0F, 4.0F, 0.0F, false);
        post.setTextureOffset(0, 0).addCuboid(-1.0F, -8.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPivot(0.0F, 0.0F, 0.0F);
        turret.addChild(head);
        head.setTextureOffset(0, 0).addCuboid(-3.0F, -15.0F, -3.0F, 6.0F, 5.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(0, 19).addCuboid(-1.0F, -13.0F, -8.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setAngles(TurretEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        turret.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart bone, float x, float y, float z) {
        bone.pitch = x;
        bone.yaw = y;
        bone.roll = z;
    }

}