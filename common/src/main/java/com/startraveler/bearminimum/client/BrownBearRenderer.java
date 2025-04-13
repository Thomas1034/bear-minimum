package com.startraveler.bearminimum.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.world.entity.animal.PolarBear;

public class BrownBearRenderer extends PolarBearRenderer {

    public BrownBearRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
    }

    protected void scale(PolarBear $$0, PoseStack $$1, float $$2) {
        // Counteract polar bear scaling
        $$1.scale(1/1.2F, 1/1.2F, 1/1.2F);
        super.scale($$0, $$1, $$2);
    }
}
