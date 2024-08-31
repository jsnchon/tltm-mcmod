package io.github.chobl.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class RecluseHeartItem extends Item {
    public RecluseHeartItem(Properties properties) {
        super(properties);
    }

    public int getUseDuration(ItemStack stack) {
        return 50;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }
}
