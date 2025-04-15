package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.BlackBearRenderer;
import com.startraveler.bearminimum.client.BrownBearRenderer;
import com.startraveler.bearminimum.entity.BlackBearEntity;
import com.startraveler.bearminimum.entity.BrownBearEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
                    .sized(1.4F*0.8F, 1.4F*0.8F)
                    .clientTrackingRange(10)
                    .build("black_bear")
    );
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final RegistryObject<Item> BLACK_BEAR_SPAWN_EGG = ITEMS.register(
            "black_bear_spawn_egg",
            () -> new ForgeSpawnEggItem(
                    () -> BLACK_BEAR.get(),
                    0x161619,
                    0x0,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            )
    );
    public static final RegistryObject<Item> BEAR_MEAT = ITEMS.register(
            "bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.BEAR_MEAT).tab(CreativeModeTab.TAB_FOOD))
    );
    public static final RegistryObject<EntityType<BrownBearEntity>> BROWN_BEAR = ENTITY_TYPES.register(
            "brown_bear",
            () -> EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .build("brown_bear")
    );
    public static final RegistryObject<Item> BROWN_BEAR_SPAWN_EGG = ITEMS.register(
            "brown_bear_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> BROWN_BEAR.get(),
                    0x573525, 0x382417,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)
            )
    );
    public static final RegistryObject<Item> COOKED_BEAR_MEAT = ITEMS.register(
            "cooked_bear_meat",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_BEAR_MEAT).tab(CreativeModeTab.TAB_FOOD))
    );
    private static final ResourceLocation POLAR_BEAR_LOOT_TABLE_ID = new ResourceLocation(
            "minecraft",
            "entities/polar_bear"
    );
    private static final ResourceLocation MODIFIED_POLAR_BEAR_LOOT_TABLE_ID = Constants.id("entities/polar_bear");

    public BearMinimum() {
        FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
        IEventBus modBus = context.getModEventBus();
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.


        // Use Forge to bootstrap the Common mod.
        // Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);

        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(BLACK_BEAR.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);
            SpawnPlacements.register(BROWN_BEAR.get(),
                    SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules);

        });
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventBusEvents {

        @SubscribeEvent
        public static void onLootTableLoad(LootTableLoadEvent event) {
            ResourceLocation tableName = event.getName();

            if (tableName.equals(POLAR_BEAR_LOOT_TABLE_ID)) {
                LootPool nestedTablePool = LootPool.lootPool()
                        .add(LootTableReference.lootTableReference(MODIFIED_POLAR_BEAR_LOOT_TABLE_ID))
                        .build();
                event.getTable().addPool(nestedTablePool);
            }
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEventBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(BLACK_BEAR.get(), BlackBearRenderer::new);
            EntityRenderers.register(BROWN_BEAR.get(), BrownBearRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLCommonSetupEvent event) {
            EntityRenderers.register(BLACK_BEAR.get(), BlackBearRenderer::new);
            EntityRenderers.register(BROWN_BEAR.get(), BrownBearRenderer::new);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(BearMinimum.BLACK_BEAR.get(), BlackBearEntity.createAttributes().build());
            event.put(BearMinimum.BROWN_BEAR.get(), BrownBearEntity.createAttributes().build());
        }
    }
}