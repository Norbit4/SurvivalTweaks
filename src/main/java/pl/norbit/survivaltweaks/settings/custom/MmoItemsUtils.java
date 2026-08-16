package pl.norbit.survivaltweaks.settings.custom;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.inventory.ItemStack;

public class MmoItemsUtils {
    private MmoItemsUtils() {}

    protected static ItemStack getItem(String id){
        MMOItem mmoitem = getMmoItem(id);

        if(mmoitem == null){
            return null;
        }

        return mmoitem.newBuilder().build();
    }

    protected static boolean isCustom(ItemStack stack){
        String id = MMOItems.getID(stack);

        if (id == null) {
            return false;
        }

        return !id.isEmpty();
    }

    protected static String getId(ItemStack stack){
        Type type = MMOItems.getType(stack);

        if(type == null){
            return null;
        }

        String stackId = MMOItems.getID(stack);

        if(stackId == null){
            return null;
        }
        return type + ":" + stackId;
    }

    private static MMOItem getMmoItem(String id){
        String[] split = id.split(":");

        if (split.length < 2){
            return null;
        }
        Type type = MMOItems.plugin.getTypes().get(split[0]);

        if(type == null){
            return null;
        }

        return MMOItems.plugin.getMMOItem(type, split[1]);
    }

    protected static boolean isEqual(ItemStack stack, String id) {
        String[] split = id.split(":");

        if (split.length < 2){
            return false;
        }

        String configType = split[0];
        String configId = split[1];

        Type type = MMOItems.getType(stack);

        if(type == null){
            return false;
        }

        String stackId = MMOItems.getID(stack);
        String stackType = type.getId();

        if(!configType.equals(stackType)){
            return false;
        }

        return configId.equals(stackId);
    }
}
