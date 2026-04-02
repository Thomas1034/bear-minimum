package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.model.BlackBearModel;
import com.startraveler.bearminimum.client.model.BrownBearModel;
import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import com.startraveler.bearminimum.client.renderer.BrownBearRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class BearMinimumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(BearMinimum.BLACK_BEAR, BlackBearRenderer::new);
        EntityRenderers.register(BearMinimum.BROWN_BEAR, BrownBearRenderer::new);
        ModelLayerRegistry.registerModelLayer(BlackBearModel.BODY_LAYER, () -> BlackBearModel.createBodyLayer(false));
        ModelLayerRegistry.registerModelLayer(
                BlackBearModel.BODY_LAYER_BABY,
                () -> BlackBearModel.createBodyLayer(true)
        );
        ModelLayerRegistry.registerModelLayer(BrownBearModel.BODY_LAYER, () -> BrownBearModel.createBodyLayer(false));
        ModelLayerRegistry.registerModelLayer(
                BrownBearModel.BODY_LAYER_BABY,
                () -> BrownBearModel.createBodyLayer(true)
        );
    }
}
