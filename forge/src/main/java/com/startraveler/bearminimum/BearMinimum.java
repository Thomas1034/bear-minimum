package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.model.BlackBearModel;
import com.startraveler.bearminimum.client.model.BrownBearModel;
import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import com.startraveler.bearminimum.client.renderer.BrownBearRenderer;
import com.startraveler.bearminimum.entity.BlackBearEntity;
import com.startraveler.bearminimum.entity.BrownBearEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Constants.MOD_ID)
public class BearMinimum {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            ForgeRegistries.ENTITY_TYPES,
            Constants.MOD_ID
    );
    public static final RegistryObject<EntityType<BlackBearEntity>> BLACK_BEAR = ENTITY_TYPES.register(
            "black_bear",
            () -> EntityType.Builder.of(BlackBearEntity::new, MobCategory.CREATURE)
                    .sized(BlackBearEntity.WIDTH, BlackBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build(Constants.key(Registries.ENTITY_TYPE, "black_bear"))
    );
    public static final RegistryObject<EntityType<BrownBearEntity>> BROWN_BEAR = ENTITY_TYPES.register(
            "brown_bear",
            () -> EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
                    .sized(BrownBearEntity.WIDTH, BrownBearEntity.HEIGHT)
                    .clientTrackingRange(10)
                    .build(Constants.key(Registries.ENTITY_TYPE, "brown_bear"))
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final RegistryObject<Item> BLACK_BEAR_SPAWN_EGG = ITEMS.register(
            "black_bear_spawn_egg",
            () -> new SpawnEggItem(
                    BLACK_BEAR.get(), new Item.Properties()
                    .setId(Constants.key(Registries.ITEM, "black_bear_spawn_egg"))
            )
    );
    public static final RegistryObject<Item> BROWN_BEAR_SPAWN_EGG = ITEMS.register(
            "brown_bear_spawn_egg",
            () -> new SpawnEggItem(
                    BROWN_BEAR.get(), new Item.Properties()
                    .setId(Constants.key(Registries.ITEM, "brown_bear_spawn_egg"))
            )
    );
    public static final RegistryObject<Item> BEAR_MEAT = ITEMS.register(
            "bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.BEAR_MEAT)
                    .setId(Constants.key(Registries.ITEM, "bear_meat")))
    );
    public static final RegistryObject<Item> COOKED_BEAR_MEAT = ITEMS.register(
            "cooked_bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT)
                    .setId(Constants.key(Registries.ITEM, "cooked_bear_meat")))
    );
    private static final ResourceLocation POLAR_BEAR_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath(
            "minecraft",
            "entities/polar_bear"
    );
    private static final ResourceKey<LootTable> MODIFIED_POLAR_BEAR_LOOT_TABLE_ID = Constants.key(
            Registries.LOOT_TABLE,
            "entities/polar_bear"
    );

    public BearMinimum(FMLJavaModLoadingContext context) {
        BusGroup modBus = context.getModBusGroup();
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.


        // Use Forge to bootstrap the Common mod.
        // Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEventBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(BLACK_BEAR.get(), BlackBearRenderer::new);
            EntityRenderers.register(BROWN_BEAR.get(), BrownBearRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BlackBearModel.BODY_LAYER, () -> BlackBearModel.createBodyLayer(false));
            event.registerLayerDefinition(BlackBearModel.BODY_LAYER_BABY, () -> BlackBearModel.createBodyLayer(true));
            event.registerLayerDefinition(BrownBearModel.BODY_LAYER, () -> BrownBearModel.createBodyLayer(false));
            event.registerLayerDefinition(BrownBearModel.BODY_LAYER_BABY, () -> BrownBearModel.createBodyLayer(true));
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            event.register(
                    BROWN_BEAR.get(),
                    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE
            );
            event.register(
                    BLACK_BEAR.get(),
                    SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE
            );
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(BearMinimum.BLACK_BEAR.get(), BlackBearEntity.createAttributes().build());
            event.put(BearMinimum.BROWN_BEAR.get(), BrownBearEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void addCreative(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                event.accept(BEAR_MEAT);
                event.accept(COOKED_BEAR_MEAT);
            }
            if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.accept(BLACK_BEAR_SPAWN_EGG.get());
                event.accept(BROWN_BEAR_SPAWN_EGG.get());
            }
        }

    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventBusEvents {

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