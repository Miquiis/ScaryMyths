package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
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

public class BuildKeeperEntity extends MobEntity implements IMerchant, IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private PlayerEntity player;

    public BuildKeeperEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BuildKeeperEntity(World worldIn) {
        super(EntityRegister.BUILD_KEEPER.get(), worldIn);
    }

    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!this.world.isRemote && !getOffers().isEmpty()) {
            setCustomer(playerIn);
            OptionalInt optionalint = playerIn.openContainer(new SimpleNamedContainerProvider((id, playerInventory, player2) -> {
                return new MerchantContainer(id, playerInventory, this);
            }, new StringTextComponent("Build Keeper")));
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
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.STONE),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.OAK_LOG),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.GLASS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 5),
                new ItemStack(Items.BRICKS),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.IRON_INGOT, 64),
                new ItemStack(Items.ANVIL),
                9999,
                0,
                1
        ));
        merchantOffers.add(new MerchantOffer(
                new ItemStack(Items.GOLD_INGOT, 25),
                new ItemStack(Items.TNT),
                9999,
                0,
                1
        ));
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
                EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(Enchantments.PROTECTION, 5)),
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
