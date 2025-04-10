package com.startraveler.bearminimum.entity;

import com.startraveler.bearminimum.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BearFoodPreferences(TagKey<Item> foodTag, TagKey<EntityType<?>> preyTag, TagKey<Block> forageTag) {

    public static final TagKey<Item> BLACK_BEAR_FOOD = TagKey.create(
            Registries.ITEM,
            Constants.id("black_bear_food")
    );
    public static final TagKey<Item> BROWN_BEAR_FOOD = TagKey.create(
            Registries.ITEM,
            Constants.id("brown_bear_food")
    );
    public static final TagKey<EntityType<?>> BLACK_BEAR_PREY = TagKey.create(
            Registries.ENTITY_TYPE,
            Constants.id("black_bear_prey")
    );
    public static final TagKey<EntityType<?>> BROWN_BEAR_PREY = TagKey.create(
            Registries.ENTITY_TYPE,
            Constants.id("brown_bear_prey")
    );
    public static final TagKey<Block> BLACK_BEAR_FORAGE = TagKey.create(
            Registries.BLOCK,
            Constants.id("black_bear_forage")
    );
    public static final TagKey<Block> BROWN_BEAR_FORAGE = TagKey.create(
            Registries.BLOCK,
            Constants.id("brown_bear_forage")
    );
}
