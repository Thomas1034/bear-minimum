package com.startraveler.bearminimum.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.world.entity.animal.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearRenderer.class)
public class PolarBearRendererMixin {

    @Inject(method = "scale(Lnet/minecraft/world/entity/animal/PolarBear;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At(value = "HEAD"))
    public void modifyScale(PolarBear $$0, PoseStack $$1, float $$2, CallbackInfo ci) {
        $$1.scale(1.2F, 1.2F, 1.2F);
    }

}
