package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.IceAndFireConfig;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemTrollArmor;
import com.github.alexthe666.iceandfire.item.ItemTrollWeapon;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public enum EnumTroll {
    FOREST(BiomeDictionary.Type.FOREST, IafItemRegistry.troll_forest, IafItemRegistry.troll_leather_forest, Weapon.TRUNK, Weapon.COLUMN_FOREST, Weapon.AXE, Weapon.HAMMER),
    FROST(BiomeDictionary.Type.SNOWY, IafItemRegistry.troll_frost, IafItemRegistry.troll_leather_frost, Weapon.COLUMN_FROST, Weapon.TRUNK_FROST, Weapon.AXE, Weapon.HAMMER),
    MOUNTAIN(BiomeDictionary.Type.MOUNTAIN, IafItemRegistry.troll_mountain, IafItemRegistry.troll_leather_mountain, Weapon.COLUMN, Weapon.AXE, Weapon.HAMMER);

    public final ResourceLocation TEXTURE;
    public final ResourceLocation TEXTURE_STONE;
    public final ResourceLocation TEXTURE_EYES;
    public final BiomeDictionary.Type spawnBiome;
    public final ItemArmor.ArmorMaterial material;
    public final Item leather;
    private Weapon[] weapons;
    @GameRegistry.ObjectHolder(IceAndFire.MODID + ":troll_helmet")
    public Item helmet;
    @GameRegistry.ObjectHolder(IceAndFire.MODID + ":troll_chestplate")
    public Item chestplate;
    @GameRegistry.ObjectHolder(IceAndFire.MODID + ":troll_leggings")
    public Item leggings;
    @GameRegistry.ObjectHolder(IceAndFire.MODID + ":troll_boots")
    public Item boots;

    EnumTroll(BiomeDictionary.Type biome, ItemArmor.ArmorMaterial material, Item leather, Weapon... weapons){
        spawnBiome = biome;
        this.weapons = weapons;
        this.material = material;
        this.leather = leather;
        TEXTURE = new ResourceLocation("iceandfire:textures/models/troll/troll_" + this.name().toLowerCase() + ".png");
        TEXTURE_STONE = new ResourceLocation("iceandfire:textures/models/troll/troll_" + this.name().toLowerCase() + "_stone.png");
        TEXTURE_EYES = new ResourceLocation("iceandfire:textures/models/troll/troll_" + this.name().toLowerCase() + "_eyes.png");
        helmet = new ItemTrollArmor(this, material, 0, EntityEquipmentSlot.HEAD);
        chestplate = new ItemTrollArmor(this, material, 1, EntityEquipmentSlot.CHEST);
        leggings = new ItemTrollArmor(this, material, 2, EntityEquipmentSlot.LEGS);
        boots = new ItemTrollArmor(this, material, 3, EntityEquipmentSlot.FEET);

    }

    public static EnumTroll getBiomeType(Biome biome) {
        if (!IceAndFireConfig.getTrollSpawnType().isEmpty()) {
            String biomeName =  biome.getRegistryName() != null ? biome.getRegistryName().toString() : null;
            if (IceAndFireConfig.getTrollSpawnType().containsKey(biomeName)) {
                String value = IceAndFireConfig.getTrollSpawnType().get(biomeName);
                EnumTroll type = getType(value);
                if (type != null) {
                    return type;
                }
            }
        }
        List<EnumTroll> types = new ArrayList<EnumTroll>();
        for (EnumTroll type : values()) {
            if (BiomeDictionary.hasType(biome, type.spawnBiome)) {
                types.add(type);
            }
        }
        if (types.isEmpty()) {
            return values()[new Random().nextInt(values().length)];
        } else {
            return types.get(new Random().nextInt(types.size()));
        }
    }

    public static Weapon getWeaponForType(EnumTroll troll){
        return troll.weapons[new Random().nextInt(troll.weapons.length)];
    }

    private static EnumTroll getType(String value) {
        EnumTroll type = null;
        try {
            type = EnumTroll.valueOf(value.toUpperCase());
        } catch (Exception e) {
            IceAndFire.logger.error("Invalid troll spawn type: " + value);
        }
        return type;
    }

    public enum Weapon {
        AXE, COLUMN, COLUMN_FOREST, COLUMN_FROST, HAMMER, TRUNK, TRUNK_FROST;
        public ResourceLocation TEXTURE;
        public Item item;
        Weapon(){
            TEXTURE = new ResourceLocation("iceandfire:textures/models/troll/weapon/weapon_" + this.name().toLowerCase() + ".png");
            item = new ItemTrollWeapon(this);
        }

    }
}
