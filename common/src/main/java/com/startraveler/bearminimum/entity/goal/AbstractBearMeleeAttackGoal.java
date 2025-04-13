package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AbstractBearMeleeAttackGoal extends MeleeAttackGoal {
    private final AbstractBearEntity bear;

    public AbstractBearMeleeAttackGoal(AbstractBearEntity bear) {
        super(bear, 1.25F, true);
        this.bear = bear;
    }

    public void stop() {
        this.bear.setStanding(false);
        super.stop();
    }

    protected void checkAndPerformAttack(LivingEntity $$0, double $$1) {
        double $$2 = this.getAttackReachSqr($$0);
        if ($$1 <= $$2 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget($$0);
            this.bear.setStanding(false);
        } else if ($$1 <= $$2 * (double) 2.0F) {
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

    protected double getAttackReachSqr(LivingEntity $$0) {
        return (double) (4.0F + $$0.getBbWidth());
    }
}