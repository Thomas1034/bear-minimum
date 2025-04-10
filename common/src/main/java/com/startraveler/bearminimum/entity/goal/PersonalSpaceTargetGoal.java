package com.startraveler.bearminimum.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;

public class PersonalSpaceTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public final float attackWhenInDistance;

    public PersonalSpaceTargetGoal(Mob mob, Class<T> targetType, float attackWhenInDistance, boolean mustSee) {
        super(mob, targetType, mustSee);
        this.attackWhenInDistance = attackWhenInDistance;
    }

    public PersonalSpaceTargetGoal(Mob mob, Class<T> targetType, float attackWhenInDistance, boolean mustSee, TargetingConditions.Selector selector) {
        super(mob, targetType, mustSee, selector);
        this.attackWhenInDistance = attackWhenInDistance;
    }

    public PersonalSpaceTargetGoal(Mob mob, Class<T> targetType, float attackWhenInDistance, boolean mustSee, boolean mustReach) {
        super(mob, targetType, mustSee, mustReach);
        this.attackWhenInDistance = attackWhenInDistance;
    }

    public PersonalSpaceTargetGoal(Mob mob, Class<T> targetType, float attackWhenInDistance, int interval, boolean mustSee, boolean mustReach, @Nullable TargetingConditions.Selector selector) {
        super(mob, targetType, interval, mustSee, mustReach, selector);
        this.attackWhenInDistance = attackWhenInDistance;
    }

    @Override
    protected double getFollowDistance() {
        return super.getFollowDistance(); //this.attackWhenInDistance;
    }
}
