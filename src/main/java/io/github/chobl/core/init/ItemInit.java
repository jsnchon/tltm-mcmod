package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.items.RecluseHeartItem;
import io.github.chobl.common.items.TLTMDebugStickItem;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

//https://forums.minecraftforge.net/topic/59867-110-item-texture-questions/

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemInit {
    //DeferredRegister does nothing by itself, still needs its items registered and held

    public static final Item ADVANCED_BOLT = buildDefaultItem("advanced_bolt", ItemGroup.COMBAT);
    //item groups are creative tabs
    //create a new item and pass it in with registered name
    //now we need to register the listener for the registry event in the main class so the program automatically registers all listed items

    //https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/resource-packs/resource-pack-discussion/1256350-animation-in-resource-packs-a-minecraft-1-6

    public static final Item PURE_ENDRONIUM_NUGGET = buildDefaultItem("pure_endronium_nugget", ItemGroup.MISC);
    public static final Item IMPURE_ENDRONIUM_NUGGET = new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(16)).setRegistryName("impure_endronium_nugget");
    public static final Item ENDRONIUM_INGOT = new Item(new Item.Properties().group(ItemGroup.MISC).isImmuneToFire()).setRegistryName("endronium_ingot");
    public static final Item ENDRONIUM_APPLE = new Item(new Item.Properties().group(ItemGroup.FOOD).isImmuneToFire()
            .food(new Food.Builder().effect(() -> new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F)
            .effect(() -> new EffectInstance(Effects.GLOWING, 2400, 2), 1.0F)
            .effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 4), 1.0F)
            .effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 6000), 1.0F)
            .effect(() -> new EffectInstance(Effects.RESISTANCE, 6000), 1.0F)
            .effect(() -> new EffectInstance(Effects.STRENGTH, 200, 1), 1.0F)
            .hunger(6).saturation(1.8f).meat()
            .setAlwaysEdible().build())).setRegistryName("endronium_apple");
    public static final Item RECLUSE_EYE = new Item(new Item.Properties().group(ItemGroup.FOOD).isImmuneToFire().maxStackSize(16)
            .food(new Food.Builder().effect(() -> new EffectInstance(Effects.HASTE, 4800, 2), 1.0F)
                    .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 4800, 0), 1.0F)
                    .effect(() -> new EffectInstance(Effects.POISON, 4800, 9), 1.0F).hunger(2).saturation(0.2F)
                    .setAlwaysEdible().fastToEat().build())).setRegistryName("recluse_eye");
    public static final Item RECLUSE_HEART = new RecluseHeartItem(new Item.Properties().group(ItemGroup.FOOD).isImmuneToFire().maxStackSize(16)
            .food(new Food.Builder().effect(() -> new EffectInstance(Effects.STRENGTH, 4800, 1), 1.0F)
                    .effect(() -> new EffectInstance(Effects.SPEED, 4800, 1), 1.0F)
                    .effect(() -> new EffectInstance(Effects.POISON, 4800, 9), 1.0F)
                    .setAlwaysEdible().build())).setRegistryName("recluse_heart");
    public static final Item RECLUSE_SOUL = new Item(new Item.Properties().group(ItemGroup.FOOD).isImmuneToFire().maxStackSize(16)
            .food(new Food.Builder().effect(() -> new EffectInstance(Effects.HASTE, 6000, 4), 1.0F)
                    .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 6000, 0), 1.0F)
                    .effect(() -> new EffectInstance(Effects.STRENGTH, 6000, 2), 1.0F)
                    .effect(() -> new EffectInstance(Effects.SPEED, 6000, 2), 1.0F)
                    .effect(() -> new EffectInstance(Effects.POISON, 6000, 9), 1.0F)
                    .setAlwaysEdible().build())).setRegistryName("recluse_soul");
    public static final Item TLTM_DEBUG_STICK = new TLTMDebugStickItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName("tltm_debug_stick");
    public static final Item RECLUSE_SPAWN_EGG = new SpawnEggItem(EntityTypeInit.RECLUSE, 0X693A2C, 0X976144, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("tltm_jdsmel:recluse_spawn_egg");

    //Spawn Egg Items (COLORS ARE HEXADECIMAL)

    //public static final Item RECLUSE_SPAWN_EGG = ITEMS.register("recluse_spawn_egg", () -> new SpawnEggItem(RECLUSE, 4996656, 986895, (new Item.Properties()).group(ItemGroup.MISC)));

    //Block Items (allow us to mine and place blocks)
    public static final Item ENDRONIUM_BLOCK = new BlockItem(BlockInit.ENDRONIUM_BLOCK, new Item.Properties().isImmuneToFire().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName("endronium_block");
    //accepts the block that should drop this block item
    public static final Item VEINOUS_END_STONE = buildDefaultBlockItem("veinous_end_stone", ItemGroup.BUILDING_BLOCKS, BlockInit.VEINOUS_END_STONE);

    //when this method is called, an item is automatically initialized and its group and itemName (for the registry) are also initialized
    private static Item buildDefaultItem(String itemName, ItemGroup group){
        return new Item(new Item.Properties().group(group)).setRegistryName(itemName);
    }

    private static Item buildDefaultBlockItem(String itemName, ItemGroup group, Block block){
        return new BlockItem(block, new Item.Properties().group(group)).setRegistryName(itemName);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        try {
            for (Field f : ItemInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    event.getRegistry().register((Item) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
}
