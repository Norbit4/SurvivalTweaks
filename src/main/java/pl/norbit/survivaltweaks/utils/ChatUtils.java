package pl.norbit.survivaltweaks.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;


public class ChatUtils {
    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private ChatUtils() {}

    public static Component format(String text, Player player) {

        return MINI_MESSAGE.deserialize(
                PlaceholderUtils.setPlaceholders(text, player)
        );
    }

    public static Component format(String text) {
        return MINI_MESSAGE.deserialize(
                PlaceholderUtils.setPlaceholders(text, null)
        );
    }
}
