package io.github.chobl.common.items;

import io.github.chobl.ClientProxy;
import io.github.chobl.common.SetBlockArea;
import io.github.chobl.common.entities.RecluseEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TLTMDebugStickItem extends Item {

    private static final Random RAND = new Random();

    public TLTMDebugStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        System.out.println("Debug stick used");
        BlockPos pos = new BlockPos(playerIn.getPosX(), playerIn.getPosY()-1, playerIn.getPosZ());
        //SetBlockArea.setBlockArea(pos, worldIn, 3, 4, 5, Blocks.ACACIA_LOG);
        for (int x = 0; x < 5 + RAND.nextInt(10); x++){
            double xFactor = Math.random()*2;
            double yFactor = 1 + Math.random()*2;
            double zFactor = Math.random()*2;
            double xMotFactor = 1 + Math.random()/10;
            double yMotFactor = 1 + Math.random()/10;
            double zMotFactor = 1 + Math.random()/10;
            float scaleFactor = (float) (Math.random() * 2.5F);
            int ageFactor = (int) (Math.random() * 26) + 10;
            ClientProxy.spawnParticle("recluse_curse", pos.getX()+xFactor, pos.getY()+yFactor, pos.getZ()+zFactor, xMotFactor, yMotFactor, zMotFactor, scaleFactor, ageFactor);
        }
        for (int x = 0; x < 10 + RAND.nextInt(15); x++){
            double xFactor = Math.random();
            double yFactor = 1 + Math.random()*2;
            double zFactor = Math.random();
            double xMotFactor = 1 + Math.random()*10;
            double yMotFactor = (1 + Math.random()*30)*-1;
            double zMotFactor = 1 + Math.random()*10;
            float scaleFactor = (float) (Math.random() * 1.0F);
            int ageFactor = (int) (Math.random());
            ClientProxy.spawnParticle("bleeding", pos.getX()+xFactor, pos.getY()+yFactor, pos.getZ()+zFactor, xMotFactor, yMotFactor, zMotFactor, scaleFactor, ageFactor);
        }
        //ClientProxy.spawnParticle("recluse_curse", pos.getX(), pos.getY(), pos.getZ(), 0.0, 2.0, 0.0, 5.0F, 50);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
