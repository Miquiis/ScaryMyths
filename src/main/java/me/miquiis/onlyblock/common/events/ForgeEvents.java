package me.miquiis.onlyblock.common.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.EffectRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.registries.ParticleRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBlockUse(PlayerInteractEvent event)
    {
        if (event.getHand() != Hand.MAIN_HAND) return;
        if (event.getWorld().isRemote()) return;
        final BlockState block = event.getWorld().getBlockState(event.getPos());
        if (block.getBlock().equals(BlockRegister.XP_BLOCK.get()))
        {
            OnlyBlock.getInstance().getBlockManager().onXPInteractEvent(event);
        }
    }

    @SubscribeEvent
    public static void onPlayerPickupXP(PlayerXpEvent.PickupXp event)
    {
        EffectInstance effectInstance = event.getPlayer().getActivePotionEffect(EffectRegister.XP_BOOST.get());
        if (effectInstance == null) return;
        event.getOrb().xpValue += Math.ceil(event.getOrb().xpValue * 0.25);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getWorld().isRemote()) return;
        final ItemStack usingItem = event.getPlayer().getHeldItemMainhand();

        if (usingItem.getItem() == ItemRegister.XP_AXE.get()) {
            if (event.getState().getBlock() == BlockRegister.ENCHANTED_OAK_PLANKS.get()) {
                event.setCanceled(true);
                event.getWorld().setBlockState(event.getPos(), Blocks.DIRT.getDefaultState(), 12);
                Tree tree = new OakTree();
                tree.attemptGrowTree((ServerWorld) event.getWorld(), ((ServerWorld) event.getWorld()).getChunkProvider().getChunkGenerator(), event.getPos().add(0, 1, 0), Blocks.OAK_SAPLING.getDefaultState(), ((ServerWorld) event.getWorld()).rand);
                event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState(), 12);
                return;
            }

            if (event.getState().getBlock() == Blocks.OAK_LOG) {
                final ArrayList<BlockPos> brokenLogs = new ArrayList<>();
                chopLogs((ServerWorld)event.getWorld(), event.getPos(), event.getState().getBlock(), brokenLogs, true, brokenLogs);
                final BlockPos highestBlock = getHighestBlock(brokenLogs);
                chopLogs((ServerWorld)event.getWorld(), highestBlock, Blocks.OAK_LEAVES, new ArrayList<>(), true, new ArrayList<>());
                final BlockPos midBlock = getMidBlock(brokenLogs);;
                event.getWorld().playSound(null, midBlock, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (event.getWorld().getRandom().nextFloat() - event.getWorld().getRandom().nextFloat()) * 0.2F) * 0.7F);

                for (int i = 0; i < 50; i++)
                {
                    double rx = MathUtils.getRandomMinMax(-0.2, 0.2);
                    double ry = MathUtils.getRandomMinMax(0.5, 1.0);
                    double rz = MathUtils.getRandomMinMax(-0.2, 0.2);
                    ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity((ServerWorld)event.getWorld(), highestBlock.getX() + 0.5, highestBlock.getY() + 0.5, highestBlock.getZ() + 0.5, 3);
                    experienceOrbEntity.setVelocity(rx, ry, rz);
                    event.getWorld().addEntity(experienceOrbEntity);
                }
            }
        }
    }

    private static BlockPos getMidBlock(ArrayList<BlockPos> list)
    {
        final BlockPos highestBlock = getHighestBlock(list);
        final BlockPos lowestBlock = getLowestBlock(list);

        return lowestBlock.add(0, (highestBlock.getY() - lowestBlock.getY()) / 2, 0);
    }

    private static BlockPos getLowestBlock(ArrayList<BlockPos> list)
    {
        BlockPos currentHighest = null;

        for (BlockPos currentPos : list)
        {
            if (currentHighest == null)
            {
                currentHighest = currentPos;
                continue;
            }

            if (currentHighest.getY() > currentPos.getY())
            {
                currentHighest = currentPos;
            }
        }

        return currentHighest;
    }

    private static BlockPos getHighestBlock(ArrayList<BlockPos> list)
    {
        BlockPos currentHighest = null;

        for (BlockPos currentPos : list)
        {
            if (currentHighest == null)
            {
                currentHighest = currentPos;
                continue;
            }

            if (currentHighest.getY() < currentPos.getY())
            {
                currentHighest = currentPos;
            }
        }

        return currentHighest;
    }

    private static void chopLogs(World world, BlockPos pos, Block block, ArrayList<BlockPos> list, boolean drop, ArrayList<BlockPos> listOfBlocks) {
        if (world.getBlockState(pos.north()).getBlock() == block) {
            list.add(pos.north());
        }
        if (world.getBlockState(pos.north().east()).getBlock() == block) {
            list.add(pos.north().east());
        }
        if (world.getBlockState(pos.north().west()).getBlock() == block) {
            list.add(pos.north().west());
        }
        if (world.getBlockState(pos.north().east().down()).getBlock() == block) {
            list.add(pos.north().east().down());
        }
        if (world.getBlockState(pos.north().west().down()).getBlock() == block) {
            list.add(pos.north().west().down());
        }
        if (world.getBlockState(pos.south()).getBlock() == block) {
            list.add(pos.south());
        }
        if (world.getBlockState(pos.south().east()).getBlock() == block) {
            list.add(pos.south().east());
        }
        if (world.getBlockState(pos.south().west()).getBlock() == block) {
            list.add(pos.south().west());
        }
        if (world.getBlockState(pos.south().east().down()).getBlock() == block) {
            list.add(pos.south().east().down());
        }
        if (world.getBlockState(pos.south().west().down()).getBlock() == block) {
            list.add(pos.south().west().down());
        }
        if (world.getBlockState(pos.east()).getBlock() == block) {
            list.add(pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block) {
            list.add(pos.west());
        }
        if (world.getBlockState(pos.down()).getBlock() == block) {
            list.add(pos.down());
        }
        if (world.getBlockState(pos.down().north()).getBlock() == block) {
            list.add(pos.down().north());
        }
        if (world.getBlockState(pos.down().south()).getBlock() == block) {
            list.add(pos.down().south());
        }
        if (world.getBlockState(pos.down().east()).getBlock() == block) {
            list.add(pos.down().east());
        }
        if (world.getBlockState(pos.down().west()).getBlock() == block) {
            list.add(pos.down().west());
        }
        if (world.getBlockState(pos.north().east().up()).getBlock() == block) {
            list.add(pos.north().east().up());
        }
        if (world.getBlockState(pos.north().west().up()).getBlock() == block) {
            list.add(pos.north().west().up());
        }
        if (world.getBlockState(pos.south().east().up()).getBlock() == block) {
            list.add(pos.south().east().up());
        }
        if (world.getBlockState(pos.south().west().up()).getBlock() == block) {
            list.add(pos.south().west().up());
        }
        if (world.getBlockState(pos.up()).getBlock() == block) {
            list.add(pos.up());
        }
        if (world.getBlockState(pos.up().north()).getBlock() == block) {
            list.add(pos.up().north());
        }
        if (world.getBlockState(pos.up().south()).getBlock() == block) {
            list.add(pos.up().south());
        }
        if (world.getBlockState(pos.up().east()).getBlock() == block) {
            list.add(pos.up().east());
        }
        if (world.getBlockState(pos.up().west()).getBlock() == block) {
            list.add(pos.up().west());
        }
        if (list.size() <= 0 || list == null) {
            list = null;
            return;
        }

        listOfBlocks.addAll(list);

        for (int i = 0; i < list.size(); i++) {
            BlockPos pos1 = list.get(i);
            destroyBlock(world, pos1, pos, drop);
            chopLogs(world, list.get(i), block, new ArrayList<BlockPos>(), drop, listOfBlocks);
        }
    }

    public static boolean destroyBlock(World world, BlockPos pos, BlockPos posToDropItems, boolean drop) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
            ((ServerWorld)world).spawnParticle(ParticleRegister.EXP_EXPLOSION.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0, 0, 0, 0);
            if (drop) {
                if (blockstate.getBlock() == Blocks.OAK_LEAVES){
                    if (MathUtils.chance(3)) {
                        spawnAsEntity(world, pos, new ItemStack(ItemRegister.XP_APPLE.get(), 1));
                    }
                }
                Block.spawnDrops(blockstate, world, posToDropItems, tileentity);
            }
            return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
    }
}
