package com.github.alexthe666.iceandfire.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ParticleSpark extends ParticleFlame {

    private static final ResourceLocation SPARK = new ResourceLocation("iceandfire:textures/particles/spark.png");
    private final double initialX;
    private final double initialZ;

    @SideOnly(Side.CLIENT)
    public ParticleSpark(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.particleMaxAge = 25;
        this.initialX = xCoordIn;
        this.initialZ = zCoordIn;
        this.posX = this.initialX + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 1.25F);
        this.posY = yCoordIn + 0.5F + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 1.25F);
        this.posZ = this.initialZ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 1.25F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setParticleTextureIndex(rand.nextInt(8));
    }

    public void setParticleTextureIndex(int particleTextureIndex) {
        this.particleTextureIndexX = particleTextureIndex % 4;
        this.particleTextureIndexY = Math.min(2, particleTextureIndex / 4);
    }

    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if (particleAge > 25) {
            this.setExpired();
        }
        particleScale = 0.125F * (this.particleMaxAge - (this.particleAge));
        float f3 = (float) (this.posX - interpPosX);
        float f4 = (float) (this.posY - interpPosY);
        float f5 = (float) (this.posZ - interpPosZ);
        float width = particleScale * 0.09F;
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & 65535;
        int k = i & 65535;
        Vec3d[] avec3d = new Vec3d[]{new Vec3d(-rotationX * width - rotationXY * width, -rotationZ * width, -rotationYZ * width - rotationXZ * width), new Vec3d(-rotationX * width + rotationXY * width, rotationZ * width, -rotationYZ * width + rotationXZ * width), new Vec3d(rotationX * width + rotationXY * width, rotationZ * width, rotationYZ * width + rotationXZ * width), new Vec3d(rotationX * width - rotationXY * width, -rotationZ * width, rotationYZ * width - rotationXZ * width)};
        GlStateManager.enableBlend();
        GlStateManager.enableNormalize();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(false);
        float f8 = (float)Math.PI / 2 + this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
        float f9 = MathHelper.cos(f8 * 0.5F);
        float f10 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.x;
        float f11 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.y;
        float f12 = MathHelper.sin(f8 * 0.5F) * (float) cameraViewDir.z;
        Vec3d vec3d = new Vec3d(f10, f11, f12);

        for (int l = 0; l < 4; ++l) {
            avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double) (f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale(2.0F * f9));
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(SPARK);
        GlStateManager.disableLighting();
        float alpha = 1;
        GL11.glPushMatrix();
        buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        buffer.pos((double)f3 + avec3d[0].x, (double)f4 + avec3d[0].y, (double)f5 + avec3d[0].z).tex(0, 1).color(1, 1, 1, alpha).lightmap(j, k).endVertex();
        buffer.pos((double)f3 + avec3d[1].x, (double)f4 + avec3d[1].y, (double)f5 + avec3d[1].z).tex(1, 1).color(1, 1, 1, alpha).lightmap(j, k).endVertex();
        buffer.pos((double)f3 + avec3d[2].x, (double)f4 + avec3d[2].y, (double)f5 + avec3d[2].z).tex(1, 0).color(1, 1, 1, alpha).lightmap(j, k).endVertex();
        buffer.pos((double)f3 + avec3d[3].x, (double)f4 + avec3d[3].y, (double)f5 + avec3d[3].z).tex(0, 0).color(1, 1, 1, alpha).lightmap(j, k).endVertex();
        Tessellator.getInstance().draw();
        GL11.glPopMatrix();
        GlStateManager.disableBlend();
    }

    public int getBrightnessForRender(float partialTick) {
        return 240;
    }

    public int getFXLayer() {
        return 3;
    }

    public void onUpdate() {
        super.onUpdate();
        float distX = (float) (this.initialX - this.posX);
        float distZ = (float) (this.initialZ - this.posZ);
        this.motionX += distX * 0.1F * rand.nextFloat();
        this.motionZ += distZ * 0.1F * rand.nextFloat();
        this.motionY += 0.05F * (rand.nextFloat() - rand.nextFloat());
        this.motionZ += 0.05F * (rand.nextFloat() - rand.nextFloat());
        this.motionX += 0.05F * (rand.nextFloat() - rand.nextFloat());
        this.particleAngle += 0.25F * (rand.nextFloat() - rand.nextFloat());
    }

    public void move(double x, double y, double z) {
        double d0 = y;
        double origX = x;
        double origZ = z;

        if (this.canCollide) {
            List<AxisAlignedBB> list = this.world.getCollisionBoxes((Entity) null, this.getBoundingBox().expand(x, y, z));

            for (AxisAlignedBB axisalignedbb : list) {
                y = axisalignedbb.calculateYOffset(this.getBoundingBox(), y);
            }

            this.setBoundingBox(this.getBoundingBox().offset(0.0D, y, 0.0D));

            for (AxisAlignedBB axisalignedbb1 : list) {
                x = axisalignedbb1.calculateXOffset(this.getBoundingBox(), x);
            }

            this.setBoundingBox(this.getBoundingBox().offset(x, 0.0D, 0.0D));

            for (AxisAlignedBB axisalignedbb2 : list) {
                z = axisalignedbb2.calculateZOffset(this.getBoundingBox(), z);
            }

            this.setBoundingBox(this.getBoundingBox().offset(0.0D, 0.0D, z));
        } else {
            this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        }

        this.resetPositionToBB();
        this.onGround = d0 != y && d0 < 0.0D;

        if (origX != x) {
            this.motionX = 0.0D;
        }

        if (origZ != z) {
            this.motionZ = 0.0D;
        }
    }
}
