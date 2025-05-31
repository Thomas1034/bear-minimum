package com.startraveler.bearminimum.client.renderer;

import com.startraveler.bearminimum.Constants;
import com.startraveler.bearminimum.client.model.BrownBearModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class BrownBearRenderer extends AgeableMobRenderer<PolarBear, PolarBearRenderState, BrownBearModel> {

    private static final ResourceLocation BEAR_LOCATION = Constants.id("textures/entity/bear/brown_bear.png");

    public BrownBearRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new BrownBearModel(context.bakeLayer(BrownBearModel.BODY_LAYER)),
                new BrownBearModel(context.bakeLayer(BrownBearModel.BODY_LAYER_BABY)),
                0.9F
        );
    }

    @Override
    public ResourceLocation getTextureLocation(PolarBearRenderState state) {
        return BEAR_LOCATION;
    }

    public void extractRenderState(PolarBear bear, PolarBearRenderState state, float tickDelta) {
        super.extractRenderState(bear, state, tickDelta);
        state.standScale = bear.getStandingAnimationScale(tickDelta);
    }

    public PolarBearRenderState createRenderState() {
        return new PolarBearRenderState();
    }

}
