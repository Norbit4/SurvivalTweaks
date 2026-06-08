package pl.norbit.survivaltweaks.utils;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.settings.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public final class ItemSignatureUtil {
    private static final NamespacedKey SIGNED_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed");
    private static final NamespacedKey PLAYER_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed_by");
    private static final NamespacedKey DATE_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed_date");
    private static final NamespacedKey NAME_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signed_name");
    private static final NamespacedKey SIGNATURE_LINES_KEY = new NamespacedKey(SurvivalTweaks.getInstance(), "signature_lines");

    private ItemSignatureUtil() {}

    public static void signItem(String signedName, ItemStack item, Player p) {
        if (item == null || item.isEmpty()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        String playerName = p.getName();
        String date = DateUtils.getCurrentDateTime();

        meta.displayName(
                LegacyComponentSerializer.legacySection()
                        .deserialize(ChatUtils.format("&f" + signedName))
        );

        List<String> signatureLore = createSignatureLore(playerName, date);

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

        saveSignatureData(
                pdc,
                playerName,
                date,
                signedName,
                signatureLore.size()
        );

        item.setItemMeta(meta);
    }

    public static void restoreSignature(ItemStack oldItem, ItemStack newItem) {
        if (oldItem == null || newItem == null) {
            return;
        }

        ItemMeta oldMeta = oldItem.getItemMeta();
        ItemMeta newMeta = newItem.getItemMeta();

        if (oldMeta == null || newMeta == null) {
            return;
        }

        PersistentDataContainer oldPdc = oldMeta.getPersistentDataContainer();
        PersistentDataContainer newPdc = newMeta.getPersistentDataContainer();

        if (!oldPdc.has(SIGNED_KEY, PersistentDataType.BYTE)) {
            return;
        }

        String playerName = oldPdc.get(PLAYER_KEY, PersistentDataType.STRING);
        String date = oldPdc.get(DATE_KEY, PersistentDataType.STRING);
        String signedName = oldPdc.get(NAME_KEY, PersistentDataType.STRING);

        if (playerName == null || date == null || signedName == null) {
            return;
        }

        newMeta.displayName(
                LegacyComponentSerializer.legacyAmpersand()
                        .deserialize("&f" + signedName)
        );


        List<String> oldLore = oldMeta.getLore();
        List<String> newLore = newMeta.getLore();

        if (oldLore == null) {
            oldLore = List.of();
        }

        if (newLore == null) {
            newLore = List.of();
        }

        if (!oldLore.equals(newLore)) {
            List<String> signatureLore = createSignatureLore(playerName, date);

            List<String> lore = new ArrayList<>(newLore);
            lore.addAll(signatureLore);

            newMeta.setLore(lore);
            newPdc.set(SIGNATURE_LINES_KEY, PersistentDataType.INTEGER, signatureLore.size());
        }

        newItem.setItemMeta(newMeta);
    }

    private static List<String> createSignatureLore(String player, String date) {
        return ConfigManager.getMessagesConfig()
                .getItemSignatureLore()
                .stream()
                .map(line -> replaceName(line, player, date))
                .toList();
    }

    private static void saveSignatureData(
            PersistentDataContainer pdc,
            String playerName,
            String date,
            String signedNme,
            int signatureLines
    ) {
        pdc.set(SIGNED_KEY, PersistentDataType.BYTE, (byte) 1);
        pdc.set(PLAYER_KEY, PersistentDataType.STRING, playerName);
        pdc.set(DATE_KEY, PersistentDataType.STRING, date);
        pdc.set(NAME_KEY, PersistentDataType.STRING, signedNme);
        pdc.set(SIGNATURE_LINES_KEY, PersistentDataType.INTEGER, signatureLines);
    }

    private static String replaceName(String line, String name, String date) {
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
}