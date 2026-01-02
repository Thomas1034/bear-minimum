package com.startraveler.bearminimum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.startraveler.bearminimum.entity.AbstractBearEntity;
import com.startraveler.bearminimum.entity.goal.PersonalSpaceTargetGoal;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBear.class)
public abstract class PolarBearMixin {

    @Unique
    private static final float bearminimum$PERSONAL_SPACE_DISTANCE = 8.0f;

    @ModifyExpressionValue(method = "createAttributes", at = @At(value = "CONSTANT", args = "doubleValue=30.0"))
    private static double bearminimum$boostPolarBearHealth(double original) {
        return original * 2 + (original / 2);
    }

    @ModifyExpressionValue(method = "createAttributes", at = @At(value = "CONSTANT", args = "doubleValue=6.0"))
    private static double bearminimum$boostPolarBearDamage(double original) {
        return original * 2;
    }

    @ModifyExpressionValue(method = "createAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Animal;createAnimalAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder bearminimum$boostPolarBearDamage(AttributeSupplier.Builder original) {
        return original.add(Attributes.ARMOR, 10.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5F)
                .add(Attributes.SCALE, 1.2);
    }

    @ModifyExpressionValue(method = "playStepSound", at = @At(value = "CONSTANT", args = "floatValue=0.15"))
    protected float bearminimum$amplifyStepSound(float originalVolume) {
        return originalVolume * (((PolarBear) (Object) (this)).isAngry() ? 2 : 1);
    }

    @Inject(method = "registerGoals", at = @At(value = "TAIL"))
    public void bearminimum$addGoals(CallbackInfo ci) {
        PolarBear thisBear = (PolarBear) (Object) this;
        // is my bear, this bear is your bear...
        ((MobMixin) thisBear).getGoalSelector().addGoal(5, new BreedGoal(thisBear, 0.8));
        ((MobMixin) thisBear).getGoalSelector().addGoal(
                8, new AvoidEntityGoal<>(
                        thisBear,
                        Player.class,
                        (entity) -> !thisBear.isAngry(),
                        bearminimum$PERSONAL_SPACE_DISTANCE * 2,
                        1 / AbstractBearEntity.SCARED_BOOST,
                        1 / AbstractBearEntity.SCARED_BOOST,
                        EntitySelector.NO_CREATIVE_OR_SPECTATOR
                )
        );

        ((MobMixin) thisBear).getGoalSelector().addGoal(
                8, new AvoidEntityGoal<>(
                        thisBear,
                        Villager.class,
                        (entity) -> !thisBear.isAngry(),
                        bearminimum$PERSONAL_SPACE_DISTANCE * 2,
                        1 / AbstractBearEntity.SCARED_BOOST,
                        1 / AbstractBearEntity.SCARED_BOOST,
                        EntitySelector.NO_CREATIVE_OR_SPECTATOR
                )
        );

        ((MobMixin) thisBear).getGoalSelector().addGoal(
                7, new PersonalSpaceTargetGoal<>(
                        thisBear,
                        Player.class,
                        bearminimum$PERSONAL_SPACE_DISTANCE,
                        10,
                        true,
                        true,
                        (entity, level) -> true
                )
        );
    }

}
