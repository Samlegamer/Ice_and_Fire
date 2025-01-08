package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.IceAndFireConfig;
import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.entity.ai.GorgonAIStareAttack;
import com.github.alexthe666.iceandfire.entity.util.*;
import com.github.alexthe666.iceandfire.enums.EnumParticle;
import com.github.alexthe666.iceandfire.util.ParticleHelper;
import com.google.common.base.Predicate;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityGorgon extends EntityMob implements IAnimatedEntity, IVillagerFear, IAnimalFear, IHumanoid {

	public static Animation ANIMATION_SCARE;
	public static Animation ANIMATION_HIT;
	private int animationTick;
	private Animation currentAnimation;
	private GorgonAIStareAttack aiStare;
	private EntityAIAttackMelee aiMelee;
	public static final ResourceLocation LOOT = LootTableList.register(new ResourceLocation("iceandfire", "gorgon"));
	private int statueCooldown = 0;

	public EntityGorgon(World worldIn) {
		super(worldIn);
		this.setSize(0.8F, 1.99F);
		ANIMATION_SCARE = Animation.create(30);
		ANIMATION_HIT = Animation.create(10);
	}

	public static boolean isEntityLookingAt(EntityLivingBase looker, EntityLivingBase seen, double degree) {
		degree *= 1 + (looker.getDistance(seen) * 0.1);
		Vec3d vec3d = looker.getLook(1.0F).normalize();
		Vec3d vec3d1 = new Vec3d(seen.posX - looker.posX, seen.getEntityBoundingBox().minY + (double) seen.getEyeHeight() - (looker.posY + (double) looker.getEyeHeight()), seen.posZ - looker.posZ);
		double d0 = vec3d1.length();
		vec3d1 = vec3d1.normalize();
		double d1 = vec3d.dotProduct(vec3d1);
		return d1 > 1.0D - degree / d0 && looker.canEntityBeSeen(seen) && !isStoneMob(seen);
	}

	public static boolean isBlindfolded(EntityLivingBase attackTarget) {
		return attackTarget != null && attackTarget.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == IafItemRegistry.blindfold;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LOOT;
	}

	public static boolean isStoneMob(EntityLivingBase mob) {
		if (mob instanceof EntityLiving) {
			IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(mob);
			return capability != null && capability.isStoned();
		}
		return false;
	}

	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIRestrictSun(this));
		this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		this.tasks.addTask(3, aiStare = new GorgonAIStareAttack(this, 1.0D, 0, 15.0F));
		this.tasks.addTask(3, aiMelee = new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(4, new GorgonAIStareAttack(this, 1.0D, 0, 15.0F));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D) {
			public boolean shouldExecute() {
				executionChance = 20;
				return super.shouldExecute();
			}
		});
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 1.0F) {
			public boolean shouldContinueExecuting() {
				if (this.closestEntity != null && this.closestEntity instanceof EntityPlayer && ((EntityPlayer) this.closestEntity).isCreative()) {
					return false;
				}
				return super.shouldContinueExecuting();
			}
		});
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, new Predicate<EntityPlayer>() {
			@Override
			public boolean apply(@Nullable EntityPlayer entity) {
				return entity != null && !entity.isCreative() && !entity.isSpectator();
			}
		}));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 0, true, false, new Predicate<EntityLiving>() {
			@Override
			public boolean apply(@Nullable EntityLiving entity) {
				if(entity != null && !entity.isDead && DragonUtils.isAlive(entity) && entity.canBeCollidedWith() && !(entity instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)entity).canBeTurnedToStone())) {
					if(!IceAndFireConfig.isEntityBlacklistedFromBeingStoned(entity)) {
						IEntityEffectCapability cap = InFCapabilities.getEntityEffectCapability(entity);
						return cap != null && !cap.isStoned();
					}
				}
				return false;
			}
		}));
		this.tasks.removeTask(aiMelee);
	}

	public void attackEntityWithRangedAttack(EntityLivingBase entity) {
		if (entity instanceof EntityLiving) {
			forcePreyToLook((EntityLiving) entity);
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean blindness = this.isPotionActive(MobEffects.BLINDNESS) ||
				this.getAttackTarget() != null && this.getAttackTarget().isPotionActive(MobEffects.BLINDNESS) ||
				this.getAttackTarget() != null && this.getAttackTarget() instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues) this.getAttackTarget()).canBeTurnedToStone() ||
				this.getAttackTarget() != null && isBlindfolded(this.getAttackTarget());
		if (blindness && this.deathTime == 0) {
			if (this.getAnimation() != ANIMATION_HIT) {
				this.setAnimation(ANIMATION_HIT);
			}
			if (entityIn instanceof EntityLivingBase) {
				((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 2, false, true));
			}
		}
		return super.attackEntityAsMob(entityIn);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);
		if (entitylivingbaseIn != null && !world.isRemote) {
			boolean blindness = this.isPotionActive(MobEffects.BLINDNESS) ||
					entitylivingbaseIn.isPotionActive(MobEffects.BLINDNESS) ||
					entitylivingbaseIn instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues) entitylivingbaseIn).canBeTurnedToStone() ||
					isBlindfolded(entitylivingbaseIn);
			if (blindness && this.deathTime == 0) {
				this.tasks.removeTask(aiStare);
				this.tasks.addTask(3, aiMelee);
			} else {
				this.tasks.removeTask(aiMelee);
				this.tasks.addTask(3, aiStare);
			}
		}
	}

	protected int getExperiencePoints(EntityPlayer player) {
		return 30;
	}

	protected void onDeathUpdate() {
		++this.deathTime;
		this.livingSoundTime = 20;
		if(this.world.isRemote) {
			for (int k = 0; k < 5; ++k) {
				double d2 = 0.4;
				double d0 = 0.1;
				double d1 = 0.1;
				IceAndFire.PROXY.spawnParticle(EnumParticle.BLOOD, this.world, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0, d1);
			}
		}
		if (this.deathTime >= 200) {
			if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))) {
				int i = this.getExperiencePoints(this.attackingPlayer);
				i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
				while (i > 0) {
					int j = EntityXPOrb.getXPSplit(i);
					i -= j;
					this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
				}
			}
			this.setDead();

			if(this.world.isRemote) {
				for (int k = 0; k < 20; ++k) {
					double d2 = this.rand.nextGaussian() * 0.02D;
					double d0 = this.rand.nextGaussian() * 0.02D;
					double d1 = this.rand.nextGaussian() * 0.02D;
					ParticleHelper.spawnParticle(this.world, EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0, d1);
				}
			}
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		if(statueCooldown > 0) statueCooldown--;
		if (this.getAttackTarget() != null) {
			boolean blindness = this.isPotionActive(MobEffects.BLINDNESS) ||
					this.getAttackTarget().isPotionActive(MobEffects.BLINDNESS) ||
					this.getAttackTarget() instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)this.getAttackTarget()).canBeTurnedToStone() ||
					isBlindfolded(this.getAttackTarget());
			this.getLookHelper().setLookPosition(this.getAttackTarget().posX, this.getAttackTarget().posY + (double) this.getAttackTarget().getEyeHeight(), this.getAttackTarget().posZ, (float) this.getHorizontalFaceSpeed(), (float) this.getVerticalFaceSpeed());
			if (!blindness && this.deathTime == 0 && this.getAttackTarget() instanceof EntityLiving && !(this.getAttackTarget() instanceof EntityPlayer)) {
				forcePreyToLook((EntityLiving) this.getAttackTarget());
			}
		}

		if (this.getAttackTarget() != null && isEntityLookingAt(this, this.getAttackTarget(), 0.4) && isEntityLookingAt(this.getAttackTarget(), this, 0.4)) {
			boolean blindness = this.isPotionActive(MobEffects.BLINDNESS) ||
					this.getAttackTarget().isPotionActive(MobEffects.BLINDNESS) ||
					this.getAttackTarget() instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)this.getAttackTarget()).canBeTurnedToStone() ||
					isBlindfolded(this.getAttackTarget());
			if (!blindness && this.deathTime == 0) {
				if (this.getAnimation() != ANIMATION_SCARE) {
					this.playSound(IafSoundRegistry.GORGON_ATTACK, 1, 1);
					this.setAnimation(ANIMATION_SCARE);
				}
				if (this.getAnimation() == ANIMATION_SCARE) {
					if (this.getAnimationTick() > 10) {
						if (this.getAttackTarget() instanceof EntityPlayer) {
							if (!world.isRemote) {
								this.getAttackTarget().attackEntityFrom(IceAndFire.gorgon, Integer.MAX_VALUE);
								if (!this.getAttackTarget().isEntityAlive() && statueCooldown <= 0) {
									EntityStoneStatue statue = new EntityStoneStatue(world);
									statue.setPositionAndRotation(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, this.getAttackTarget().rotationYaw, this.getAttackTarget().rotationPitch);
									statue.smallArms = true;
									statue.prevRotationYaw = this.getAttackTarget().rotationYaw;
									statue.rotationYaw = this.getAttackTarget().rotationYaw;
									statue.rotationYawHead = this.getAttackTarget().rotationYaw;
									statue.renderYawOffset = this.getAttackTarget().rotationYaw;
									statue.prevRenderYawOffset = this.getAttackTarget().rotationYaw;
									world.spawnEntity(statue);
									statueCooldown = 40;
								}
								this.setAttackTarget(null);
							}
						} else {
							if (this.getAttackTarget() instanceof EntityLiving && !(this.getAttackTarget() instanceof IBlacklistedFromStatues) || this.getAttackTarget() instanceof IBlacklistedFromStatues && ((IBlacklistedFromStatues) this.getAttackTarget()).canBeTurnedToStone()) {
								IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(this.getAttackTarget());
								EntityLiving attackTarget = (EntityLiving) this.getAttackTarget();
								if (capability != null && !capability.isStoned()) {
									capability.setStoned();
									this.playSound(IafSoundRegistry.GORGON_TURN_STONE, 1, 1);
									this.setAttackTarget(null);
								}

								if (attackTarget instanceof EntityDragonBase) {
									EntityDragonBase dragon = (EntityDragonBase) attackTarget;
									dragon.setFlying(false);
									dragon.setHovering(false);
									dragon.airTarget = null;
								}
								if (attackTarget instanceof EntityHippogryph) {
									EntityHippogryph dragon = (EntityHippogryph) attackTarget;
									dragon.setFlying(false);
									dragon.setHovering(false);
									dragon.airTarget = null;
								}
							}
						}
					}
				}
			}
		}
		AnimationHandler.INSTANCE.updateAnimations(this);
		EntityPlayer player = world.getClosestPlayerToEntity(this, 25);
		//if (player != null) {
		//	player.addStat(ModAchievements.findGorgon);
		//}
	}

	public int getVerticalFaceSpeed() {
		return 10;
	}

	public int getHorizontalFaceSpeed() {
		return 10;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	public void forcePreyToLook(EntityLiving mob) {
		mob.getLookHelper().setLookPosition(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ, (float) mob.getHorizontalFaceSpeed(), (float) mob.getVerticalFaceSpeed());
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(IceAndFireConfig.ENTITY_SETTINGS.gorgonMaxHealth);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{ANIMATION_SCARE, ANIMATION_HIT};
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return IafSoundRegistry.GORGON_IDLE;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return IafSoundRegistry.GORGON_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return IafSoundRegistry.GORGON_DIE;
	}

	@Override
	public boolean shouldAnimalsFear(Entity entity) {
		return true;
	}
}
