package pl.norbit.survivaltweaks.utils.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class ItemsUtils {
    private ItemsUtils() {}

    public static String getId(ItemStack itemStack){
        if(SurvivalTweaks.isNexoEnabled()){
            String id = NexoUtils.getId(itemStack);

            if(id != null){
                return id;
            }
        } else if (SurvivalTweaks.isItemsAdderEnabled()) {
            String id = ItemsAdderUtils.getId(itemStack);

            if(id != null){
                return id;
            }
        }

        return itemStack.getType().toString().toUpperCase();
    }


    public static ItemStack getItem(String id){
        ItemResult result = getItemType(id);

        ItemType itemType = result.itemType();
        String finalId = result.finalId();

        if(itemType == ItemType.NEXO){
            if(!SurvivalTweaks.isNexoEnabled()){
                return null;
            }
            return NexoUtils.getItem(finalId);
        }

        if(itemType == ItemType.IA){
            if(!SurvivalTweaks.isItemsAdderEnabled()){
                return null;
            }
            return ItemsAdderUtils.getItem(finalId);
        }

        try {
            Material mat = Material.matchMaterial(finalId);

            if(mat == null){
                return null;
            }

            return new ItemStack(mat);
        }catch (Exception e){
            return null;
        }
    }

    public static boolean isValidItem(ItemStack is, String id) {
        ItemResult result = getItemType(id);

        ItemType itemType = result.itemType();
        String finalId = result.finalId();

        if(itemType == ItemType.NEXO){
            if(!SurvivalTweaks.isNexoEnabled()){
                return false;
            }
            return NexoUtils.isItem(is, finalId);
        }

        if(itemType == ItemType.IA){
            if(!SurvivalTweaks.isItemsAdderEnabled()){
                return false;
            }
            return ItemsAdderUtils.isItem(is, finalId);
        }

        try {
            Material mat = Material.matchMaterial(finalId);

            if(mat == null){
                return false;
            }

            return is.getType() == mat;
        }catch (Exception e){
            return false;
        }
    }

    private static ItemResult getItemType(String id) {
        String[] split = id.split(":");

        if(split.length != 2){
            return new ItemResult(ItemType.MINECRAFT, id.toUpperCase());
        }

        String type = split[0].toUpperCase();
        String finalId = split[1];

        return switch (type) {
            case "IA", "ITEMSADDER" -> new ItemResult(ItemType.IA, finalId);
            case "NEXO" -> new ItemResult(ItemType.NEXO, finalId);
            default -> new ItemResult(ItemType.MINECRAFT, finalId.toUpperCase());
        };

    }

    private record ItemResult(ItemType itemType, String finalId) {}
}
