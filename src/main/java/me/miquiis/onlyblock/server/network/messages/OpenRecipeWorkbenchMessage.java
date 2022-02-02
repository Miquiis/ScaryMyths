package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.CraftRecipe;
import me.miquiis.onlyblock.common.containers.PortableWorkbenchContainer;
import me.miquiis.onlyblock.common.containers.RecipeWorkbenchContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenRecipeWorkbenchMessage {

    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.crafting");

    private CraftRecipe craftRecipe;

    public OpenRecipeWorkbenchMessage(CraftRecipe craftRecipe) {
        this.craftRecipe = craftRecipe;
    }

    public static void encode(OpenRecipeWorkbenchMessage message, PacketBuffer buffer)
    {
        buffer.writeString(message.craftRecipe.getTitle());
        for (ItemStack itemStack : message.craftRecipe.getAllRows()) {
            buffer.writeItemStack(itemStack);
        }
        buffer.writeItemStack(message.craftRecipe.getResult());
    }

    public static OpenRecipeWorkbenchMessage decode(PacketBuffer buffer)
    {
        return new OpenRecipeWorkbenchMessage(new CraftRecipe(
                buffer.readString(),
                new ItemStack[]{buffer.readItemStack(),buffer.readItemStack(),buffer.readItemStack()},
                new ItemStack[]{buffer.readItemStack(),buffer.readItemStack(),buffer.readItemStack()},
                new ItemStack[]{buffer.readItemStack(),buffer.readItemStack(),buffer.readItemStack()},
                buffer.readItemStack()
        ));
    }

    public static void handle(OpenRecipeWorkbenchMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.getSender().openContainer(new SimpleNamedContainerProvider((id, inventory, player) -> new RecipeWorkbenchContainer(id, inventory, IWorldPosCallable.of(player.getEntityWorld(), null), message.craftRecipe), CONTAINER_NAME));
        context.setPacketHandled(true);
    }
}
