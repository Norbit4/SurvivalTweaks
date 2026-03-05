package pl.norbit.survivaltweaks.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemsUtils {
    private ItemsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isValidItem(ItemStack is, String id) {
        ItemType itemType = getItemType(id);

        if(itemType == ItemType.NEXO){
            return NexoUtils.isItem(is, id);
        }

        if(itemType == ItemType.IA){
            return NexoUtils.isItem(is, id);
        }

        try {
            Material mat = Material.matchMaterial(id);

            if(mat == null){
                return false;
            }

            return is.getType() == mat;
        }catch (Exception e){
            return false;
        }
    }

    private static ItemType getItemType(String id) {
        String[] split = id.split(":");

        if(split.length != 2){
            return ItemType.MINECRAFT;
        }

        String type = split[0].toUpperCase();

        return switch (type) {
            case "IA", "ITEMSADDER" -> ItemType.IA;
            case "NEXO" -> ItemType.NEXO;
            default -> ItemType.MINECRAFT;
        };

    }
}
