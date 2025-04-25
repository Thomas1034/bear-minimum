package com.startraveler.bearminimum.entity;

import com.startraveler.bearminimum.Constants;
import com.startraveler.bearminimum.entity.goal.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.InstrumentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;


public class BrownBearEntity extends AbstractBearEntity {

    public static final TagKey<Biome> HAS_BROWN_BEAR_SPAWNS = TagKey.create(
            Registries.BIOME,
            Constants.id("has_brown_bear_spawns")
    );

    public static final float PERSONAL_SPACE_DISTANCE = 4.0f;
    // Set up proper tags!
    public static final BearFoodPreferences BROWN_BEAR_FOODS = new BearFoodPreferences(
            BearFoodPreferences.BROWN_BEAR_FOOD,
            BearFoodPreferences.BROWN_BEAR_PREY,
            BearFoodPreferences.BROWN_BEAR_FORAGE
    );

    public BrownBearEntity(EntityType<? extends BrownBearEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 50.0F)
                .add(Attributes.FOLLOW_RANGE, 30.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 6.0F)
                .add(Attributes.ARMOR, 4.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25F)
                .add(Attributes.SCALE, 1.0);
    }

    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) {
        return (AgeableMob) this.getType().create(level, EntitySpawnReason.BREEDING);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbstractBearMeleeAttackGoal(this));
        this.goalSelector.addGoal(
                1, new PanicGoal(
                        this,
                        SCARED_BOOST,
                        (bear) -> bear.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES
                )
        );
        this.goalSelector.addGoal(
                2, new AvoidEntityGoal<>(
                        this,
                        Player.class,
                        (entity) -> entity.isUsingItem() && entity.getUseItem().getItem() instanceof InstrumentItem,
                        PERSONAL_SPACE_DISTANCE * 3,
                        SCARED_BOOST,
                        SCARED_BOOST,
                        entity -> true
                )
        );
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
        this.goalSelector.addGoal(
                3,
                new TemptGoal(this, 0.5F, (stack) -> stack.is(this.getFoodPreferences().foodTag()), true)
        );
        this.goalSelector.addGoal(
                8, new AvoidEntityGoal<>(
                        this,
                        Player.class,
                        (entity) -> !this.isAngry(),
                        PERSONAL_SPACE_DISTANCE * 2,
                        SCARED_BOOST,
                        SCARED_BOOST,
                        EntitySelector.NO_CREATIVE_OR_SPECTATOR::test
                )
        );

        this.goalSelector.addGoal(
                8, new AvoidEntityGoal<>(
                        this,
                        Villager.class,
                        (entity) -> !this.isAngry(),
                        PERSONAL_SPACE_DISTANCE * 2,
                        SCARED_BOOST,
                        SCARED_BOOST,
                        EntitySelector.NO_CREATIVE_OR_SPECTATOR::test
                )
        );
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new ForageGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, PERSONAL_SPACE_DISTANCE * 3));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new AbstractBearHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new AbstractBearAttackPlayersGoal(this));
        this.targetSelector.addGoal(
                3,
                new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt)
        );

        this.targetSelector.addGoal(
                4, new NearestAttackableTargetGoal<>(
                        this,
                        Mob.class,
                        10,
                        true,
                        true,
                        (entity, level) -> this.wantsMoreFood() && entity.getType().is(this.getFoodPreferences().preyTag())
                )
        );
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
        this.targetSelector.addGoal(
                7,
                new PersonalSpaceTargetGoal<>(
                        this,
                        Player.class,
                        PERSONAL_SPACE_DISTANCE,
                        10,
                        true,
                        true,
                        (entity, level) -> true
                )
        );
    }

    @Override
    public BearFoodPreferences getFoodPreferences() {
        return BROWN_BEAR_FOODS;
    }
}
