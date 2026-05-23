package pl.norbit.survivaltweaks.utils.items;

import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderUtils {

    private ItemsAdderUtils() {}

    public static String getId(ItemStack itemStack){
        if (itemStack == null) {
            return null;
        }
        CustomStack customStack = CustomStack.byItemStack(itemStack);

        if(customStack == null){
            return null;
        }

        return customStack.getId();
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
