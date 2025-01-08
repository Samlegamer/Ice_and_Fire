package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.item.ICustomRendered;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockMyrmexResin extends Block implements ICustomRendered {

    public final boolean sticky;
    private static final AxisAlignedBB STICKY_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", BlockMyrmexResin.EnumType.class);

    public BlockMyrmexResin(boolean sticky) {
        super(Material.CLAY);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.DESERT));
        this.setHardness(2.5F);
        this.setTranslationKey(sticky ? "iceandfire.myrmex_resin_sticky" : "iceandfire.myrmex_resin");
        this.setCreativeTab(IceAndFire.TAB_BLOCKS);
        this.setSoundType(sticky ? SoundType.SLIME : SoundType.GROUND);
        this.setRegistryName(IceAndFire.MODID, sticky ? "myrmex_resin_sticky" : "myrmex_resin");
        this.sticky = sticky;
    }

    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        if (entity instanceof EntityMyrmexBase) {
            return super.getSlipperiness(state, world, pos, entity);
        }
        return 0.75F;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumType.values()[MathHelper.clamp(meta, 0, 1)]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state) {
        Item item = Item.getItemFromBlock(this);
        int i = this.getMetaFromState(state);
        return new ItemStack(item, 1, i);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.sticky ? STICKY_AABB : super.getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if(this.sticky) {
            if((entityIn instanceof EntityMyrmexBase)){
                entityIn.motionX *= 1.2D;
                entityIn.motionY *= 1.2D;
                entityIn.motionZ *= 1.2D;
            }else{
                entityIn.motionX *= 0.4D;
                entityIn.motionZ *= 0.4D;
            }
        }
    }

    public enum EnumType implements IStringSerializable {
        DESERT("desert"),
        JUNGLE("jungle");

        private final String name;
        EnumType(String name){
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}