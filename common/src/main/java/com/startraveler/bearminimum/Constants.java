package com.startraveler.bearminimum;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "bearminimum";
    public static final String MOD_NAME = "Bear Minimum";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}