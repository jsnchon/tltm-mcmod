package io.github.chobl.core.init;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.blocks.VeinousEndStone;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.function.ToIntFunction;

@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            TheLittleThingsMod.MOD_ID);

    //https://forums.minecraftforge.net/topic/72424-1143-tags-help/
    //in order to make endronium compatible with beacons, it needs the vanilla beacon_base_blocks tag. to add vanilla tags, had to create a minecraft package under data with a tags and block section, then
    //put in a json file with the vanilla tag name (beacon_base_blocks.json). forge looks for tags data that is both custom (mod id package) and vanilla (minecraft package). it then reads in the minecraft package
    //the json file with the respective tag. in the json file, replace is set to false to make sure that the rest of the blocks in the official tag.json file are not overwritten, but that the endronium is merely
    //appended to the json file. when it is, it is added to the list of blocks with the tag

    //http://jabelarminecraft.blogspot.com/p/creating-custom-entities.html

    public static final Block ENDRONIUM_BLOCK = new Block(AbstractBlock.Properties.create(Material.MISCELLANEOUS)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(4)
            .sound(SoundType.CHAIN)
            .setLightLevel((state) -> {
                return 11;
            }) //setLightLevel takes a block state and converts it into a value for the block light level. pass in a lambda that accepts any block state
            //(so the block's state itself) and just convert it so that it returns an int value
            .hardnessAndResistance(45f, 100f)
            .setRequiresTool()).setRegistryName("endronium_block");

    //since veinous end stone is a SPECIAL block with its own class, states, and events, we construct it using its own class, not just the generic block class
    public static final Block VEINOUS_END_STONE = new VeinousEndStone(AbstractBlock.Properties.create(Material.ROCK).sound(SoundType.GILDED_BLACKSTONE)
            .tickRandomly() //tell game that random ticks should apply to this block
            .setRequiresTool()
            .setLightLevel(getLightValueLit(12)) //set the light level of the block ONLY WHEN THE LIT BLOCK STATE IS TRUE, otherwise, lightValue is 0 (that's what getLightValueLitMethod does)
            .harvestTool(ToolType.PICKAXE).harvestLevel(4)
            .hardnessAndResistance(30, 50f)).setRegistryName("veinous_end_stone");

    //this ? : operator is a ternary conditional operator https://stackoverflow.com/questions/10336899/what-is-a-question-mark-and-colon-operator-used-for
    //https://en.wikipedia.org/wiki/%3F:
    private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
        return (state) -> {
            return state.get(BlockStateProperties.LIT) ? lightValue : 0; //equivalent to if the block does have a LIT state, set it equal to lightValue. if not, set it to 0
        };
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        try{
            for (Field f : BlockInit.class.getDeclaredFields()){
                Object obj = f.get(null);
                if (obj instanceof Block){
                    event.getRegistry().register((Block) obj);
                }
            }
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
