package io.github.chobl.common.network.packets;

import io.github.chobl.ClientProxy;
import io.github.chobl.core.init.SoundInit;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RecluseCursePacket {

    public RecluseCursePacket(ByteBuf buf) {

    }

    public RecluseCursePacket(){

    }

    public void toBytes(ByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            System.out.println("/:DEBUG: Curse sound packet fired");
            ServerPlayerEntity player = ctx.get().getSender();
            World world = player.getEntityWorld();
            world.playSound(null, player.getPosition(), SoundInit.RECLUSE_CURSE, SoundCategory.HOSTILE, 1.0f, 1.0f);
        });
        ctx.get().setPacketHandled(true); //notify the server that the packet was handled after execution
    }
}
