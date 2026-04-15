package pl.norbit.survivaltweaks.utils.items;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class NexoUtils {

    private NexoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemStack getItem(String id) {
        ItemBuilder itemBuilder = NexoItems.itemFromId(id);

        if (itemBuilder == null) {
            return null;
        }

        return itemBuilder.build();
    }

    public static boolean isItem(ItemStack stack, String id) {
        String idFromItem = NexoItems.idFromItem(stack);

        if (idFromItem == null) {
            return false;
        }

        return idFromItem.equals(id);
    }
}
