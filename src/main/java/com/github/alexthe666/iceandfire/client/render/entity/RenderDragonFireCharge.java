package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderDragonFireCharge extends Render<Entity> {

	private final Type type;

	public enum Type {
		FIRE, ICE, LIGHTNING
	}

	public RenderDragonFireCharge(RenderManager renderManager, Type type) {
		super(renderManager);
		this.type = type;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
		BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		this.bindEntityTexture(entity);
		GlStateManager.rotate(entity.ticksExisted * 7, 1.0F, 1.0F, 1.0F);
		GlStateManager.translate(-0.5F, 0F, 0.5F);
		blockrendererdispatcher.renderBlockBrightness(getBlock().getDefaultState(), entity.getBrightness());
		GlStateManager.translate(-1.0F, 0.0F, 1.0F);
		GL11.glPopMatrix();
	}

	private Block getBlock() {
		switch(this.type) {
			case ICE: return IafBlockRegistry.dragon_ice;
			case LIGHTNING: return IafBlockRegistry.lightning_stone;
			default: return Blocks.MAGMA;
		}
	}
}