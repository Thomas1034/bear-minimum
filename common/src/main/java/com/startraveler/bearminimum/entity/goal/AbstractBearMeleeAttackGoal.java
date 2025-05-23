package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class AbstractBearMeleeAttackGoal extends MeleeAttackGoal {
    private final AbstractBearEntity bear;

    public AbstractBearMeleeAttackGoal(AbstractBearEntity bear) {
        super(bear, (double) 1.25F, true);
        this.bear = bear;
    }

    public void stop() {
        this.bear.setStanding(false);
        super.stop();
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double $$2 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= $$2 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
            this.bear.setStanding(false);
        } else if (distToEnemySqr <= $$2 * (double) 2.0F) {
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
