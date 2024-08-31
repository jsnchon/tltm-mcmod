package io.github.chobl.common;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.github.chobl.TheLittleThingsMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.concurrent.*;

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Countdown {
    private int countdownCount;
    private int countdownLength;
    private boolean countdownComplete;

    public Countdown(int countdownLength){
        this.countdownLength = countdownLength;
        this.countdownCount = 0;
        this.countdownComplete = false;
        //IMPORTANT NOTE: since we are using non-static event listeners, we have to pass in each INSTANCE of the class to register thier
        //listeners. so automatically do that in constructor
    }

//    public void initializeCountdown(){
//        //https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds
//        //use java scheduled executor service or timer class?
//        //want to increment the count every second then check if it equals the length, returning true once it finishes
//        //or use while loop with thread sleep
//        Countdown count = this;
//        Callable<Boolean> callable = new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                if (count.countdownIncrement()) {
//                    System.out.println("countdown done.");
//                    return true;
//                }
//                else{
//                    System.out.println("Hello world");
//                    return false;
//                }
//            }
//        };
//        Runnable countRunnable = new Runnable() {
//            public void run() {
//                if (count.countdownIncrement()) {
//                    System.out.println("countdown done.");
//                }
//                System.out.println("Hello world");
//            }
//        };
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
//
//                executor.scheduleAtFixedRate((Runnable) countRunnable, 0, 1, TimeUnit.SECONDS);
//    }

    //countdown method for incrementing up whenever it is called. useful if a cooldown check is occurring every tick
    public boolean countdownIncrement(){
        this.countdownCount++;
        return this.countdownCount == this.countdownLength;
    }
}
