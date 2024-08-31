package io.github.chobl;

import io.github.chobl.client.particles.BleedingParticle;
import io.github.chobl.client.particles.RecluseCurseParticle;
import io.github.chobl.client.render.entity.RecluseRenderer;
import io.github.chobl.core.init.EntityTypeInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TheLittleThingsMod.MOD_ID, value = Dist.CLIENT)
public class ClientProxy {

    public static void clientInit(){
        RenderingRegistry.registerEntityRenderingHandler(EntityTypeInit.RECLUSE, manager -> new RecluseRenderer(manager));
    }

    public static void spawnParticle(String name, double x, double y, double z, double motX, double motY, double motZ, float scale, int minAge){
        ClientWorld world = Minecraft.getInstance().world;
        if (world==null){
            return;
        }
        net.minecraft.client.particle.Particle particle = null;
        if (name.equals("recluse_curse")){
            particle = new RecluseCurseParticle(world, x, y, z, motX, motY, motZ, scale, minAge);
        }
        else if (name.equals("bleeding")){
            particle = new BleedingParticle(world, x, y, z, motX, motY, motZ, scale, minAge);
        }
        if (particle != null) {
            Minecraft.getInstance().particles.addEffect(particle);
        }
        else{
            System.out.println("/:ERROR! Faulty particle name passed into spawnParticle");
        }
    }
}
