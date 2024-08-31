package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundInit {

    public static final SoundEvent RECLUSE_SPAWNS = createSound("recluse_spawns");
    public static final SoundEvent RECLUSE_CURSE = createSound("recluse_curse");

    private static SoundEvent createSound(final String soundName) { //create a new Sound resource file with the right id and name
        final ResourceLocation soundID = new ResourceLocation(TheLittleThingsMod.MOD_ID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @SubscribeEvent
    public static void registerSoundsEvent(RegistryEvent.Register<SoundEvent> event){ //actually register the sounds
        try {
            for (Field f : SoundInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        event.getRegistry().register(soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
