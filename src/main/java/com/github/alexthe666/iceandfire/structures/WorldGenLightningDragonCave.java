package com.github.alexthe666.iceandfire.structures;

import com.github.alexthe666.iceandfire.IceAndFireConfig;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityLightningDragon;
import com.github.alexthe666.iceandfire.integration.CompatLoadUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenLightningDragonCave extends WorldGenDragonCave {
	public static final ResourceLocation LIGHTNINGDRAGON_CHEST = LootTableList.register(new ResourceLocation("iceandfire", "lightning_dragon_female_cave"));
	public static final ResourceLocation LIGHTNINGDRAGON_MALE_CHEST = LootTableList.register(new ResourceLocation("iceandfire", "lightning_dragon_male_cave"));
	protected IBlockState getStone() {
		return IafBlockRegistry.crackledStone.getDefaultState();
	}

	protected IBlockState getCobblestone() {
		return IafBlockRegistry.crackledCobblestone.getDefaultState();
	}

	protected IBlockState getPile() {
		if (CompatLoadUtil.isVariedCommoditiesLoaded()) {
			return IafBlockRegistry.diamondPile.getDefaultState();
		}
		return IafBlockRegistry.copperPile.getDefaultState();
	}

	protected IBlockState getGemstone() {
		return IceAndFireConfig.WORLDGEN.generateAmethystOre ? IafBlockRegistry.amethystOre.getDefaultState() : Blocks.EMERALD_ORE.getDefaultState();
	}

	protected ResourceLocation getLootTable() {
		if (isMale) {
			return LIGHTNINGDRAGON_MALE_CHEST;
		} else return LIGHTNINGDRAGON_CHEST;
	}

	protected EntityDragonBase createDragon(World worldIn) {
		return new EntityLightningDragon(worldIn);
	}
}
