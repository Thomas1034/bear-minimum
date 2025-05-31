package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.BlackBearModel;
import com.startraveler.bearminimum.client.BlackBearRenderer;
import com.startraveler.bearminimum.client.BrownBearModel;
import com.startraveler.bearminimum.client.BrownBearRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BearMinimumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(BearMinimum.BLACK_BEAR, BlackBearRenderer::new);
        EntityRendererRegistry.register(BearMinimum.BROWN_BEAR, BrownBearRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BlackBearModel.BODY_LAYER, BlackBearModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BrownBearModel.BODY_LAYER, BrownBearModel::createBodyLayer);
    }
}