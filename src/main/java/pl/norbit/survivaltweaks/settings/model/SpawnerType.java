package pl.norbit.survivaltweaks.settings.model;

import lombok.Data;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

import java.util.List;
import java.util.Set;

@Data
public class SpawnerType {
    private boolean silkTouch;
    private double chance;
    private List<String> tools;
    private Set<String> worlds;

    public boolean isCorrectWorld(String world) {
        if (worlds.contains("ALL")) {
            return true;
        }

        return worlds.contains(world);
    }

    public boolean isCorrectTool(ItemStack itemStack){
        if(silkTouch && !itemStack.containsEnchantment(Enchantment.SILK_TOUCH)){
            return false;
        }

        for (String toolId : tools) {
            if(ItemsUtils.isValidItem(itemStack, toolId)){
                return true;
            }
        }

        return false;
    }
}
