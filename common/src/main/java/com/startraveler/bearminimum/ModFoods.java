package com.startraveler.bearminimum;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties BEAR_MEAT = new FoodProperties.Builder().nutrition(3)
            .saturationModifier(0.3F)
            .build();
    public static final FoodProperties COOKED_BEAR_MEAT = new FoodProperties.Builder().nutrition(6)
            .saturationModifier(1.2F)
            .build();
}
