package io.github.chobl;

import io.github.chobl.common.listeners.EventHandler;
import io.github.chobl.common.network.PacketHandler;
import io.github.chobl.core.init.BlockInit;
import io.github.chobl.core.init.EntityTypeInit;
import io.github.chobl.core.init.ItemInit;
import io.github.chobl.core.init.SoundInit;
import io.github.chobl.world.OreGeneration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//ctrl+n to search all files or in classes
//ctrl+f12 to look at inherited methods for a class
//ctrl+o to implement and override inherited methods

//loop the registration of objects (IMPLEMENT THIS TO CLEAN UP CODE): https://pastebin.com/AXTLGqmq

//TEAM PNEUMATIC MOD CODE FOR REFERENCE: https://github.com/TeamPneumatic/pnc-repressurized/blob/25eb5d5813ad531f47e75ae2546278cc21fa93cc/src/main/java/me/desht/pneumaticcraft/common/core/ModEntities.java#L67
//TIP: If you suspect that an issue involves a file path being incorrect, look in the log for any FileNotFound exceptions

//vanilla sounds.json file in readable format: https://pastebin.com/gaPhj0tp

//lang file: https://sites.google.com/site/unminecrafttranslations/en_us

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TheLittleThingsMod.MOD_ID)
public class TheLittleThingsMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "tltm_jdsmel";

    public TheLittleThingsMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup); //event bus listens for mod setup

        BlockInit.BLOCKS.register(bus);

        bus.addListener(this::setupClient); //when bus detects the client is being set up, will call set up method which will call ClientHandler to run its setup, registering entities
        MinecraftForge.EVENT_BUS.register(EntityTypeInit.class); //register the two classes into the Event Bus so their subscribe events are fired and will register
        MinecraftForge.EVENT_BUS.register(ItemInit.class);
        MinecraftForge.EVENT_BUS.register(BlockInit.class);
        MinecraftForge.EVENT_BUS.register(SoundInit.class);
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        //note that the @Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD) annotation automatically
        //tells forge to register the class in the event bus


        //colon takes a class and directly retrieves and passes in the requested method. like a lambda function
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);
        //MinecraftForge.EVENT_BUS.register(EventHandler.class); //register the event listeners/handlers
        // Register the setup method for mod-loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("deprecation")
    private void setup(final FMLCommonSetupEvent event)
    {
        // some pre-init code
        PacketHandler.init(); //initialize the creation of a packet handler so packets can actually be sent
        //handler MUST be registered during setup
    }

    private void setupClient(FMLClientSetupEvent event){
        ClientProxy.clientInit();
    }
}
