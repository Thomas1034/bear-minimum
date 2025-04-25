package com.startraveler.bearminimum.entity;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBearEntity extends PolarBear {

    public static final float SCARED_BOOST = 1.5f;
    public static final Integer TICKS_TILL_HUNGRY_AGAIN = 40;
    public static final String EAT_CROP_AGAIN_TICKS_ID = "EatCropsAgainTicks";
    public int eatCropAgainTicks = 0;

    public AbstractBearEntity(EntityType<? extends AbstractBearEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isAngry();
    }

    public abstract BearFoodPreferences getFoodPreferences();

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(this.getFoodPreferences().foodTag());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.eatCropAgainTicks = 0;
        if (compound.contains(EAT_CROP_AGAIN_TICKS_ID)) {
            this.eatCropAgainTicks = compound.getInt(EAT_CROP_AGAIN_TICKS_ID);
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(EAT_CROP_AGAIN_TICKS_ID, this.eatCropAgainTicks);
    }

    @Override
    public void playWarningSound() {
        super.playWarningSound();
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean succeeded = super.doHurtTarget(target);
        if (succeeded && !target.isAlive()) {
            this.eatCropAgainTicks = TICKS_TILL_HUNGRY_AGAIN * 20;
        }
        return succeeded;
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();

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