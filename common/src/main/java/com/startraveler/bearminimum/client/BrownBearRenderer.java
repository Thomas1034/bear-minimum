package com.startraveler.bearminimum.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.bearminimum.Constants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class BrownBearRenderer extends MobRenderer<PolarBear, BrownBearModel<PolarBear>> {

    private static final ResourceLocation BEAR_LOCATION = Constants.id("textures/entity/bear/brown_bear.png");

    public BrownBearRenderer(EntityRendererProvider.Context context) {
        super(context, new BrownBearModel<>(context.bakeLayer(BrownBearModel.BODY_LAYER)), 0.9F);
    }

    @Override
    public ResourceLocation getTextureLocation(PolarBear bear) {
        return BEAR_LOCATION;
    }

    protected void scale(PolarBear livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
        super.scale(livingEntity, poseStack, partialTickTime);
    }
}
