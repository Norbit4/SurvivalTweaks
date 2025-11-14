package pl.norbit.survivaltweaks.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.settings.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerUtils {
    private PlayerUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Optional<Player> getPlayer(UUID playerUUID) {
        return Optional.ofNullable(SurvivalTweaks.getInstance().getServer().getPlayer(playerUUID));
    }

    public static Player getPlayerByName(String playerName) {
        return SurvivalTweaks.getInstance().getServer().getPlayer(playerName);
    }

    public static ItemStack getCustomSkull(OfflinePlayer offlinePlayer) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        skullMeta.setOwningPlayer(offlinePlayer);
        head.setItemMeta(skullMeta);
        return head;
    }

    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(SurvivalTweaks.getInstance().getServer().getOnlinePlayers());
    }

    public static void sendActionBar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.format(message, p)));
    }

    public static Entity getEntityLookingAt(Player p, double maxDist) {
        Location eyeLoc = p.getEyeLocation();
        Vector direction = eyeLoc.getDirection();
        World w = p.getWorld();



        RayTraceResult rayTraceResult = w.rayTraceEntities(eyeLoc, direction, maxDist, entity -> entity != p);

        if (rayTraceResult != null) {
            Entity hitEntity = rayTraceResult.getHitEntity();

            if (hitEntity == null) {
                return null;
            }

            Location entityLoc = hitEntity.getLocation();
            RayTraceResult bTrace = w.rayTraceBlocks(eyeLoc, direction, entityLoc.distance(eyeLoc));

            if (bTrace != null && bTrace.getHitBlock() != null) {
                return null;
            }
            return hitEntity;
        }

        return null;
    }
}
