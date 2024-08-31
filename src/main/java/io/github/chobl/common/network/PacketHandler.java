package io.github.chobl.common.network;

import io.github.chobl.TheLittleThingsMod;
import io.github.chobl.common.network.packets.RecluseCursePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

//handling packets: https://mcforge.readthedocs.io/en/latest/networking/simpleimpl/#handling-packets
//really stupid mod but packet code example: https://github.com/NicosaurusRex99/farts/blob/1.16.1/src/main/java/naturix/networking/PacketPlayFart.java

public class PacketHandler {
    private static int packetId = 0;
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TheLittleThingsMod.MOD_ID, "main_packet_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int nextId(){
        return packetId++;
    } //ensuring a unique packetId for each packet sent

    public static void init() {
        INSTANCE.registerMessage(PacketHandler.nextId(), //register the packet
                RecluseCursePacket.class,
                RecluseCursePacket::toBytes,
                RecluseCursePacket::new,
                RecluseCursePacket::handle);
    }
}
