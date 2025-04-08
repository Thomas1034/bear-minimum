package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BearMinimumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(BearMinimum.BLACK_BEAR, BlackBearRenderer::new);
    }
}
