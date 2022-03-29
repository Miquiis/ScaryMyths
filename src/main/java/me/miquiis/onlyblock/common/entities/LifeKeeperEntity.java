package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.*;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LifeKeeperEntity extends Entity implements IMerchant {

    private PlayerEntity player;

    public LifeKeeperEntity(World worldIn, PlayerEntity player) {
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
                new ItemStack(Items.IRON_INGOT, 10),
                new ItemStack(Items.STONE_PICKAXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 10),
                new ItemStack(Items.IRON_PICKAXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(Items.DIAMOND_PICKAXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 20),
                new ItemStack(Items.STONE_SWORD),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 20),
                new ItemStack(Items.IRON_SWORD),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                new ItemStack(Items.DIAMOND_SWORD),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 5),
                new ItemStack(Items.STONE_SHOVEL),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.IRON_SHOVEL),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(Items.DIAMOND_SHOVEL),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 15),
                new ItemStack(Items.STONE_AXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 15),
                new ItemStack(Items.IRON_AXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(Items.DIAMOND_AXE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 10),
                new ItemStack(Items.BOW),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 15),
                new ItemStack(Items.IRON_HELMET),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(Items.DIAMOND_HELMET),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 15),
                new ItemStack(Items.IRON_CHESTPLATE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 15),
                new ItemStack(Items.DIAMOND_CHESTPLATE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 10),
                new ItemStack(Items.IRON_LEGGINGS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 5),
                new ItemStack(Items.DIAMOND_LEGGINGS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.IRON_BOOTS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 10),
                new ItemStack(Items.DIAMOND_BOOTS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 25),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_STRENGTH),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 25),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_SWIFTNESS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 25),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.LONG_FIRE_RESISTANCE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 25),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.LONG_NIGHT_VISION),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.EMERALD, 25),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_HEALING),
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
