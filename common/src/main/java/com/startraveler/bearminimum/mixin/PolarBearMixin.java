package com.startraveler.bearminimum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.animal.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PolarBear.class)
public class PolarBearMixin {

    @ModifyExpressionValue(method = "playStepSound", at = @At(value = "CONSTANT", args = "intValue=0.15"))
    protected float verdant$amplifyStepSound(float originalVolume) {
        return originalVolume * (((PolarBear) (Object) (this)).isAngry() ? 2 : 1);
    }

}
