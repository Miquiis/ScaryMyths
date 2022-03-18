package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.GoldenHelicopterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GoldenHelicopterItem extends Item implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public GoldenHelicopterItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote)
        {
            GoldenHelicopterEntity spaceship = new GoldenHelicopterEntity(context.getWorld());
            spaceship.setPosition(context.getHitVec().getX(), context.getHitVec().getY(), context.getHitVec().getZ());
            context.getWorld().addEntity(spaceship);
            context.getItem().shrink(1);
        }
        return super.onItemUse(context);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, event -> {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
