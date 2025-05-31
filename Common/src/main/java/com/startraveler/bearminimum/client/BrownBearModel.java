package com.startraveler.bearminimum.client;

import com.startraveler.bearminimum.Constants;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.PolarBear;

public class BrownBearModel<T extends PolarBear> extends QuadrupedModel<T> {
    public static final ModelLayerLocation BODY_LAYER = new ModelLayerLocation(Constants.id("brown_bear"), "main");


    public BrownBearModel(ModelPart root) {
        super(root, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();


        PartDefinition head = partdefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -5.0F, -7.0F, 9.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 56)
                        .addBox(-2.5F, -2.0F, -11.0F, 5.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(21, 61)
                        .addBox(3.5F, -6.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(21, 61)
                        .addBox(-5.5F, -6.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.0F, -13.0F)
        );

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 30)
                        .addBox(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(37, 0)
                        .addBox(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 9.0F, 12.0F, 1.5708F, 0.0F, 0.0F)
        );

        PartDefinition rightHindLeg = partdefinition.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(50, 30)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.5F, 14.0F, 6.0F)
        );

        PartDefinition leftHindLeg = partdefinition.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(50, 30)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.5F, 14.0F, 6.0F)
        );

        PartDefinition rightFrontLeg = partdefinition.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create()
                        .texOffs(50, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, 14.0F, -8.0F)
        );

        PartDefinition leftFrontLeg = partdefinition.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create()
                        .texOffs(50, 48)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, 14.0F, -8.0F)
        );


        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float partialTick = ageInTicks - (float) entity.tickCount;
        float standAnimationScale = entity.getStandingAnimationScale(partialTick);
        standAnimationScale *= standAnimationScale;
        float complement = 1.0F - standAnimationScale;
        this.body.xRot = ((float) Math.PI / 2F) - standAnimationScale * (float) Math.PI * 0.35F;
        this.body.y = 9.0F * complement + 11.0F * standAnimationScale;
        this.rightFrontLeg.y = 14.0F * complement - 6.0F * standAnimationScale;
        this.rightFrontLeg.z = -8.0F * complement - 4.0F * standAnimationScale;
        ModelPart modelPart = this.rightFrontLeg;
        modelPart.xRot -= standAnimationScale * (float) Math.PI * 0.45F;
        this.leftFrontLeg.y = this.rightFrontLeg.y;
        this.leftFrontLeg.z = this.rightFrontLeg.z;
        modelPart = this.leftFrontLeg;
        modelPart.xRot -= standAnimationScale * (float) Math.PI * 0.45F;
        if (this.young) {
            this.head.y = 10.0F * complement - 9.0F * standAnimationScale;
            this.head.z = -14.0F * complement - 8.0F * standAnimationScale;
        } else {
            this.head.y = 10.0F * complement - 12.0F * standAnimationScale; // was -14
            this.head.z = -14.0F * complement - 1.0F * standAnimationScale; // was 3
        }

        modelPart = this.head;
        modelPart.xRot += standAnimationScale * (float) Math.PI * 0.15F;
    }
}