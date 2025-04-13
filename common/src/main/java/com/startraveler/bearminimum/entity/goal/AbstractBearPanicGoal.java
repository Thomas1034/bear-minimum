package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class AbstractBearPanicGoal extends PanicGoal {
    public AbstractBearPanicGoal(AbstractBearEntity bear, float speedup) {
        super(bear, speedup);
    }

    protected boolean shouldPanic() {
        return this.mob.getLastHurtByMob() != null && this.mob.isBaby() || this.mob.isOnFire();
    }
}
