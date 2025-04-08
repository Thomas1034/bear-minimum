package com.startraveler.bearminimum;

import com.startraveler.bearminimum.client.renderer.BlackBearRenderer;
import com.startraveler.bearminimum.entity.BlackBearEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
                    .sized(1.4F, 1.4F)
                    .clientTrackingRange(10)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, Constants.id("black_bear")))
    );


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

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

    public BearMinimum(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);


    }

    // Add the example block item to the building blocks tab
    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(BEAR_MEAT);
            event.accept(COOKED_BEAR_MEAT);
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEventBusEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(BLACK_BEAR.get(), BlackBearRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(BearMinimum.BLACK_BEAR.get(), BlackBearEntity.createAttributes().build());
        }
    }
}