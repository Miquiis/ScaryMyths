package me.miquiis.onlyblock.common.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class ItemUtils {

    public static void addLores(ItemStack itemStack, String... lores)
    {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        CompoundNBT display;
        ListNBT lore;
        if (nbt.get("display") != null)
        {
            display = (CompoundNBT) nbt.get("display");
            if (display.get("Lore") != null) {
                lore = (ListNBT) display.get("Lore");
            } else {
                lore = new ListNBT();
            }
        } else
        {
            lore = new ListNBT();
            display = new CompoundNBT();
        }

        for (String loreString : lores)
        {
            CompoundNBT tagRaw = new CompoundNBT();
            tagRaw.putString("tag", "{'text': '[tag]', 'italic': false}".replace("[tag]", loreString));
            lore.add(tagRaw.get("tag"));
            display.put("Lore", lore);
            nbt.put("display", display);
            itemStack.setTag(nbt);
        }

    }

}
