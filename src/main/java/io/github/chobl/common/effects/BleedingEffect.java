package io.github.chobl.common.effects;

import io.github.chobl.ClientProxy;
import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.Countdown;
import io.github.chobl.core.init.DamageSourceInit;
import io.github.chobl.core.init.EffectInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BleedingEffect extends Effect {
    private final Random RAND = new Random();
    private int tickDelay;
    private Countdown countdown;

    public BleedingEffect() {
        //color help: https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1431254-foliage-and-spawn-egg-color-codes
        super(EffectType.HARMFUL, 0X701606);
        System.out.println("/:DEBUG BleedingEffect constructor called");
        this.setRegistryName(TheLittleThingsMod.MOD_ID, "bleeding");
        this.addAttributesModifier(Attributes.MAX_HEALTH, "0b0f2680-68da-11eb-9439-0242ac130002", -4.0F, AttributeModifier.Operation.ADDITION);
        this.tickDelay = 0;
        tickDelay = RAND.nextInt((int) ((100 * (0.2))+0.5)); //when effect is first initialized, it needs a tickDelay and countdown instance created to start the first
        tickDelay+=85; //initialization of the cycle's first iteration
        countdown = new Countdown(tickDelay);
        System.out.println("/:DEBUG tickDelay" + tickDelay);
        MinecraftForge.EVENT_BUS.register(this);
        //IMPORTANT NOTE: since we are using non-static event listeners, we have to pass in each INSTANCE of the class to register their
        //listeners. so automatically do that in constructor
        //UUID should be randomly made/unique to each effect
        //https://www.uuidgenerator.net/version1
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        //creating damage sources: https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1436492-how-to-make-a-new-damage-source
        //amplifier is 0 based, and since some of this math assumes that 1 is the lowest, 1 must be added in certain cases
        entity.attackEntityFrom(DamageSourceInit.BLEEDING, 4F);
        System.out.println("/:DEBUG damaging entity by " + 4F);
        //after isReady is true, the damage is applied and a new tickDelay is created for the countdown to look at and track
        BlockPos pos = entity.getPosition();
        for (int x = 0; x < 15 + RAND.nextInt(15); x++){
            double xFactor = Math.random()*1.5;
            double yFactor = 1 + Math.random()*2;
            double zFactor = Math.random()*1.5;
            double xMotFactor = 1 + Math.random()*10;
            double yMotFactor = (1 + Math.random()*30)*-1;
            double zMotFactor = 1 + Math.random()*10;
            float scaleFactor = (float) (Math.random() * 1.2);
            int ageFactor = (int) (Math.random()*1.25);
            ClientProxy.spawnParticle("bleeding", pos.getX()-xFactor, pos.getY()+yFactor, pos.getZ()+zFactor, xMotFactor, yMotFactor, zMotFactor, scaleFactor, ageFactor);
        }
        if (amplifier >= 6){ //amplifiers 6 or above would cause a negative in the RAND generator, which would throw an error
            tickDelay = 30 + RAND.nextInt(50); //generates delay of 30 - 79 ticks (stupid fast)
        }
        else{
            tickDelay = RAND.nextInt((int) ((100 * ((1 - amplifier/5)))+0.5));
            tickDelay+=((-25*amplifier)+110);
        }
        System.out.println("/:DEBUG tickDelay" + tickDelay);
        countdown = new Countdown(tickDelay); //create a new countdown to start with and increment
        //create blood particle
    }

    @Override
    public boolean isReady(int duration, int amplifier){
        //isReady is called every tick, so every tick we check if the countdown is met with the previously initialized tick delay
        return countdown.countdownIncrement(); //increment the countdown instance. will keep returning false until countdown is met
    }
    //change heart icon to one with cracks and a band aid on it

    @Override
    public String getName(){
        return "tltm_jdsmel.potion.bleeding";
    }

    //bleed is similar to WITHER. RANDOMLY does damage to player that slowly GETS WORSE. maybe changes heart icons to broken hearts? while player is
    //affected, a blood trail (red droplet particles) is left behind where they walk (randomly summon particles at player pos during bleed update?
    //https://forums.minecraftforge.net/topic/64365-change-hearts-based-on-potion-effect/
}
