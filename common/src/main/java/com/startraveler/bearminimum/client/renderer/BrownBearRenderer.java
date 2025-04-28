package com.startraveler.bearminimum.client.renderer;

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

}
