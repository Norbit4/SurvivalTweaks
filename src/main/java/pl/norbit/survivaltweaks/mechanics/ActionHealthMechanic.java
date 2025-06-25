package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import pl.norbit.survivaltweaks.hooks.SuperVanish;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import static pl.norbit.survivaltweaks.utils.TaskUtils.sync;

public class ActionHealthMechanic {

    private ActionHealthMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
        if(MechanicsLoader.isDisabled(Mechanic.ENTITY_HP)) {
            return;
        }

        Material mainType = itemInMainHand.getType();
        Material offType = itemInOffHand.getType();

        if((mainType == Material.COMPASS || offType == Material.COMPASS
                || mainType == Material.CLOCK || offType == Material.CLOCK
                || mainType == Material.SPYGLASS || offType == Material.SPYGLASS
                || mainType == Material.RECOVERY_COMPASS || offType == Material.RECOVERY_COMPASS)){
            return;
        }

        sync(() ->{
            Entity targetEntity = PlayerUtils.getEntityLookingAt(p, 4);

            if(targetEntity == null){
                return;
            }

            if(!(targetEntity instanceof LivingEntity livingEntity)){
                return;
            }

            if(targetEntity instanceof ArmorStand){
                return;
            }

            //check npc
            if(targetEntity instanceof Player player){
                String playerName = player.getName();

                if(player.isInvisible()){
                    return;
                }

                if (!SuperVanish.canSeeVanished(p)){ // IF SuperVanish is Vanished
                    return;
                }

                if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                    return;
                }

                if(PlayerUtils.getPlayerByName(playerName) == null){
                    return;
                }
            }

            //translate entity name
            String customName = targetEntity.getCustomName();

            String displayName = customName != null
                    ? customName
                    : Config.getMobNameOrDefault(targetEntity.getType(), targetEntity.getName());




            String message = Config.getEntityHpDisplay()
                    .replace("{ENTITY}", displayName)
                    .replace("{HEALTH}", Integer.toString((int) livingEntity.getHealth()));

            PlayerUtils.sendActionBar(p, message);
        });
    }
}
