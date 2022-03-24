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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
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

public class ElonMuskEntity extends MonsterEntity implements IAnimatable {

    private static final List<String> DIALOGUE = new ArrayList<>(Arrays.asList(
            "&6<Elon Musk> &eThe Earth is being destroyed by meteors.",
            "&6<Elon Musk> &eI need you to save it by shooting them down.",
            "&6<Elon Musk> &eEach meteor shot will give you &l$70M&e.",
            "&6<Elon Musk> &eGodspeed!"
    ));

    private int currentDialogue;
    private boolean hasPlayedMinigame;

    private AnimationFactory factory = new AnimationFactory(this);

    public ElonMuskEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.setHealth(1);
        this.currentDialogue = 0;
        this.hasPlayedMinigame = false;
    }

    public ElonMuskEntity(World worldIn) {
        super(EntityRegister.ELON_MUSK.get(), worldIn);
        this.setHealth(1);
        this.currentDialogue = 0;
        this.hasPlayedMinigame = false;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5).createMutableAttribute(Attributes.FOLLOW_RANGE, 50).createMutableAttribute(Attributes.ARMOR, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F);
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

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!playerIn.world.isRemote && hand == Hand.MAIN_HAND)
        {
            if (this.hasNextDialogue() && !hasPlayedMinigame) {
                playerIn.sendStatusMessage(new StringTextComponent(DIALOGUE.get(currentDialogue++).replace("&", "\u00A7")), false);
                if (!this.hasNextDialogue())
                {
                    hasPlayedMinigame = true;
                    currentDialogue = 0;

                    playerIn.setPositionAndUpdate(-1000, 300, -1000);
                    SpaceshipEntity spaceship = new SpaceshipEntity(world);
                    spaceship.setPosition(-1000, 300, -1000);
                    world.addEntity(spaceship);
                    playerIn.startRiding(spaceship);

                    //playerIn.setPositionAndUpdate(974, 66, 1029);
                    IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(playerIn);
                    onlyBlock.getBillionaireIsland().startMinigame(playerIn);
                }
                return super.getEntityInteractionResult(playerIn, hand);
            }
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    private boolean hasNextDialogue()
    {
        return currentDialogue <= DIALOGUE.size() - 1;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<ElonMuskEntity>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
