package com.startraveler.bearminimum.entity;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class AbstractBearEntity extends PolarBear {

    public static final float SCARED_BOOST = 1.5f;
    public static final Integer TICKS_TILL_HUNGRY_AGAIN = 40;
    public static final String EAT_CROP_AGAIN_TICKS_ID = "EatCropsAgainTicks";
    public int eatCropAgainTicks = 0;

    public AbstractBearEntity(EntityType<? extends AbstractBearEntity> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity, DamageSource damageSource) {
        this.eatCropAgainTicks = TICKS_TILL_HUNGRY_AGAIN * 20;
        return super.killedEntity(level, entity, damageSource);
    }

    public abstract BearFoodPreferences getFoodPreferences();

    public boolean isFood(ItemStack stack) {
        return stack.is(this.getFoodPreferences().foodTag());
    }

    @Override
    public void readAdditionalSaveData(ValueInput compound) {
        super.readAdditionalSaveData(compound);
        this.eatCropAgainTicks = compound.getIntOr(EAT_CROP_AGAIN_TICKS_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(EAT_CROP_AGAIN_TICKS_ID, this.eatCropAgainTicks);
    }

    @Override
    public void playWarningSound() {
        super.playWarningSound();
    }

    @Override
    public void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);

        if (this.eatCropAgainTicks > 0) {
            this.eatCropAgainTicks -= this.random.nextInt(3);
            if (this.eatCropAgainTicks < 0) {
                this.eatCropAgainTicks = 0;
            }
        }
    }

    public boolean wantsMoreFood() {
        return this.eatCropAgainTicks <= 0;
    }

}
