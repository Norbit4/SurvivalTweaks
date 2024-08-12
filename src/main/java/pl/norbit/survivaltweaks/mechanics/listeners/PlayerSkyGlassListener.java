package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.SpyGlassMechanic;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

public class PlayerSkyGlassListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SPYGLASS)){
            return;
        }

        ItemStack item = e.getItem();
        Player p = e.getPlayer();

        if(item == null){
            return;
        }

        Material type = item.getType();
        Action action = e.getAction();

        if (type != Material.SPYGLASS) {
            return;
        }

        if(p.getCooldown(Material.SPYGLASS) > 0){
            return;
        }

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        Entity targetEntity = getEntityLookingAt(p, 80);

        if (targetEntity == null) {
            return;
        }

        if(!(targetEntity instanceof LivingEntity livingEntity)){
            return;
        }

        SpyGlassMechanic.addGlowingEntity(targetEntity, p, 25);

        int distance = (int) p.getLocation().distance(targetEntity.getLocation());

        String message = Config.getSpyglass()
                .replace("{ENTITY}", targetEntity.getName())
                .replace("{HEALTH}", String.valueOf((int) livingEntity.getHealth()))
                .replace("{DISTANCE}", String.valueOf(distance));

        PlayerUtils.sendActionBar(p, message);

        int spyglassCooldown = Config.getSpyglassCooldown();
        p.setCooldown(Material.SPYGLASS, 20 * spyglassCooldown);
    }

    public static Entity getEntityLookingAt(Player p, double maxDistance) {
        Location eyeLocation = p.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult rayTraceResult = p.getWorld().rayTraceEntities(
                eyeLocation, direction, maxDistance, entity -> entity != p);

        return rayTraceResult != null ? rayTraceResult.getHitEntity() : null;
    }
}
