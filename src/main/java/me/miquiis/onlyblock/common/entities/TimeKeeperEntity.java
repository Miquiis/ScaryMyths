package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
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

public class TimeKeeperEntity extends MobEntity implements IMerchant, IAnimatable {

    private AnimationFactory animationFactory = new AnimationFactory(this);
    private PlayerEntity player;

    public TimeKeeperEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public TimeKeeperEntity(World worldIn) {
        super(EntityRegister.TIME_KEEPER.get(), worldIn);
    }

    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!this.world.isRemote && !getOffers().isEmpty()) {
            setCustomer(playerIn);
            OptionalInt optionalint = playerIn.openContainer(new SimpleNamedContainerProvider((id, playerInventory, player2) -> {
                return new MerchantContainer(id, playerInventory, this);
            }, new StringTextComponent("Time Keeper")));
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
                new ItemStack(Items.DIAMOND, 64),
                createTimeClock(),
                1,
                0,
                1
        ));
        return merchantOffers;
    }

    private ItemStack createTimeClock()
    {
        ItemStack itemStack = new ItemStack(Items.CLOCK);
        itemStack.setDisplayName(new StringTextComponent("\u00A76\u00A7lTime Advancer"));
        CompoundNBT displayTag = itemStack.getChildTag("display");
        ListNBT listnbt = new ListNBT();
        CompoundNBT test = new CompoundNBT();
        test.putString("2", ITextComponent.Serializer.toJson(new StringTextComponent("\u00A7eAdvances time in 10 days.")));
        listnbt.add(test.get("2"));
        displayTag.put("Lore", listnbt);
        CompoundNBT tag = itemStack.getOrCreateTag();
        tag.putInt("TimeForward", 10);
        tag.put("display", displayTag);
        itemStack.setTag(tag);
        return itemStack;
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
