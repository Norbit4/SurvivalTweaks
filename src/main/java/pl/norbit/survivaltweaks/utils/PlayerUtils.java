package pl.norbit.survivaltweaks.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import pl.norbit.survivaltweaks.SurvivalTweaks;

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

    public static List<Player> getOnlinePlayers() {
        return new ArrayList<>(SurvivalTweaks.getInstance().getServer().getOnlinePlayers());
    }

    public static void sendActionBar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatUtils.format(message)));
    }
}
