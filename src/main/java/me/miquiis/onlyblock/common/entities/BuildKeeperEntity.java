package me.miquiis.onlyblock.common.entities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BuildKeeperEntity extends Entity implements IMerchant {

    private PlayerEntity player;

    public BuildKeeperEntity(World worldIn, PlayerEntity player) {
        super(EntityType.VILLAGER, worldIn);
        this.player = player;
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {

    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return player;
    }

    @Override
    public MerchantOffers getOffers() {
        MerchantOffers merchantOffers = new MerchantOffers();
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.FIRE_ASPECT, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.FIRE_ASPECT, 2)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SHARPNESS, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SHARPNESS, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SHARPNESS, 5)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.POWER, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.POWER, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.POWER, 5)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.UNBREAKING, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.UNBREAKING, 2)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.UNBREAKING, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.MENDING, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.KNOCKBACK, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.KNOCKBACK, 2)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.PROTECTION, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.PROTECTION, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.KNOCKBACK, 5)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.INFINITY, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.FLAME, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.FLAME, 2)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 5)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SMITE, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SMITE, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.SMITE, 5)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.EFFICIENCY, 1)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.EFFICIENCY, 3)),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 25),
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.EFFICIENCY, 5)),
                9999,
                0,
                1
        ));
        return merchantOffers;
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers) {

    }

    @Override
    public void onTrade(MerchantOffer offer) {

    }

    @Override
    public void verifySellingItem(ItemStack stack) {

    }

    @Override
    public World getWorld() {
        return player.world;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public void setXP(int xpIn) {

    }

    @Override
    public boolean hasXPBar() {
        return false;
    }

    @Override
    public SoundEvent getYesSound() {
        return null;
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}
