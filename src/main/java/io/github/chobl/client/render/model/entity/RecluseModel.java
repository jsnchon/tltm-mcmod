package io.github.chobl.client.render.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chobl.common.entities.RecluseEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RecluseModel<T extends Entity> extends SegmentedModel<RecluseEntity> {
    private final ModelRenderer recluse;
    private final ModelRenderer head;
    private final ModelRenderer head_nub_1_r1;
    private final ModelRenderer head_nub_4;
    private final ModelRenderer head_nub_5;
    private final ModelRenderer body;
    private final ModelRenderer leg_nub_3_r1;
    private final ModelRenderer leg_nub_2_r1;
    private final ModelRenderer leg_nub_1_r1;
    private final ModelRenderer leg_joint_r_r1;
    private final ModelRenderer leg_joint_l_r1;

    public RecluseModel() {
        textureWidth = 32;
        textureHeight = 32;

        recluse = new ModelRenderer(this);
        recluse.setRotationPoint(0.0F, 16.0F, 0.0F);
        setRotationAngle(recluse, 0.0F, 3.1416F, 0.0F);


        head = new ModelRenderer(this);
        head.setRotationPoint(-1.0F, -1.0F, 0.5F);
        recluse.addChild(head);
        head.setTextureOffset(16, 23).addBox(0.5F, -30.0F, 0.5F, 3.0F, 4.0F, 3.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addBox(-3.5F, -31.0F, -3.5F, 4.0F, 8.0F, 8.0F, 0.0F, false);
        head.setTextureOffset(0, 16).addBox(0.5F, -31.0F, -3.5F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(16, 0).addBox(0.5F, -26.0F, 0.5F, 4.0F, 3.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(0, 0).addBox(0.5F, -31.0F, 0.5F, 1.0F, 5.0F, 2.0F, 0.0F, false);
        head.setTextureOffset(0, 16).addBox(3.5F, -29.0F, 0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

        head_nub_1_r1 = new ModelRenderer(this);
        head_nub_1_r1.setRotationPoint(3.5F, 0.0F, 0.6F);
        head.addChild(head_nub_1_r1);
        setRotationAngle(head_nub_1_r1, 0.0F, -1.5708F, 0.0F);
        head_nub_1_r1.setTextureOffset(24, 10).addBox(2.9F, -27.0F, 1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        head_nub_4 = new ModelRenderer(this);
        head_nub_4.setRotationPoint(1.6F, -29.0F, 3.85F);
        head.addChild(head_nub_4);
        setRotationAngle(head_nub_4, 0.0F, 0.0F, -0.6109F);
        head_nub_4.setTextureOffset(24, 7).addBox(-2.3629F, 2.1266F, -0.5F, 2.0F, 2.0F, 1.0F, 0.0F, false);

        head_nub_5 = new ModelRenderer(this);
        head_nub_5.setRotationPoint(5.45F, -26.8F, 6.55F);
        head.addChild(head_nub_5);
        setRotationAngle(head_nub_5, -0.5672F, 0.0F, 0.0F);
        head_nub_5.setTextureOffset(12, 16).addBox(-2.1F, 2.5373F, -4.3434F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(-0.5F, 0.0F, 0.5F);
        recluse.addChild(body);
        body.setTextureOffset(0, 0).addBox(-5.0F, -24.0F, -2.0F, 10.0F, 15.0F, 5.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(5.0F, -24.0F, -2.0F, 5.0F, 13.0F, 5.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(-10.0F, -24.0F, -2.0F, 5.0F, 13.0F, 5.0F, 0.0F, true);
        body.setTextureOffset(0, 0).addBox(1.85F, -9.0F, -2.0F, 4.0F, 17.0F, 5.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addBox(-5.85F, -9.0F, -2.0F, 4.0F, 12.0F, 5.0F, 0.0F, true);
        body.setTextureOffset(0, 0).addBox(-3.85F, 3.0F, -2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        leg_nub_3_r1 = new ModelRenderer(this);
        leg_nub_3_r1.setRotationPoint(-2.85F, 5.0F, 2.0F);
        body.addChild(leg_nub_3_r1);
        setRotationAngle(leg_nub_3_r1, 0.0F, -2.4871F, 0.0F);
        leg_nub_3_r1.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, 2.0F, 1.0F, 5.0F, 2.0F, 0.0F, false);

        leg_nub_2_r1 = new ModelRenderer(this);
        leg_nub_2_r1.setRotationPoint(-2.85F, 5.0F, -1.0F);
        body.addChild(leg_nub_2_r1);
        setRotationAngle(leg_nub_2_r1, 0.0F, -1.5708F, 0.0F);
        leg_nub_2_r1.setTextureOffset(0, 0).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 5.0F, 3.0F, 0.0F, false);

        leg_nub_1_r1 = new ModelRenderer(this);
        leg_nub_1_r1.setRotationPoint(-2.7F, 4.0F, 0.4F);
        body.addChild(leg_nub_1_r1);
        setRotationAngle(leg_nub_1_r1, 0.0F, -0.2182F, 0.0F);
        leg_nub_1_r1.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, -2.0F, 2.0F, 1.0F, 4.0F, 0.0F, true);

        leg_joint_r_r1 = new ModelRenderer(this);
        leg_joint_r_r1.setRotationPoint(-19.325F, -6.5F, -2.25F);
        body.addChild(leg_joint_r_r1);
        setRotationAngle(leg_joint_r_r1, 0.0F, 0.0F, 0.4363F);
        leg_joint_r_r1.setTextureOffset(24, 7).addBox(11.3086F, -9.6717F, 0.85F, 2.0F, 2.0F, 4.0F, 0.0F, true);

        leg_joint_l_r1 = new ModelRenderer(this);
        leg_joint_l_r1.setRotationPoint(19.325F, -6.5F, -2.25F);
        body.addChild(leg_joint_l_r1);
        setRotationAngle(leg_joint_l_r1, 0.0F, 0.0F, -0.4363F);
        leg_joint_l_r1.setTextureOffset(24, 7).addBox(-13.3086F, -9.6717F, 0.85F, 2.0F, 2.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(RecluseEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        recluse.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return null;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
