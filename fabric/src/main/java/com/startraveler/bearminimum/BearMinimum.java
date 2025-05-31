package com.startraveler.bearminimum;

import com.startraveler.bearminimum.entity.BlackBearEntity;
import com.startraveler.bearminimum.entity.BrownBearEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.Function;

public class BearMinimum implements ModInitializer {


    public static final EntityType<BlackBearEntity> BLACK_BEAR = Registry.register(
            Registry.ENTITY_TYPE,
            Constants.id("black_bear"),
            EntityType.Builder.of(BlackBearEntity::new, MobCategory.CREATURE)
                    .sized(BlackBearEntity.WIDTH, BlackBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build("black_bear")
    );
    public static final EntityType<BrownBearEntity> BROWN_BEAR = Registry.register(
            Registry.ENTITY_TYPE,
            Constants.id("brown_bear"),
            EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
                    .sized(BrownBearEntity.WIDTH, BrownBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build("brown_bear")
    );
    public static final Item BEAR_MEAT = register(
            "bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.BEAR_MEAT).tab(CreativeModeTab.TAB_FOOD)
    );
    public static final Item BROWN_BEAR_SPAWN_EGG = register(
            "brown_bear_spawn_egg",
            properties -> new SpawnEggItem(BROWN_BEAR, 0x573525, 0x382417, properties),
            new Item.Properties().tab(CreativeModeTab.TAB_MISC)
    );
    public static final Item BLACK_BEAR_SPAWN_EGG = register(
            "black_bear_spawn_egg",
            properties -> new SpawnEggItem(BLACK_BEAR, 0x161619, 0x0, properties),
            new Item.Properties().tab(CreativeModeTab.TAB_MISC)
    );
    public static final Item COOKED_BEAR_MEAT = register(
            "cooked_bear_meat",
            Item::new,
            new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT).tab(CreativeModeTab.TAB_FOOD)
    );
    private static final ResourceLocation POLAR_BEAR_LOOT_TABLE_ID = new ResourceLocation("entities/polar_bear");
    private static final ResourceLocation MODIFIED_POLAR_BEAR_LOOT_TABLE_ID = Constants.id("entities/polar_bear");

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {

        // Create the item instance.
        Item item = itemFactory.apply(settings);

        // Register the item.
        Registry.register(Registry.ITEM, Constants.id(name), item);

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

        LootTableEvents.MODIFY.register((ResourceManager resourceManager, LootTables lootManager, ResourceLocation id, LootTable.Builder tableBuilder, LootTableSource source) -> {
            if (POLAR_BEAR_LOOT_TABLE_ID.equals(id)) {
                LootPool customPool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(MODIFIED_POLAR_BEAR_LOOT_TABLE_ID))
                        .build();
                tableBuilder.pool(customPool);
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
                SpawnPlacements.Type.ON_GROUND,
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
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules
        );
    }
}