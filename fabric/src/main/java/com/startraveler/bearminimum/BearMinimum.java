package com.startraveler.bearminimum;

import com.startraveler.bearminimum.entity.BlackBearEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class BearMinimum implements ModInitializer {


    public static final EntityType<BlackBearEntity> BLACK_BEAR = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Constants.id("black_bear"),
            EntityType.Builder.of(BlackBearEntity::new, MobCategory.CREATURE)
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Constants.id("black_bear")))
    );

    public static final Item BEAR_MEAT = register(
            "bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.BEAR_MEAT)
    );
    public static final Item COOKED_BEAR_MEAT = register(
            "cooked_bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT)
    );

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        // Create the item key.
        ResourceKey<Item> itemKey = Constants.key(Registries.ITEM, name);

        // Create the item instance.
        Item item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        FabricDefaultAttributeRegistry.register(BLACK_BEAR, BlackBearEntity.createAttributes());

    }
}
