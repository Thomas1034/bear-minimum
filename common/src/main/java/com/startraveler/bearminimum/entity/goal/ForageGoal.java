package com.startraveler.bearminimum.entity.goal;

import com.startraveler.bearminimum.entity.AbstractBearEntity;
import com.startraveler.bearminimum.mixin.CropBlockMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class ForageGoal extends MoveToBlockGoal {
    public static final int TICKS_TILL_START_AGAIN = 10;
    private final AbstractBearEntity bear;
    private boolean wantsToRaid;
    private boolean canRaid;

    public ForageGoal(AbstractBearEntity bear) {
        super(bear, 0.7F, 16);
        this.bear = bear;
    }

    @Override
    public boolean canUse() {
        if (this.nextStartTick <= 0) {
            if (!(this.bear.level).getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return false;
            }

            this.canRaid = false;
            this.wantsToRaid = this.bear.wantsMoreFood();
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.canRaid && super.canContinueToUse();
    }

    public void tick() {
        super.tick();
        this.bear.getLookControl().setLookAt(
                this.blockPos.getX() + 0.5,
                this.blockPos.getY() + 1,
                this.blockPos.getZ() + 0.5,
                10.0F,
                this.bear.getMaxHeadXRot()
        );
        if (this.isReachedTarget()) {
            Level level = this.bear.level;
            BlockPos blockpos = this.blockPos.above();
            BlockState blockstate = level.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (this.canRaid && blockstate.is(this.bear.getFoodPreferences().forageTag())) {
                if (block instanceof CropBlock cropBlock) {
                    IntegerProperty property = ((CropBlockMixin) cropBlock).invokeGetAgeProperty();
                    int age = blockstate.getValue(property);
                    int minAge = property.getPossibleValues().stream().mapToInt(a -> a).min().orElse(0);
                    if (age == minAge) {
                        level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                        level.destroyBlock(blockpos, true, this.bear);
                    } else {
                        level.setBlock(blockpos, blockstate.setValue(property, minAge), Block.UPDATE_CLIENTS);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(this.bear));
                        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockpos, Block.getId(blockstate));
                    }

                } else {
                    level.destroyBlock(blockpos, true, this.bear);
                    level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockpos, Block.getId(blockstate));
                }

                this.bear.eatCropAgainTicks = AbstractBearEntity.TICKS_TILL_HUNGRY_AGAIN;
            }

            this.canRaid = false;
            this.nextStartTick = TICKS_TILL_START_AGAIN;
        }

    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(Blocks.FARMLAND) && this.wantsToRaid && !this.canRaid) {
            blockstate = level.getBlockState(pos.above());
            if (blockstate.is(this.bear.getFoodPreferences()
                    .forageTag()) && (!(blockstate.getBlock() instanceof CropBlock cropBlock) || cropBlock.isMaxAge(
                    blockstate))) {
                this.canRaid = true;
                return true;
            }
        }

        return false;
    }
}