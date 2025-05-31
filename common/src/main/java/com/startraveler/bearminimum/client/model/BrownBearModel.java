package com.startraveler.bearminimum.client.model;

import com.startraveler.bearminimum.Constants;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;

import java.util.Set;

public class BrownBearModel extends QuadrupedModel<PolarBearRenderState> {
    public static final ModelLayerLocation BODY_LAYER = new ModelLayerLocation(Constants.id("brown_bear"), "main");
    public static final ModelLayerLocation BODY_LAYER_BABY = new ModelLayerLocation(
            Constants.id("brown_bear_baby"),
            "main"
    );

    private static final float BABY_HEAD_SCALE = 2.25F;
    private static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(
            true,
            16.0F,
            4.0F,
            BABY_HEAD_SCALE,
            2.0F,
            24.0F,
            Set.of("head")
    );


    public BrownBearModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer(boolean isBaby) {
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

        return LayerDefinition.create(meshdefinition, 128, 64)
                .apply(isBaby ? BABY_TRANSFORMER : MeshTransformer.IDENTITY)
                .apply(MeshTransformer.scaling(1.2F));
    }

    public void setupAnim(PolarBearRenderState renderState) {
        super.setupAnim(renderState);
        float standScaleSquared = renderState.standScale * renderState.standScale;
        float ageScale = renderState.ageScale;
        float headScale = renderState.isBaby ? 0.44444445F : 1.0F;
        ModelPart modelPart = this.body;
        modelPart.xRot -= standScaleSquared * (float)Math.PI * 0.35F;
        modelPart = this.body;
        modelPart.y += standScaleSquared * ageScale * 2.0F;
        modelPart = this.rightFrontLeg;
        modelPart.y -= standScaleSquared * ageScale * 20.0F;
        modelPart = this.rightFrontLeg;
        modelPart.z += standScaleSquared * ageScale * 4.0F;
        modelPart = this.rightFrontLeg;
        modelPart.xRot -= standScaleSquared * (float)Math.PI * 0.45F;
        this.leftFrontLeg.y = this.rightFrontLeg.y;
        this.leftFrontLeg.z = this.rightFrontLeg.z;
        modelPart = this.leftFrontLeg;
        modelPart.xRot -= standScaleSquared * (float)Math.PI * 0.45F;
        modelPart = this.head;
        modelPart.y -= standScaleSquared * headScale * 24.0F; // was 24
        modelPart = this.head;
        modelPart.z += standScaleSquared * headScale * 11.0F; // was 13
        modelPart = this.head;
        modelPart.xRot += standScaleSquared * (float)Math.PI * 0.15F;
    }
}
