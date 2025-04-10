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
