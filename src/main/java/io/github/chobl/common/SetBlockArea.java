package io.github.chobl.common;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SetBlockArea {
    public static BlockPos setBlockArea(BlockPos startBlock, World world, int width, int length, int height, Block setTo){
        for (int y = 0; y < height; y++){ //make the program set one layer at a time
            BlockPos incrementStartY = new BlockPos(startBlock.getX(), startBlock.getY()+y, startBlock.getZ());
            //without this incrementStartY, starting block on y layers above the first would not get set
            attemptReplace(incrementStartY, setTo, world); //attempt to set starting block to whatever needed
            BlockPos incrementStartX;
            for (int x = 1; x < length; x++){ //without this loop, start block would not have its x blocks set
                incrementStartX = new BlockPos(startBlock.getX()+x, startBlock.getY()+y, startBlock.getZ());
                attemptReplace(incrementStartX, setTo, world);
            }
            BlockPos incrementZ;
            for (int z = 1; z < width; z++){
                incrementZ = new BlockPos(startBlock.getX(), startBlock.getY()+y, startBlock.getZ()+z);
                attemptReplace(incrementZ, setTo, world);
                BlockPos incrementX;
                for (int x = 1; x < length; x++){ //each time we set one block in z direction, set length blocks in x direction
                    incrementX = new BlockPos(startBlock.getX()+x, startBlock.getY()+y, incrementZ.getZ());
                    attemptReplace(incrementX, setTo, world);
                }
            }
        }
        double centerX = length/2.0; //find how many blocks to get to the center on the x axis
        double centerZ = width/2.0; //find how many blocks to get to the center on the z axis
        System.out.println(new BlockPos(startBlock.getX()+centerX, startBlock.getY(), startBlock.getZ()+centerZ));
        return new BlockPos(startBlock.getX()+centerX, startBlock.getY(), startBlock.getZ()+centerZ);
        //returns block at the center of the bottom layer
    }

    public static void attemptReplace(BlockPos pos, Block setTo, World world){
        if (world.getBlockState(pos).getBlock().getExplosionResistance() < 100) { //will not remove blocks like water, lava, obsidian, bedrock, etc
            world.setBlockState(pos, setTo.getDefaultState());
        }
    }
}
