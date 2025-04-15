package com.startraveler.bearminimum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EntityType.class)
public class EntityTypeMixin {

    @WrapOperation(method = "<clinit>", slice = {@Slice(from = @At(value = "CONSTANT", args = "stringValue=polar_bear"))}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType$Builder;sized(FF)Lnet/minecraft/world/entity/EntityType$Builder;", ordinal = 0))
    private static EntityType.Builder<?> bearminimum$boostPolarBearSize(EntityType.Builder<?> instance, float originalX, float originalY, Operation<EntityType.Builder<?>> original) {
        return instance.sized(originalX * 1.2f, originalY * 1.2f);
    }


}
