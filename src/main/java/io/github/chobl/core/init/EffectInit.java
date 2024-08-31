package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.effects.BleedingEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectInit {
    public static final Effect BLEEDING = new BleedingEffect();

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event){
        try {
            for (Field f : EffectInit.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Effect) {
                    event.getRegistry().register((Effect) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
