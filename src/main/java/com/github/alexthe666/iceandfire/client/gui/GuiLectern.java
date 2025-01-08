package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.client.StatCollector;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.inventory.ContainerLectern;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiLectern extends GuiContainer {
	private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("iceandfire:textures/gui/lectern.png");
	private final InventoryPlayer playerInventory;
	private final IInventory tileFurnace;

	public GuiLectern(InventoryPlayer playerInv, IInventory furnaceInv) {
		super(new ContainerLectern(playerInv, furnaceInv));
		this.playerInventory = playerInv;
		this.tileFurnace = furnaceInv;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if (tileFurnace != null) {
			String s = this.tileFurnace.getDisplayName().getUnformattedText();
			this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		}
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
		String s1 = StatCollector.translateToLocal("lectern.nopages");
		if (hasAllPages())
			this.fontRenderer.drawString(s1, this.xSize / 2 - this.fontRenderer.getStringWidth("No new information can be added.") / 2, 20, 4210752);
	}

	public boolean hasAllPages() {
		if (tileFurnace.getStackInSlot(0).getItem() == IafItemRegistry.bestiary) {
			List<EnumBestiaryPages> list = EnumBestiaryPages.possiblePages(tileFurnace.getStackInSlot(0));
			return list == null || list.isEmpty();
		}
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		i1 = this.func_175381_h(25);
		this.drawTexturedModalRect(k + 76, l + 36, 176, 0, i1, 16);
	}

	private int func_175381_h(int p_175381_1_) {
		int j = this.tileFurnace.getField(2);
		int k = this.tileFurnace.getField(3);
		return k != 0 && j != 0 ? j * p_175381_1_ / k : 0;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}