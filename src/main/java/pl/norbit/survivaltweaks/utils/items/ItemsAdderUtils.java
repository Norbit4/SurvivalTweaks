package pl.norbit.survivaltweaks.utils.items;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderUtils {

    private ItemsAdderUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemStack getItem(String id) {
        CustomStack instance = CustomStack.getInstance(id);

        if (instance == null) {
            return null;
        }

        return instance.getItemStack();
    }

    public static boolean isItem(ItemStack stack, String id) {
        CustomStack customStack = CustomStack.byItemStack(stack);

        if (customStack == null) {
            return false;
        }

        return customStack.getId().equals(id);
    }
}
