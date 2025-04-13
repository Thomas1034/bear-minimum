package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.PolarBear;

public class AbstractBearHurtByTargetGoal extends HurtByTargetGoal {
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