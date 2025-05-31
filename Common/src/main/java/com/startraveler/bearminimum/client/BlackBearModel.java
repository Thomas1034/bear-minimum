package com.startraveler.bearminimum.client;


import com.startraveler.bearminimum.Constants;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.animal.PolarBear;

public class BlackBearModel<T extends PolarBear> extends QuadrupedModel<T> {
    public static final ModelLayerLocation BODY_LAYER = new ModelLayerLocation(Constants.id("black_bear"), "main");


    public BlackBearModel(ModelPart root) {
        super(root, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();


        PartDefinition head = partdefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create()
                        .texOffs(1, 0)
                        .addBox(-3.0F, -3.0F, -6.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 44)
                        .addBox(-1.5F, 0.0F, -9.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 0)
                        .addBox(2.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 0)
                        .addBox(-4.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 12.0F, -11.0F)
        );

        PartDefinition body = partdefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(4, 21)
                        .addBox(-3.5F, -12.0F, -7.0F, 11.0F, 10.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 0)
                        .addBox(-3.5F, -23.0F, -7.0F, 11.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 10.0F, 12.0F, 1.5708F, 0.0F, 0.0F)
        );

        PartDefinition rightHindLeg = partdefinition.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(52, 24)
                        .addBox(-3.0F, 0.0F, -2.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, 16.0F, 5.0F)
        );

        PartDefinition leftHindLeg = partdefinition.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(52, 24)
                        .addBox(-1.0F, 0.0F, -2.0F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-4.0F, 16.0F, 5.0F)
        );

        PartDefinition rightFrontLeg = partdefinition.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create()
                        .texOffs(51, 41)
                        .addBox(-2.5F, 0.0F, -1.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, 16.0F, -7.0F)
        );

        PartDefinition leftFrontLeg = partdefinition.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create()
                        .texOffs(51, 41)
                        .addBox(-1.5F, 0.0F, -1.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, 16.0F, -7.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 64);
    }


    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        float partialTick = ageInTicks - (float) entity.tickCount;
        float standingScaleSquared = entity.getStandingAnimationScale(partialTick);
        standingScaleSquared *= standingScaleSquared;
        float complement = 1.0F - standingScaleSquared;
        this.body.xRot = ((float) Math.PI / 2F) - standingScaleSquared * (float) Math.PI * 0.35F;
        this.body.y = 10.0F * complement + 15.0F * standingScaleSquared;
        this.rightFrontLeg.y = 16.0F * complement - 1.0F * standingScaleSquared;
        this.rightFrontLeg.z = -7.0F * complement - 3.0F * standingScaleSquared;
        ModelPart part = this.rightFrontLeg;
        part.xRot -= standingScaleSquared * (float) Math.PI * 0.45F;
        this.leftFrontLeg.y = this.rightFrontLeg.y;
        this.leftFrontLeg.z = this.rightFrontLeg.z;
        part = this.leftFrontLeg;
        part.xRot -= standingScaleSquared * (float) Math.PI * 0.45F;
        if (this.young) {
            this.head.y = 10.0F * complement - 9.0F * standingScaleSquared;
            this.head.z = -16.0F * complement - 12.0F * standingScaleSquared;
        } else {
            this.head.y = 12.0F * complement - 7.0F * standingScaleSquared;
            this.head.z = -11.0F * complement - 1.0F * standingScaleSquared;
        }

        part = this.head;
        part.xRot += standingScaleSquared * (float) Math.PI * 0.15F;
    }
}
