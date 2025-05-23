package com.startraveler.bearminimum.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.bearminimum.Constants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class BrownBearRenderer extends PolarBearRenderer {

    private static final ResourceLocation BEAR_LOCATION = Constants.id("textures/entity/bear/brown_bear.png");

    public BrownBearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PolarBear bear) {
        return BEAR_LOCATION;
    }

    @Override
    protected void scale(PolarBear $$0, PoseStack $$1, float $$2) {
        // Counteract polar bear scaling
        $$1.scale(1 / 1.2F, 1 / 1.2F, 1 / 1.2F);
        super.scale($$0, $$1, $$2);
    }

}
