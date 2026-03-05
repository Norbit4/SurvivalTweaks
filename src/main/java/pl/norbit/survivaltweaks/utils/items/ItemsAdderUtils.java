package pl.norbit.survivaltweaks.utils.items;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderUtils {

    private ItemsAdderUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isItem(ItemStack stack, String id) {
        CustomStack customStack = CustomStack.byItemStack(stack);

        if (customStack == null) {
            return false;
        }

        return customStack.getId().equals(id);
    }
}
