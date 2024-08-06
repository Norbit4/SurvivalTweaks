package pl.norbit.survivaltweaks.mechanics.listeners;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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

        if(item == null){
            return;
        }

        Material type = item.getType();
        Action action = e.getAction();

        if (type != Material.SPYGLASS) {
            return;
        }

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        Player p = e.getPlayer();
        Entity targetEntity = getEntityLookingAt(p, 80);

        if (targetEntity == null) {
            return;
        }

        if(!(targetEntity instanceof LivingEntity)){
            return;
        }

        SpyGlassMechanic.addGlowingEntity(targetEntity, p, 25);

        int distance = (int) p.getLocation().distance(targetEntity.getLocation());

        String message = Config.getSpyglass()
                .replace("{ENTITY}", targetEntity.getName())
                .replace("{DISTANCE}", String.valueOf(distance));

        PlayerUtils.sendActionBar(p, message);
    }

    public static Entity getEntityLookingAt(Player p, double maxDistance) {
        Location eyeLocation = p.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        RayTraceResult rayTraceResult = p.getWorld().rayTraceEntities(
                eyeLocation, direction, maxDistance, entity -> entity != p);

        return rayTraceResult != null ? rayTraceResult.getHitEntity() : null;
    }
}
