package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

import java.util.List;

import static pl.norbit.survivaltweaks.utils.TaskUtils.sync;

public class CampfireMechanic {

    private CampfireMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void applyEffect(Player p, List<Block> blocks) {
        if(MechanicsLoader.isDisabled(Mechanic.CAMPFIRE)){
            return;
        }

        if(containsLitMaterial(Material.CAMPFIRE, blocks)){
            sync(() -> applyEffect(p, PotionEffectType.REGENERATION));
        }

        if(containsLitMaterial(Material.SOUL_CAMPFIRE, blocks)){
            sync(() -> applyEffect(p, PotionEffectType.FIRE_RESISTANCE));
        }
    }

    private static void applyEffect(Player player, PotionEffectType potionEffectType) {
        player.addPotionEffect(new PotionEffect(potionEffectType, 50, 0));
    }

    private static boolean containsLitMaterial(Material mat, List<Block> blocks) {
        List<Block> campfires = blocks
                .stream()
                .filter(b -> b.getType() == mat)
                .toList();

        if(campfires.isEmpty()){
            return false;
        }

        return campfires.stream()
                .map(b -> (Campfire) b.getBlockData())
                .anyMatch(Lightable::isLit);
    }
}
