package io.github.chobl.common.blocks;

import io.github.chobl.ClientProxy;
import io.github.chobl.client.particles.RecluseCurseParticle;
import io.github.chobl.common.Countdown;
import io.github.chobl.common.entities.RecluseEntity;
import io.github.chobl.core.init.EntityTypeInit;
import io.github.chobl.core.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class VeinousEndStone extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT; //using the redstone torch's boolean property of either being on or not
    private static final Random RAND = new Random();
    private int recluseRoll = -1;
    //protected static final ResourceLocation[] END_BIOMES = {Biomes.THE_END, Biomes.END_BARRENS, Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS};
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT); //creating a new StateContainer (contains block states) and adding the LIT state (registering states)
    }

    public VeinousEndStone(Properties properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(LIT, Boolean.FALSE)); //when constructing the block, set the default state of this block to LIT to false)
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) { //this event fires twice
        super.onBlockHarvested(worldIn, pos, state, player);
        if (worldIn.isRemote){
            recluseRoll = RAND.nextInt(3);
        }
        //couldn't find how to directly access the End class, if it exists. a workaround is that the end is the only dimension that has a dragon fight
        if (recluseRoll > 0  && worldIn.getDimensionType().doesHasDragonFight() == true){ //recluse spawn event only occurs in the end, to make it not so much of a hassle to use veinous endstone elsewhere
            spawnRecluse(worldIn, pos, player);
        }
    }

    public void spawnRecluse(World worldIn, BlockPos pos, PlayerEntity player){
        if (player.isCreative()){ //do not spawn recluses if a player is in creative
            return;
        }
        if (RecluseEntity.recountAllRecluse(worldIn) >= 10){  //only spawn a new recluse if there isn't ten or more near the player
            return;
        }
        if (!worldIn.isRemote){ //only summons the item in on the server side to prevent ghost items
            ItemEntity enderPearl = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(),
                    new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:ender_pearl")))); //create an ItemStack of an ender pearl, then construct that into an ItemEntity
            worldIn.addEntity(enderPearl); //add the entity to the world. note that the entity coords are already constructed into the ItemEntity, it just needs to be spawned
            RecluseEntity recluseEntity = EntityTypeInit.RECLUSE.create(worldIn);
            //iterate through nearby chunks, starting from y=10 to y=90 or so, and obtain a suitable area for the recluse to spawn
            recluseEntity.enablePersistence(); //enable persistence for any recluses spawned in through the summoning event
            recluseEntity.addToTargetList(player);
            worldIn.addEntity(recluseEntity);
            recluseEntity.allSeeingEyeAttack(player, worldIn);
            //recluseEntity.allSeeingEyeAttack(player, worldIn); //trigger the recluse's all seeing eye attack
//            BlockPos spawnPos = recluseEntity.allSeeingEyeAttack(player, worldIn);
//            recluseEntity.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0F, 0F);
        }
        for (int x = 0; x < 5 + RAND.nextInt(10); x++){
            double xFactor = Math.random()*2;
            double yFactor = Math.random()*2;
            double zFactor = Math.random()*2;
            double xMotFactor = 1 + Math.random()*3;
            double yMotFactor = 1 + Math.random()*3;
            double zMotFactor = 1 + Math.random()*3;
            float scaleFactor = (float) (Math.random() * 1.0F);
            int ageFactor = (int) (Math.random() * 26) + 10;
            ClientProxy.spawnParticle("recluse_curse", pos.getX()+xFactor, pos.getY()+yFactor, pos.getZ()+zFactor, xMotFactor, yMotFactor, zMotFactor, scaleFactor, ageFactor);
        }
        worldIn.playSound(null, pos, SoundInit.RECLUSE_SPAWNS, SoundCategory.HOSTILE, 1.0F, 1.0F);
        //sounds played on the server side play to everyone EXCEPT THE PLAYER WHO TRIGGERED THE EVENT, so we can play the sound twice, once on client and once on server
    }

    @SuppressWarnings("deprecation") //documentation says all inherited methods are ok to implement as an override
    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) { //when the block is clicked (being mined), call activation method
        activate(state, worldIn, pos);
        super.onBlockClicked(state, worldIn, pos, player);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) { //when the block is being walked on, call activation method
        activate(worldIn.getBlockState(pos), worldIn, pos);
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    private static void activate(BlockState state, World world, BlockPos pos) { //if the block is not lit when this method is called, set the block state's LIT value to true
        if (!state.get(LIT)) {
            world.setBlockState(pos, state.with(LIT, Boolean.TRUE), 3);
        }
    }

    public boolean ticksRandomly(BlockState state) { //tells the game to randomly tick the block and retrieve the state
        return state.get(LIT);
    }

    /**
     * Performs a random tick on a block.
     */

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) { //when a random tick is performed on a block
        if (state.get(LIT)) {
            worldIn.setBlockState(pos, state.with(LIT, Boolean.FALSE), 3); //if the LIT state is true, change it to false
        }
    }
}
