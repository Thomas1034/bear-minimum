package com.startraveler.bearminimum.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.bearminimum.Constants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class BlackBearRenderer extends PolarBearRenderer {
    private static final ResourceLocation BEAR_LOCATION = Constants.id("textures/entity/bear/black_bear.png");

    public BlackBearRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
    }

    protected void scale(PolarBear $$0, PoseStack $$1, float $$2) {
        // Counteract polar bear scaling
        $$1.scale(1/1.2F, 1/1.2F, 1/1.2F);
        // Shrink down further.
        $$1.scale(0.8f, 0.8f, 0.8f);
        super.scale($$0, $$1, $$2);
    }

    public ResourceLocation getTextureLocation(PolarBear $$0) {
        return BlackBearRenderer.BEAR_LOCATION;
    }
}
