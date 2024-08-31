package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.loot.conditions.DamageSourceProperties;
import net.minecraft.potion.Effect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//https://forums.minecraftforge.net/topic/10254-adding-custom-potion-effects-and-custom-damage-sources/

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DamageSourceInit {
    public static final DamageSource BLEEDING = (new DamageSource("bleeding")).setDamageBypassesArmor().setDamageIsAbsolute();
}
