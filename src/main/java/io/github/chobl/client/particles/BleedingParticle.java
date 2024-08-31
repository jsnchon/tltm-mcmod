package io.github.chobl.client.particles;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import io.github.chobl.TheLittleThingsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class BleedingParticle extends SpriteTexturedParticle {
    private static final ResourceLocation BLEEDING = new ResourceLocation(TheLittleThingsMod.MOD_ID, "textures/particles/bleeding.png");
    float bleedParticleScale;

    public BleedingParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double motX, double motY, double motZ, float scale, int minAge){
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, motX, motY, motZ);
        this.motionX *= motX;
        this.motionY *= motY;
        this.motionZ *= motZ;
        float f = (float) Math.random() * 0.4F + 0.6F;
        this.particleScale *= scale;
        this.bleedParticleScale = this.particleScale;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.maxAge+=minAge;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        Vector3d inerp = renderInfo.getProjectedView();
        float scaley = ((float) this.age + partialTicks) / (float) this.maxAge * 32.0F;
        scaley = MathHelper.clamp(scaley, 0.0F, 1.0F);
        this.particleScale = this.bleedParticleScale * scaley;

        float width = particleScale * 0.09F;
        if (age > this.getMaxAge()) {
            this.setExpired();
        }

        Vector3d Vector3d = renderInfo.getProjectedView();
        float f = (float) (MathHelper.lerp(partialTicks, this.prevPosX, this.posX) - Vector3d.getX());
        float f1 = (float) (MathHelper.lerp(partialTicks, this.prevPosY, this.posY) - Vector3d.getY());
        float f2 = (float) (MathHelper.lerp(partialTicks, this.prevPosZ, this.posZ) - Vector3d.getZ());
        Quaternion quaternion;
        if (this.particleAngle == 0.0F) {
            quaternion = renderInfo.getRotation();
        } else {
            quaternion = new Quaternion(renderInfo.getRotation());
            float f3 = MathHelper.lerp(partialTicks, this.prevParticleAngle, this.particleAngle);
            quaternion.multiply(Vector3f.ZP.rotation(f3));
        }

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getScale(partialTicks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }
        float f7 = 0;
        float f8 = 1;
        float f5 = 0;
        float f6 = 1;
        Minecraft.getInstance().getTextureManager().bindTexture(BLEEDING);
        int j = this.getBrightnessForRender(partialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        vertexbuffer.pos(avector3f[0].getX(), avector3f[0].getY(), avector3f[0].getZ()).tex(f8, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        vertexbuffer.pos(avector3f[1].getX(), avector3f[1].getY(), avector3f[1].getZ()).tex(f8, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        vertexbuffer.pos(avector3f[2].getX(), avector3f[2].getY(), avector3f[2].getZ()).tex(f7, f5).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        vertexbuffer.pos(avector3f[3].getX(), avector3f[3].getY(), avector3f[3].getZ()).tex(f7, f6).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j).endVertex();
        Tessellator.getInstance().draw();
    }

    @Override
    protected int getBrightnessForRender(float partialTick) {
        return 300;
    }

    public void onUpdate() {
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }

        this.move(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }
}
