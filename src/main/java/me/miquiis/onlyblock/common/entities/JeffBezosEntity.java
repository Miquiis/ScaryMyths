package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.quests.AmazonQuestTwo;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JeffBezosEntity extends MonsterEntity implements IAnimatable {

    private static final List<String> DIALOGUE = new ArrayList<>(Arrays.asList(
            "&6<Jeff Bezos> &eHey, you there! The company needs your help.",
            "&6<Jeff Bezos> &eI need you to deliver &l10&e packages under &l3 minutes&e.",
            "&6<Jeff Bezos> &eYou are in or out?"
    ));

    private static final List<String> DIALOGUE_FINAL = new ArrayList<>(Arrays.asList(
            "&6<Jeff Bezos> &eThank you for helping out!.",
            "&6<Jeff Bezos> &eI don't have anything to give you, just thank you."
    ));

    private int currentDialogue;
    private boolean hasPlayedMinigame;

    private AnimationFactory factory = new AnimationFactory(this);

    public JeffBezosEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.setHealth(1);
        this.currentDialogue = 0;
        this.hasPlayedMinigame = false;
    }

    public JeffBezosEntity(World worldIn) {
        super(EntityRegister.JEFF_BEZOS.get(), worldIn);
        this.setHealth(1);
        this.currentDialogue = 0;
        this.hasPlayedMinigame = false;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5).createMutableAttribute(Attributes.FOLLOW_RANGE, 50).createMutableAttribute(Attributes.ARMOR, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F);
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!playerIn.world.isRemote && hand == Hand.MAIN_HAND)
        {
            if (this.hasNextDialogue() && !hasPlayedMinigame) {
                playerIn.sendStatusMessage(new StringTextComponent(DIALOGUE.get(currentDialogue++).replace("&", "\u00A7")), false);
                return super.getEntityInteractionResult(playerIn, hand);
            } else if (this.hasNextFinalDialogue() && hasPlayedMinigame) {
                playerIn.sendStatusMessage(new StringTextComponent(DIALOGUE_FINAL.get(currentDialogue++).replace("&", "\u00A7")), false);
                return super.getEntityInteractionResult(playerIn, hand);
            } else if (!hasPlayedMinigame)
            {
                hasPlayedMinigame = true;
                currentDialogue = 0;
                playerIn.setPositionAndUpdate(974, 66, 1029);
                IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(playerIn);
                onlyBlock.getAmazonIsland().startMinigame(playerIn, playerIn.world);
                onlyBlock.sync((ServerPlayerEntity)playerIn);
                playerIn.setPositionAndUpdate(974, 66, 1029);
            } else {
                IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(playerIn);
                onlyBlock.setCurrentQuest(new AmazonQuestTwo(playerIn));
            }
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving())
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk",true));
        } else
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle",true));
        }
        return PlayState.CONTINUE;
    }

    private boolean hasNextDialogue()
    {
        return currentDialogue <= DIALOGUE.size() - 1;
    }

    private boolean hasNextFinalDialogue()
    {
        return currentDialogue <= DIALOGUE_FINAL.size() - 1;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<JeffBezosEntity>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
