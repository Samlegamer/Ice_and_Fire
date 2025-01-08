package com.github.alexthe666.iceandfire.misc;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.core.ModBlocks;
import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs {


	public CreativeTab(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return this == IceAndFire.TAB_ITEMS ? new ItemStack(ModItems.dragon_skull) : new ItemStack(ModBlocks.dragon_bone_block);
	}

	@Override
	public boolean hasSearchBar() {
		return false;
	}
}
