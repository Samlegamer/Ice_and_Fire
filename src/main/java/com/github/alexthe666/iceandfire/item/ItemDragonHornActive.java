package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.StatCollector;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.EntityLightningDragon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDragonHornActive extends Item {

	public ItemDragonHornActive(String name) {
		this.maxStackSize = 1;
		this.setTranslationKey("iceandfire." + name);
		this.setRegistryName(IceAndFire.MODID, name);
		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if (entityIn == null) {
					return 0.0F;
				} else {
					ItemStack itemstack = entityIn.getActiveItemStack();
					return !itemstack.isEmpty() && itemstack.getItem() instanceof ItemDragonHornActive ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.setTagCompound(new NBTTagCompound());
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			int i = this.getMaxItemUseDuration(stack) - timeLeft;
			if (i < 20) {
				return;
			}
			double d0 = player.prevPosX + (player.posX - player.prevPosX);
			double d1 = player.prevPosY + (player.posY - player.prevPosY) + (double) player.getEyeHeight();
			double d2 = player.prevPosZ + (player.posZ - player.prevPosZ);
			float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch);
			float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw);
			Vec3d vec3d = new Vec3d(d0, d1, d2);
			float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
			float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
			float f5 = -MathHelper.cos(-f1 * 0.017453292F);
			float f6 = MathHelper.sin(-f1 * 0.017453292F);
			float f7 = f4 * f5;
			float f8 = f3 * f5;
			Vec3d vec3d1 = vec3d.add((double) f7 * 5.0D, (double) f6 * 5.0D, (double) f8 * 5.0D);
			RayTraceResult raytraceresult = worldIn.rayTraceBlocks(vec3d, vec3d1, true);
			if (raytraceresult == null) {
				return;
			}
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos pos = raytraceresult.getBlockPos();
				worldIn.playSound(player, pos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 3, 0.75F);
				EntityDragonBase dragon = null;
				if (this == IafItemRegistry.dragon_horn_fire) {
					dragon = new EntityFireDragon(worldIn);
				}
				if (this == IafItemRegistry.dragon_horn_ice) {
					dragon = new EntityIceDragon(worldIn);
				}
				if (this == IafItemRegistry.dragon_horn_lightning) {
					dragon = new EntityLightningDragon(worldIn);
				}
				if (dragon != null) {
					maybeSpawnDragon(worldIn, pos, dragon, player, stack);
				}
				player.addStat(StatList.getObjectUseStats(this));
			}
		}

	}

	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityplayer, EnumHand hand) {
		ItemStack itemStackIn = entityplayer.getHeldItem(hand);
		entityplayer.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getTagCompound() != null) {
			String fire = new TextComponentTranslation("entity.firedragon.name").getUnformattedText();
			String ice = new TextComponentTranslation("entity.icedragon.name").getUnformattedText();
			String lightning = new TextComponentTranslation("entity.lightningdragon.name").getUnformattedText();
			tooltip.add(this == IafItemRegistry.dragon_horn_fire ? fire : this == IafItemRegistry.dragon_horn_ice ? ice : lightning);
			String name = stack.getTagCompound().getString("CustomName").isEmpty() ? StatCollector.translateToLocal("dragon.unnamed") : StatCollector.translateToLocal("dragon.name") + stack.getTagCompound().getString("CustomName");
			tooltip.add(name);
			String gender = StatCollector.translateToLocal("dragon.gender") + StatCollector.translateToLocal((stack.getTagCompound().getBoolean("Gender") ? "dragon.gender.male" : "dragon.gender.female"));
			tooltip.add(gender);
			int stagenumber = stack.getTagCompound().getInteger("AgeTicks") / 24000;
			int stage1;
			{
				if (stagenumber >= 100) {
					stage1 = 5;
				} else if (stagenumber >= 75) {
					stage1 = 4;
				} else if (stagenumber >= 50) {
					stage1 = 3;
				} else if (stagenumber >= 25) {
					stage1 = 2;
				} else {
					stage1 = 1;
				}
			}
			String stage = StatCollector.translateToLocal("dragon.stage") + stage1 + " " + StatCollector.translateToLocal("dragon.days.front") + stagenumber + " " + StatCollector.translateToLocal("dragon.days.back");
			tooltip.add(stage);
		}
	}

	private void maybeSpawnDragon(World world, BlockPos pos, EntityDragonBase dragon, EntityPlayer player, ItemStack horn) {
		if (horn.getItem() instanceof ItemDragonHornActive) {
			if (player.getHeldItem(player.getActiveHand()).equals(horn)) {
				dragon.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
				if (horn.getTagCompound() != null) {
					dragon.readEntityFromNBT(horn.getTagCompound());
				}
				dragon.setFlying(false);
				dragon.setHovering(false);
				dragon.getNavigator().clearPath();
				horn.shrink(0);
				player.setHeldItem(player.getActiveHand(), new ItemStack(IafItemRegistry.dragon_horn));
				if (!world.isRemote) {
					world.spawnEntity(dragon);
				}
			}
		}
	}
}
