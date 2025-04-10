package com.startraveler.bearminimum.entity;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BearFoodPreferences(TagKey<Item> foodTag, TagKey<EntityType<?>> preyTag, TagKey<Block> forageTag) {
}
