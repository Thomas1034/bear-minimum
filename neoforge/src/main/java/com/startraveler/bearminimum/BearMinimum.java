package com.startraveler.bearminimum;


import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import com.startraveler.bearminimum.client.renderer.BrownBearRenderer;
import com.startraveler.bearminimum.entity.BlackBearEntity;
import com.startraveler.bearminimum.entity.BrownBearEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class BearMinimum {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE,
            Constants.MOD_ID
    );

    public static final DeferredHolder<EntityType<?>, EntityType<BlackBearEntity>> BLACK_BEAR = ENTITY_TYPES.register(
            "black_bear",
            () -> EntityType.Builder.of(BlackBearEntity::new, MobCategory.CREATURE)
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .build("black_bear")
    );
    public static final DeferredHolder<EntityType<?>, EntityType<BrownBearEntity>> BROWN_BEAR = ENTITY_TYPES.register(
            "brown_bear",
            () -> EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .build("brown_bear")
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            BuiltInRegistries.ITEM,
            Constants.MOD_ID
    );
    public static final DeferredHolder<Item, SpawnEggItem> BLACK_BEAR_SPAWN_EGG = ITEMS.register(
            "black_bear_spawn_egg",
            () -> new SpawnEggItem(
                    BLACK_BEAR.get(),
                    0x161619,
                    0x0, new Item.Properties()
            )
    );
    public static final DeferredHolder<Item, SpawnEggItem> BROWN_BEAR_SPAWN_EGG = ITEMS.register(
            "brown_bear_spawn_egg",
            () -> new SpawnEggItem(
                    BROWN_BEAR.get(), 0x573525, 0x382417, new Item.Properties()
            )
    );
    public static final DeferredHolder<Item, Item> BEAR_MEAT = ITEMS.register(
            "bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.BEAR_MEAT)
            )
    );
    public static final DeferredHolder<Item, Item> COOKED_BEAR_MEAT = ITEMS.register(
            "cooked_bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT)
            )
    );

    private static final ResourceLocation POLAR_BEAR_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(
            "minecraft",
            "entities/polar_bear"
    );
    private static final ResourceKey<LootTable> MODIFIED_POLAR_BEAR_LOOT_TABLE_ID = Constants.key(
            Registries.LOOT_TABLE,
            "entities/polar_bear"
    );

    public BearMinimum(ModContainer modContainer) {
        IEventBus modBus = modContainer.getEventBus();
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.


        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEventBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(BLACK_BEAR.get(), BlackBearRenderer::new);
            EntityRenderers.register(BROWN_BEAR.get(), BrownBearRenderer::new);
        }
    }

    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(BearMinimum.BLACK_BEAR.get(), BlackBearEntity.createAttributes().build());
            event.put(BearMinimum.BROWN_BEAR.get(), BrownBearEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(
                    BLACK_BEAR.get(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE
            );
            event.register(
                    BROWN_BEAR.get(), SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE
            );
        }

        // Add the example block item to the building blocks tab
        @SubscribeEvent
        public static void addCreative(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                event.accept(BEAR_MEAT.get());
                event.accept(COOKED_BEAR_MEAT.get());
            }
            if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.accept(BLACK_BEAR_SPAWN_EGG.get());
                event.accept(BROWN_BEAR_SPAWN_EGG.get());
            }
        }
    }


    @EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class NeoForgeEventBusEvents {
        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            ResourceLocation tableName = event.getName();

            if (tableName.equals(POLAR_BEAR_LOOT_TABLE_ID)) {
                LootPool customPool = LootPool.lootPool()
                        .name(Constants.MOD_ID + "_add_bear_drops")
                        .setRolls(ConstantValue.exactly(1))
                        .add(NestedLootTable.lootTableReference(MODIFIED_POLAR_BEAR_LOOT_TABLE_ID))
                        .build();
                event.getTable().addPool(customPool);


            }
        }
    }
}