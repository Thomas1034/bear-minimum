package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import net.minecraft.world.entity.player.Player;

public class AbstractBearAttackPlayersGoal extends NearestAttackableTargetGoal<@org.jetbrains.annotations.NotNull Player> {
    private final AbstractBearEntity bear;

    public AbstractBearAttackPlayersGoal(AbstractBearEntity bear) {
        super(bear, Player.class, 20, true, true, null);
        this.bear = bear;
    }

    public boolean canUse() {
        if (!this.bear.isBaby()) {
            if (super.canUse()) {
                for (PolarBear polarbear : this.bear.level()
                        .getEntitiesOfClass(PolarBear.class, this.bear.getBoundingBox().inflate(8.0F, 4.0F, 8.0F))) {
                    if (polarbear.isBaby()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected double getFollowDistance() {
        return super.getFollowDistance() * 0.5F;
    }
}
