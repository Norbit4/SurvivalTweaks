package pl.norbit.survivaltweaks.settings.custom;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class MythicUtils {

    private MythicUtils() {}

    public static ItemStack getItem(String id){
        Optional<MythicItem> item = MythicBukkit.inst().getItemManager().getItem(id);

        return item
                .map(mythicItem -> BukkitAdapter.adapt(mythicItem.generateItemStack(1)))
                .orElse(null);
    }

    protected static boolean isCustom(ItemStack stack){
        return getId(stack) != null;
    }

    protected static String getId(ItemStack stack){
        return MythicBukkit.inst().getItemManager().getMythicTypeFromItem(stack);
    }

    public static boolean isEqual(ItemStack stack1, String id) {
        String mythicTypeFromItem = getId(stack1);

        return id.equals(mythicTypeFromItem);
    }
}
