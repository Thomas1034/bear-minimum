package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import com.startraveler.bearminimum.client.renderer.BrownBearRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BearMinimumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(BearMinimum.BLACK_BEAR, BlackBearRenderer::new);
        EntityRendererRegistry.register(BearMinimum.BROWN_BEAR, BrownBearRenderer::new);
    }
}
