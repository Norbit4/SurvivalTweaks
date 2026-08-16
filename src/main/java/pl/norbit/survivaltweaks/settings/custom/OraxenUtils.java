package pl.norbit.survivaltweaks.settings.custom;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class OraxenUtils {

    private OraxenUtils() {}

    public static ItemStack getItem(String id){
        ItemBuilder stack = OraxenItems.getItemById(id);

        if(stack == null) return null;

        return stack.build();
    }

    protected static String getId(ItemStack stack){
        return OraxenItems.getIdByItem(stack);
    }

    protected static boolean isCustom(ItemStack stack){
        return OraxenItems.exists(stack);
    }

    public static boolean isEqual(ItemStack stack1, String id) {
        ItemBuilder stack = OraxenItems.getItemById(id);

        if(stack == null) return false;

        ItemBuilder stack2 = OraxenItems.getBuilderByItem(stack1);

        if(stack2 == null) return false;

        return stack.equals(stack2);
    }
}
