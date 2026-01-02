package com.startraveler.bearminimum;

import com.startraveler.bearminimum.entity.BlackBearEntity;
import com.startraveler.bearminimum.entity.BrownBearEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.Function;

public class BearMinimum implements ModInitializer {


    public static final EntityType<BlackBearEntity> BLACK_BEAR = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Constants.id("black_bear"),
            EntityType.Builder.of(BlackBearEntity::new, MobCategory.CREATURE)
                    .sized(BlackBearEntity.WIDTH, BlackBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Constants.id("black_bear")))
    );
    public static final EntityType<BrownBearEntity> BROWN_BEAR = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Constants.id("brown_bear"),
            EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
                    .sized(BrownBearEntity.WIDTH, BrownBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Constants.id("brown_bear")))
    );
    public static final Item BEAR_MEAT = register(
            "bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.BEAR_MEAT)
    );
    public static final Item BROWN_BEAR_SPAWN_EGG = register(
            "brown_bear_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(BROWN_BEAR)
    );
    public static final Item BLACK_BEAR_SPAWN_EGG = register(
            "black_bear_spawn_egg",
            SpawnEggItem::new,
            new Item.Properties().spawnEgg(BLACK_BEAR)
    );
    public static final Item COOKED_BEAR_MEAT = register(
            "cooked_bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT)
    );
    private static final ResourceKey<LootTable> POLAR_BEAR_LOOT_TABLE_ID = ResourceKey.create(
            Registries.LOOT_TABLE,
            Identifier.withDefaultNamespace("entities/polar_bear")
    );
    private static final ResourceKey<LootTable> MODIFIED_POLAR_BEAR_LOOT_TABLE_ID = Constants.key(
            Registries.LOOT_TABLE,
            "entities/polar_bear"
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
        // Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();


        FabricDefaultAttributeRegistry.register(BLACK_BEAR, BlackBearEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(BROWN_BEAR, BrownBearEntity.createAttributes());

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(BEAR_MEAT);
            entries.accept(COOKED_BEAR_MEAT);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(BLACK_BEAR_SPAWN_EGG);
            entries.accept(BROWN_BEAR_SPAWN_EGG);
        });

        LootTableEvents.MODIFY.register((ResourceKey<LootTable> id, LootTable.Builder builder, LootTableSource source, HolderLookup.Provider holderLookupProvider) -> {
            if (POLAR_BEAR_LOOT_TABLE_ID.equals(id)) {
                LootPool customPool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(NestedLootTable.lootTableReference(MODIFIED_POLAR_BEAR_LOOT_TABLE_ID))
                        .build();
                builder.pool(customPool);
            }
        });

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BlackBearEntity.HAS_BLACK_BEAR_SPAWNS),
                MobCategory.CREATURE,
                BLACK_BEAR,
                10,
                1,
                4
        );

        SpawnPlacements.register(
                BLACK_BEAR,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BrownBearEntity.HAS_BROWN_BEAR_SPAWNS),
                MobCategory.CREATURE,
                BROWN_BEAR,
                10,
                1,
                4
        );
        SpawnPlacements.register(
                BROWN_BEAR,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules
        );
    }
}
