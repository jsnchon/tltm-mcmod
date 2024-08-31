package io.github.chobl.common.listeners;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.entities.RecluseEntity;
import io.github.chobl.core.init.EffectInit;
import io.github.chobl.core.init.SoundInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

import static io.github.chobl.core.init.ItemInit.VEINOUS_END_STONE;

//forge events:
//file:///C:/Users/jjcsu/Downloads/forgeevents.html

//creating event handlers:
//https://mcforge.readthedocs.io/en/latest/events/intro/#:~:text=To%20register%20this%20event%20handler,bus%20you%20should%20use%20FMLJavaModLoadingContext.

//referencing item objects:
//https://mcforge.readthedocs.io/en/latest/concepts/registries/#referencing-registered-objects

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, value = Dist.CLIENT)
public class EventHandler{

    //playing sounds: https://forums.minecraftforge.net/topic/14947-worldplaysound-how-to-call-correctly/
    //https://forums.minecraftforge.net/topic/23072-1710-add-custom-sounds/
    //https://mcforge.readthedocs.io/en/latest/effects/sounds/
    //https://forums.minecraftforge.net/topic/59347-solved1112-play-default-minecraft-sound/
    //https://cimapminecraft.com/tutorials/minecraft-playsound-list

//    @SubscribeEvent
//    public static void onPlayerUpdate(TickEvent.PlayerTickEvent event){
//        if (!event.player.isPotionActive(EffectInit.BLEEDING)){
//            return;
//        }
//    }
}
