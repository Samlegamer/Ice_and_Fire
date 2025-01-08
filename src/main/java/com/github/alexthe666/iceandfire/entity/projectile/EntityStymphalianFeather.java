package com.github.alexthe666.iceandfire.entity.projectile;

import com.github.alexthe666.iceandfire.IceAndFireConfig;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityStymphalianFeather extends EntityArrow {

    public EntityStymphalianFeather(World worldIn) {
        super(worldIn);
    }

    public EntityStymphalianFeather(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        this.setDamage(IceAndFireConfig.ENTITY_SETTINGS.stymphalianBirdFeatherAttackStength);
    }

    @Override
    public void setDead() {
        super.setDead();
        if(IceAndFireConfig.ENTITY_SETTINGS.stymphalianBirdFeatherProjectileItem){
            if (!world.isRemote && this.rand.nextInt(IceAndFireConfig.ENTITY_SETTINGS.stymphalianBirdFeatherDropChance) == 0) {
                this.entityDropItem(getArrowStack(), 0.1F);
            }
        }

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 100) {
            this.setDead();
        }
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        if (!(this.shootingEntity instanceof EntityStymphalianBird && raytraceResultIn.entityHit instanceof EntityStymphalianBird)) {
            super.onHit(raytraceResultIn);
            if (raytraceResultIn.entityHit instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase) raytraceResultIn.entityHit;
                entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() - 1);
                ItemStack itemstack1 = entitylivingbase.isHandActive() ? entitylivingbase.getActiveItemStack() : ItemStack.EMPTY;
                if (itemstack1.getItem().isShield(itemstack1, entitylivingbase)) {
                    damageShield(entitylivingbase, 1.0F);
                }
            }
        }
    }

    protected void damageShield(EntityLivingBase entity, float damage) {
        if (damage >= 3.0F && entity.getActiveItemStack().getItem().isShield(entity.getActiveItemStack(), entity)) {
            ItemStack copyBeforeUse = entity.getActiveItemStack().copy();
            int i = 1 + MathHelper.floor(damage);
            entity.getActiveItemStack().damageItem(i, entity);

            if (entity.getActiveItemStack().isEmpty()) {
                EnumHand enumhand = entity.getActiveHand();
                if (entity instanceof EntityPlayer) {
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) entity, copyBeforeUse, enumhand);
                }

                if (enumhand == EnumHand.MAIN_HAND) {
                    this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }
                this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(IafItemRegistry.stymphalian_bird_feather);
    }
}
