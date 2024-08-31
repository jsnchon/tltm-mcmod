package io.github.chobl.world;

import io.github.chobl.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGeneration {

    public static void generateOres(final BiomeLoadingEvent EVENT) {
        if (EVENT.getCategory().equals(Biome.Category.THEEND)){
            generateOre(EVENT.getGeneration(), new BlockMatchRuleTest(Blocks.END_STONE), BlockInit.VEINOUS_END_STONE.getDefaultState(), 2, 10, 32, 10);
            //for overworld/nether generation, you would use a RuleTest of the overworld base or nether base. a RuleTest is basically the parameters for what blocks the ore can be generated in. Ex:
            //overworld base means that the ore can generate in stone, granite, etc. The generation uses the test to determine if a position where an ore is to be generated is a valid block. But the
            //end doesn't have any preset, since there are no end ores. Instead, use a new BlockMatchRuleTest for end stone.
        }
    }

    private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int maxPerChunk){
        settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(fillerType, state, veinSize))
                        .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeight, 0, maxHeight)).square()
                                .func_242731_b(maxPerChunk)));
    //no surface ore is an ore feature that is configured into the ore settings. prevents ore from generating on the surface exposed to air, like ancient debris
    }
}
