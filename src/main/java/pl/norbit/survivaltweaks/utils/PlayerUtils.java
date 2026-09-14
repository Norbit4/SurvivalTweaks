package pl.norbit.survivaltweaks.utils;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerUtils {
    private PlayerUtils() {}

    public static Optional<Player> getPlayer(UUID playerUUID) {
        return Optional.ofNullable(SurvivalTweaks.getInstance().getServer().getPlayer(playerUUID));
    }

    public static Player getPlayerByName(String playerName) {
        return SurvivalTweaks.getInstance().getServer().getPlayer(playerName);
    }

    public static boolean hasPermission(Player p, String permission) {
        if(p.isOp()){
            return true;
        }

        return p.hasPermission(permission);
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
        p.sendActionBar(ChatUtils.format(message, p));
    }

    public static Entity getEntityLookingAt(Player player, double maxDistance) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection();

        RayTraceResult result = player.getWorld().rayTrace(
                eye,
                direction,
                maxDistance,
                FluidCollisionMode.NEVER,
                true,
                0.1,
                entity -> entity != player
        );

        return result != null ? result.getHitEntity() : null;
    }
}
