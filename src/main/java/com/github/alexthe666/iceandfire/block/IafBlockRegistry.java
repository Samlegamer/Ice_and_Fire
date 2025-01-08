package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.entity.tile.*;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IafBlockRegistry {
	public static final SoundType SOUND_TYPE_GOLD = new SoundType(1.0F, 1.0F, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_STEP);

	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":lectern")
	public static Block lectern = new BlockLectern();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":podium")
	public static Block podium = new BlockPodium();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":fire_lily")
	public static Block fire_lily = new BlockElementalFlower("fire_lily", 0);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frost_lily")
	public static Block frost_lily = new BlockElementalFlower("frost_lily", 1);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":lightning_lily")
	public static Block lightning_lily = new BlockElementalFlower("lightning_lily", 2);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":copperpile")
	public static Block copperPile = new BlockCoinPile("copper", IafItemRegistry.copperNugget);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":goldpile")
	public static Block goldPile = new BlockCoinPile("gold", Items.GOLD_NUGGET);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":silverpile")
	public static Block silverPile = new BlockCoinPile("silver", IafItemRegistry.silverNugget);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":diamondpile")
	public static Block diamondPile = new BlockDiamondCoinPile("diamond");
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":amethyst_ore")
	public static Block amethystOre = new BlockDragonOre(2, 3.0F, 5.0F, "iceandfire.amethystOre", "amethyst_ore", IafItemRegistry.amethystGem);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":copper_ore")
	public static Block copperOre = new BlockDragonOre(2, 3.0F, 5.0F, "iceandfire.copperOre", "copper_ore");
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":silver_ore")
	public static Block silverOre = new BlockDragonOre(2, 3.0F, 5.0F, "iceandfire.silverOre", "silver_ore");
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":sapphire_ore")
	public static Block sapphireOre = new BlockDragonOre(2, 3.0F, 5.0F, "iceandfire.sapphireOre", "sapphire_ore", IafItemRegistry.sapphireGem);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":amethyst_block")
	public static Block amethystBlock = new BlockGeneric(Material.IRON, "amethyst_block", "iceandfire.amethystBlock", "pickaxe", 2, 3.0F, 10.0F, SoundType.METAL);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":copper_block")
	public static Block copperBlock = new BlockGeneric(Material.IRON, "copper_block", "iceandfire.copperBlock", "pickaxe", 2, 3.0F, 10.0F, SoundType.METAL);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":silver_block")
	public static Block silverBlock = new BlockGeneric(Material.IRON, "silver_block", "iceandfire.silverBlock", "pickaxe", 2, 3.0F, 10.0F, SoundType.METAL);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":sapphire_block")
	public static Block sapphireBlock = new BlockGeneric(Material.IRON, "sapphire_block", "iceandfire.sapphireBlock", "pickaxe", 2, 3.0F, 10.0F, SoundType.METAL);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_dirt")
	public static Block charedDirt = new BlockReturningState(Material.GROUND, "chared_dirt", "iceandfire.charedDirt", "shovel", 0, 0.5F, 0.0F, SoundType.GROUND, Blocks.DIRT.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_grass")
	public static Block charedGrass = new BlockReturningState(Material.GRASS, "chared_grass", "iceandfire.charedGrass", "shovel", 0, 0.6F, 0.0F, SoundType.GROUND, Blocks.GRASS.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_stone")
	public static Block charedStone = new BlockReturningState(Material.ROCK, "chared_stone", "iceandfire.charedStone", "pickaxe", 0, 1.5F, 10.0F, SoundType.STONE, Blocks.STONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_cobblestone")
	public static Block charedCobblestone = new BlockReturningState(Material.ROCK, "chared_cobblestone", "iceandfire.charedCobblestone", "pickaxe", 0, 2F, 10.0F, SoundType.STONE, Blocks.COBBLESTONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_gravel")
	public static Block charedGravel = new BlockFallingReturningState(Material.GROUND, "chared_gravel", "iceandfire.charedGravel", "pickaxe", 0, 0.6F, 0F, SoundType.GROUND, Blocks.GRAVEL.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":chared_grass_path")
	public static Block charedGrassPath = new BlockPath(BlockPath.Type.CHARED);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_dirt")
	public static Block crackledDirt = new BlockReturningState(Material.GROUND, "crackled_dirt", "iceandfire.crackledDirt", "shovel", 0, 0.5F, 0.0F, SoundType.GROUND, Blocks.DIRT.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_grass")
	public static Block crackledGrass = new BlockReturningState(Material.GRASS, "crackled_grass", "iceandfire.crackledGrass", "shovel", 0, 0.6F, 0.0F, SoundType.GROUND, Blocks.GRASS.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_stone")
	public static Block crackledStone = new BlockReturningState(Material.ROCK, "crackled_stone", "iceandfire.crackledStone", "pickaxe", 0, 1.5F, 10.0F, SoundType.STONE, Blocks.STONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_cobblestone")
	public static Block crackledCobblestone = new BlockReturningState(Material.ROCK, "crackled_cobblestone", "iceandfire.crackledCobblestone", "pickaxe", 0, 2F, 10.0F, SoundType.STONE, Blocks.COBBLESTONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_gravel")
	public static Block crackledGravel = new BlockFallingReturningState(Material.GROUND, "crackled_gravel", "iceandfire.crackledGravel", "pickaxe", 0, 0.6F, 0F, SoundType.GROUND, Blocks.GRAVEL.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":crackled_grass_path")
	public static Block crackledGrassPath = new BlockPath(BlockPath.Type.CRACKLED);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":ash")
	public static Block ash = new BlockFallingGeneric(Material.SAND, "ash", "iceandfire.ash", "shovel", 0, 0.5F, 0F, SoundType.SAND);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_dirt")
	public static Block frozenDirt = new BlockReturningState(Material.GROUND, "frozen_dirt", "iceandfire.frozenDirt", "shovel", 0, 0.5F, 0.0F, SoundType.GLASS, true, Blocks.DIRT.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_grass")
	public static Block frozenGrass = new BlockReturningState(Material.GRASS, "frozen_grass", "iceandfire.frozenGrass", "shovel", 0, 0.6F, 0.0F, SoundType.GLASS, true, Blocks.GRASS.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_stone")
	public static Block frozenStone = new BlockReturningState(Material.ROCK, "frozen_stone", "iceandfire.frozenStone", "pickaxe", 0, 1.5F, 10.0F, SoundType.GLASS, true, Blocks.STONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_cobblestone")
	public static Block frozenCobblestone = new BlockReturningState(Material.ROCK, "frozen_cobblestone", "iceandfire.frozenCobblestone", "pickaxe", 0, 2F, 10.0F, SoundType.GLASS, true, Blocks.COBBLESTONE.getDefaultState());
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_gravel")
	public static Block frozenGravel = new BlockFallingReturningState(Material.GROUND, "frozen_gravel", "iceandfire.frozenGravel", "pickaxe", 0, 0.6F, 0F, SoundType.GLASS, Blocks.GRAVEL.getDefaultState(), true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_grass_path")
	public static Block frozenGrassPath = new BlockPath(BlockPath.Type.FROZEN);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":frozen_splinters")
	public static Block frozenSplinters = new BlockGeneric(Material.WOOD, "frozen_splinters", "iceandfire.frozenSplinters", "pickaxe", 0, 2.0F, 10.0F, SoundType.GLASS, true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dragon_ice")
	public static Block dragon_ice = new BlockGeneric(Material.PACKED_ICE, "dragon_ice", "iceandfire.dragon_ice", "pickaxe", 0, 0.5F, 0F, SoundType.GLASS, true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":lightning_stone")
	public static Block lightning_stone = new BlockGeneric(Material.ROCK, "lightning_stone", "iceandfire.lightning_stone", "pickaxe", 0, 2F, 10F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dragon_ice_spikes")
	public static Block dragon_ice_spikes = new BlockIceSpikes();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":nest")
	public static Block nest = new BlockGeneric(Material.GRASS, "nest", "iceandfire.nest", "axe", 0, 0.5F, 0F, SoundType.GROUND);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":ancient_dragon_bone_block")
	public static Block ancient_dragon_bone_block = new BlockDragonBone("ancient_dragon_bone_block", "iceandfire.ancient_dragon_bone_block");
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":ancient_dragon_bone_wall")
	public static Block ancient_dragon_bone_block_wall = new BlockDragonBoneWall("ancient_dragon_bone_wall", "iceandfire.ancient_dragon_bone_wall", IafBlockRegistry.ancient_dragon_bone_block);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dragon_bone_block")
	public static Block dragon_bone_block = new BlockDragonBone("dragon_bone_block", "iceandfire.dragon_bone_block");
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dragon_bone_wall")
	public static Block dragon_bone_block_wall = new BlockDragonBoneWall("dragon_bone_wall", "iceandfire.dragon_bone_wall", IafBlockRegistry.dragon_bone_block);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":egginice")
	public static Block eggInIce = new BlockEggInIce();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":pixie_house")
	public static Block pixieHouse = new BlockPixieHouse();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":jar_empty")
	public static Block jar_empty = new BlockJar(true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":jar_pixie")
	public static Block jar_pixie = new BlockJar(false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_resin")
	public static Block myrmex_resin = new BlockMyrmexResin(false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_resin_sticky")
	public static Block myrmex_resin_sticky = new BlockMyrmexResin(true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":desert_myrmex_cocoon")
	public static Block desert_myrmex_cocoon = new BlockMyrmexCocoon(false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":jungle_myrmex_cocoon")
	public static Block jungle_myrmex_cocoon = new BlockMyrmexCocoon(true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_desert_biolight")
	public static Block myrmex_desert_biolight = new BlockMyrmexBiolight(false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_jungle_biolight")
	public static Block myrmex_jungle_biolight = new BlockMyrmexBiolight(true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_desert_resin_block")
	public static Block myrmex_desert_resin_block = new BlockMyrmexConnectedResin(false, false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_jungle_resin_block")
	public static Block myrmex_jungle_resin_block = new BlockMyrmexConnectedResin(true, false);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_desert_resin_glass")
	public static Block myrmex_desert_resin_glass = new BlockMyrmexConnectedResin(false, true);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":myrmex_jungle_resin_glass")
	public static Block myrmex_jungle_resin_glass = new BlockMyrmexConnectedResin(true, true);

	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":ghost_chest")
	public static Block ghost_chest = new com.github.alexthe666.iceandfire.block.BlockGhostChest();

	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone")
	public static BlockDreadBase dread_stone = new BlockDreadBase(Material.ROCK, "dread_stone", "iceandfire.dread_stone", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_bricks")
	public static BlockDreadBase dread_stone_bricks = new BlockDreadBase(Material.ROCK, "dread_stone_bricks", "iceandfire.dread_stone_bricks", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_bricks_chiseled")
	public static BlockDreadBase dread_stone_bricks_chiseled = new BlockDreadBase(Material.ROCK, "dread_stone_bricks_chiseled", "iceandfire.dread_stone_bricks_chiseled", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_bricks_cracked")
	public static BlockDreadBase dread_stone_bricks_cracked = new BlockDreadBase(Material.ROCK, "dread_stone_bricks_cracked", "iceandfire.dread_stone_bricks_cracked", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_bricks_mossy")
	public static BlockDreadBase dread_stone_bricks_mossy = new BlockDreadBase(Material.ROCK, "dread_stone_bricks_mossy", "iceandfire.dread_stone_bricks_mossy", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_tile")
	public static BlockDreadBase dread_stone_tile = new BlockDreadBase(Material.ROCK, "dread_stone_tile", "iceandfire.dread_stone_tile", "pickaxe", 3, 20.0F, 100000.0F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_face")
	public static Block dread_stone_face = new BlockDreadStoneFace();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_torch")
	public static Block dread_torch = new BlockDreadTorch();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_stairs")
	public static Block dread_stone_bricks_stairs = new BlockGenericStairs(dread_stone_bricks.getDefaultState(), "dread_stone_stairs").setHardness(20F);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_double_slab")
	public static BlockGenericSlab dread_stone_bricks_double_slab = new BlockDreadStoneBrickSlab.Double("dread_stone_slab", 10.0F, 10000F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_stone_slab")
	public static BlockGenericSlab dread_stone_bricks_slab = new BlockDreadStoneBrickSlab.Half("dread_stone_slab", 10.0F, 10000F, SoundType.STONE);
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":dread_spawner")
	public static Block dread_spawner = new BlockDreadSpawner();
	@GameRegistry.ObjectHolder(IceAndFire.MODID + ":monster_spawner")
	public static Block monster_spawner = new BlockMonsterSpawner();

	static {
		GameRegistry.registerTileEntity(TileEntityDummyGorgonHead.class, new ResourceLocation(IceAndFire.MODID, "dummyGorgonHeadIdle"));
		GameRegistry.registerTileEntity(TileEntityDummyGorgonHeadActive.class, new ResourceLocation(IceAndFire.MODID, "dummyGorgonHeadActive"));
		GameRegistry.registerTileEntity(TileEntityMyrmexCocoon.class, new ResourceLocation(IceAndFire.MODID, "myrmexCocoon"));
		GameRegistry.registerTileEntity(TileEntityGhostChest.class, new ResourceLocation(IceAndFire.MODID, "ghostChest"));
	}
}