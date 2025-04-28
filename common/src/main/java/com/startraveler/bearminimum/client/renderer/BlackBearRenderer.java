package com.startraveler.bearminimum.client.renderer;

import com.startraveler.bearminimum.Constants;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class BlackBearRenderer extends PolarBearRenderer {

    private static final ResourceLocation BEAR_LOCATION = Constants.id("textures/entity/bear/black_bear.png");

    public BlackBearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(PolarBear bear) {
        return BEAR_LOCATION;
    }

}
