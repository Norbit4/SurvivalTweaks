package pl.norbit.survivaltweaks.settings.custom;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class NexoUtils {

    private NexoUtils() {}

    protected static ItemStack getItem(String id){
        ItemBuilder stack = NexoItems.itemFromId(id);

        if(stack == null) return null;

        return stack.build();
    }

    protected static boolean isCustom(ItemStack stack){
        return NexoItems.exists(stack);
    }

    protected static String getId(ItemStack stack){
        return NexoItems.idFromItem(stack);
    }
    protected static boolean isEqual(ItemStack stack1, String id) {
        ItemBuilder stack = NexoItems.itemFromId(id);

        if(stack == null) return false;

        ItemBuilder stack2 = NexoItems.builderFromItem(stack1);

        if(stack2 == null) return false;

        return stack.equals(stack2);
    }
}
