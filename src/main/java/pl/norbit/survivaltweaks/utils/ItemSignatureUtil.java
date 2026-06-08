package pl.norbit.survivaltweaks.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MessagesConfig;

import java.util.ArrayList;
import java.util.List;

public final class ItemSignatureUtil {
    private static final NamespacedKey SIGNED_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed");
    private static final NamespacedKey PLAYER_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed_by");
    private static final NamespacedKey DATE_KEY= new NamespacedKey(SurvivalTweaks.getInstance(), "signed_date");
    private static final NamespacedKey SIGNATURE_LINES_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signature_lines");

    private ItemSignatureUtil() {}

    public static void signItem(String name, ItemStack item, Player p) {
        if (item == null || item.isEmpty()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        MessagesConfig messagesConfig = ConfigManager.getMessagesConfig();

        String playerName = p.getName();
        String date = DateUtils.getCurrentDateTime();

        meta.setDisplayName(ChatUtils.format("&f" + name));

        List<String> signatureLore = messagesConfig.getItemSignatureLore()
                .stream()
                .map(line -> replaceName(line, playerName, date))
                .toList();

        List<String> lore = meta.hasLore()
                ? new ArrayList<>(meta.getLore())
                : new ArrayList<>();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        if (pdc.has(SIGNATURE_LINES_KEY, PersistentDataType.INTEGER)) {
            Integer previousLines = pdc.get(SIGNATURE_LINES_KEY, PersistentDataType.INTEGER);

            if (previousLines != null
                    && previousLines > 0
                    && lore.size() >= previousLines) {

                lore.subList(
                        lore.size() - previousLines,
                        lore.size()
                ).clear();
            }
        }

        lore.addAll(signatureLore);
        meta.setLore(lore);

        pdc.set(SIGNED_KEY, PersistentDataType.BYTE, (byte) 1);
        pdc.set(PLAYER_KEY, PersistentDataType.STRING, playerName);
        pdc.set(DATE_KEY, PersistentDataType.STRING, date);
        pdc.set(SIGNATURE_LINES_KEY, PersistentDataType.INTEGER, signatureLore.size());

        item.setItemMeta(meta);
    }

    private static String replaceName(String line, String name, String date){
        return ChatUtils.format(line
                .replace("{player}", name)
                .replace("{date}", date));
    }

    public static boolean isNotSigned(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return true;
        }

        return !meta.getPersistentDataContainer().has(SIGNED_KEY, PersistentDataType.BYTE);
    }

    public static String getSigner(ItemStack item) {
        if (isNotSigned(item)) {
            return null;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .get(PLAYER_KEY, PersistentDataType.STRING);
    }

    public static String getSignatureDate(ItemStack item) {
        if (isNotSigned(item)) {
            return null;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .get(DATE_KEY, PersistentDataType.STRING);
    }
}