package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.OptionalInt;

public class LifeKeeperEntity extends MobEntity implements IMerchant, IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private PlayerEntity player;

    public LifeKeeperEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public LifeKeeperEntity(World worldIn) {
        super(EntityRegister.LIFE_KEEPER.get(), worldIn);
    }

    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!this.world.isRemote && !getOffers().isEmpty()) {
            setCustomer(playerIn);
            OptionalInt optionalint = playerIn.openContainer(new SimpleNamedContainerProvider((id, playerInventory, player2) -> {
                return new MerchantContainer(id, playerInventory, this);
            }, new StringTextComponent("Life Keeper")));
            if (optionalint.isPresent()) {
                MerchantOffers merchantoffers = getOffers();
                if (!merchantoffers.isEmpty()) {
                    playerIn.openMerchantContainer(optionalint.getAsInt(), merchantoffers, 0, 0, true, true);
                }
            }
        }
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        this.player = player;
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
                new ItemStack(Items.IRON_INGOT, 15),
                new ItemStack(Items.COOKED_BEEF),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 5),
                new ItemStack(Items.MELON),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 10),
                new ItemStack(Items.POTATO),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 64),
                new ItemStack(Items.DIAMOND, 64),
                new ItemStack(Items.CAKE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 25),
                new ItemStack(Items.GOLDEN_APPLE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.DIAMOND, 32),
                new ItemStack(Items.ENCHANTED_GOLDEN_APPLE),
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
        return this.world;
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, event -> {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }
}
