package com.startraveler.bearminimum.entity;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class AbstractBearEntity extends PolarBear {

    public static final float SCARED_BOOST = 1.5f;
    public static final Integer TICKS_TILL_HUNGRY_AGAIN = 40;
    public static final String EAT_CROP_AGAIN_TICKS_ID = "EatCropsAgainTicks";
    public final BearFoodPreferences foodPreferences;
    int eatCropAgainTicks = 0;

    public AbstractBearEntity(EntityType<? extends AbstractBearEntity> entityType, Level level, BearFoodPreferences foodPreferences) {
        super(entityType, level);
        this.foodPreferences = foodPreferences;
    }

    @Override
    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity entity) {
        this.eatCropAgainTicks = TICKS_TILL_HUNGRY_AGAIN * 20;
        return super.killedEntity(level, entity);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(this.foodPreferences.foodTag);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.eatCropAgainTicks = compound.getIntOr(EAT_CROP_AGAIN_TICKS_ID, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(EAT_CROP_AGAIN_TICKS_ID, this.eatCropAgainTicks);
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


    boolean wantsMoreFood() {
        return this.eatCropAgainTicks <= 0;
    }

    public record BearFoodPreferences(TagKey<Item> foodTag, TagKey<EntityType<?>> preyTag, TagKey<Block> forageTag) {
    }

    static class AbstractBearHurtByTargetGoal extends HurtByTargetGoal {
        private final AbstractBearEntity bear;

        public AbstractBearHurtByTargetGoal(AbstractBearEntity bear) {
            super(bear);
            this.bear = bear;
        }

        public void start() {
            super.start();
            if (this.bear.isBaby()) {
                this.alertOthers();
                this.stop();
            }
        }

        protected void alertOther(Mob mob, LivingEntity target) {
            if (mob instanceof PolarBear && !mob.isBaby()) {
                super.alertOther(mob, target);
            }
        }
    }

    class AbstractBearAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {
        private final AbstractBearEntity bear;

        public AbstractBearAttackPlayersGoal(AbstractBearEntity bear) {
            super(bear, Player.class, 20, true, true, null);
            this.bear = bear;
        }

        public boolean canUse() {
            if (this.bear.isBaby()) {
                return false;
            } else {
                if (super.canUse()) {
                    for (PolarBear polarbear : this.bear.level()
                            .getEntitiesOfClass(
                                    PolarBear.class,
                                    this.bear.getBoundingBox().inflate(8.0F, 4.0F, 8.0F)
                            )) {
                        if (polarbear.isBaby()) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5F;
        }
    }

    class AbstractBearMeleeAttackGoal extends MeleeAttackGoal {
        private final AbstractBearEntity bear;

        public AbstractBearMeleeAttackGoal(AbstractBearEntity bear) {
            super(bear, (double) 1.25F, true);
            this.bear = bear;
        }

        public void stop() {
            this.bear.setStanding(false);
            super.stop();
        }

        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(getServerLevel(this.mob), target);
                this.bear.setStanding(false);
            } else if (this.mob.distanceToSqr(target) < (double) ((target.getBbWidth() + 3.0F) * (target.getBbWidth() + 3.0F))) {
                if (this.isTimeToAttack()) {
                    this.bear.setStanding(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 10) {
                    this.bear.setStanding(true);
                    this.bear.playWarningSound();
                }
            } else {
                this.resetAttackCooldown();
                this.bear.setStanding(false);
            }

        }
    }
}
