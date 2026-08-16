package pl.norbit.survivaltweaks.mechanics.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jspecify.annotations.Nullable;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.custom.CustomItemsUtils;
import pl.norbit.survivaltweaks.utils.TaskUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GrindstoneListener implements Listener {
    private final Map<UUID, ExtractionData> extractions = new HashMap<>();

    private record ExtractionData(ItemStack item, Enchantment enchantment) {}
    private record PrepareResult(ItemStack book, Enchantment enchantment) {}

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ENCHANTMENT_EXTRACTION)){
            return;
        }
        if (!(e.getInventory() instanceof GrindstoneInventory)) {
            return;
        }
        extractions.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPrepare(PrepareGrindstoneEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ENCHANTMENT_EXTRACTION)){
            return;
        }
        GrindstoneInventory inv = e.getInventory();

        ItemStack upper = inv.getUpperItem();
        ItemStack lower = inv.getLowerItem();

        if (!isRequiredItem(upper) && !isRequiredItem(lower)) {
            return;
        }

        ItemStack item = getEnchantedItem(inv);
        if (item == null) {
            e.setResult(null);
            return;
        }

        PrepareResult result = prepareBook(item);
        e.setResult(result.book());

        Player p = (Player) e.getView().getPlayer();
        extractions.put(
                p.getUniqueId(),
                new ExtractionData(item.clone(), result.enchantment())
        );
    }

    // Allows recipe item to be placed into the input slots
    // by overriding the default Grindstone behavior.
    @EventHandler
    public void onClickSlot(InventoryClickEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ENCHANTMENT_EXTRACTION)){
            return;
        }
        if (!(e.getInventory() instanceof GrindstoneInventory)) return;
        if (e.getRawSlot() != 0 && e.getRawSlot() != 1) return;

        ItemStack cursor = e.getCursor();

        if(!isRequiredItem(cursor)){
            return;
        }

        e.setCancelled(true);
        ItemStack slot = e.getCurrentItem();

        e.setCurrentItem(cursor);
        e.getWhoClicked().setItemOnCursor(slot);
    }


    // Handles enchantment extraction and applies the success/failure logic.
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ENCHANTMENT_EXTRACTION)){
            return;
        }
        if (!(e.getInventory() instanceof GrindstoneInventory inv)) return;
        if (e.getRawSlot() != 2) return;

        ItemStack currentItem = e.getCurrentItem();

        if(currentItem != null && currentItem.getType() != Material.ENCHANTED_BOOK){
            return;
        }
        Player p = (Player) e.getWhoClicked();

        ItemStack cursor = p.getItemOnCursor();

        if (!cursor.isEmpty()) {
            e.setCancelled(true);
            return;
        }

        ExtractionData data = extractions.remove(p.getUniqueId());

        if (data == null) {
            e.setCancelled(true);
            return;
        }

        ItemStack item = data.item();
        Enchantment enchant = data.enchantment();

        Location loc = p.getLocation();

        if (ThreadLocalRandom.current().nextDouble()
                >= ConfigManager.getMechanicsConfig().getEnchantmentExtractionChance()) {
            // Extraction failed
            e.setCancelled(true);
            p.setItemOnCursor(null);
            p.playSound(loc, Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        } else {
            // Extraction success
            p.setItemOnCursor(getBook(e));
            p.playSound(loc, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
        }

        item.removeEnchantment(enchant);
        restoreInventory(inv, item);
    }

    private void restoreInventory(GrindstoneInventory inv, ItemStack item) {
        boolean itemInUpper = isEnchantable(inv.getUpperItem());

        ItemStack catalyst = null;

        if (isRequiredItem(inv.getUpperItem())) {
            catalyst = inv.getUpperItem().clone();
        } else if (isRequiredItem(inv.getLowerItem())) {
            catalyst = inv.getLowerItem().clone();
        }

        if (catalyst == null) {
            return;
        }

        if (catalyst.getAmount() == 1) {
            catalyst = null;
        } else {
            catalyst.setAmount(catalyst.getAmount() - 1);
        }

        final ItemStack finalCatalyst = catalyst;
        // Clear to prevent item duplication.
        inv.clear();

        TaskUtils.syncLater(() -> {
            inv.clear();

            if (itemInUpper) {
                inv.setUpperItem(item);
                inv.setLowerItem(finalCatalyst);
            } else {
                inv.setLowerItem(item);
                inv.setUpperItem(finalCatalyst);
            }
        }, 1L);
    }

    private boolean isRequiredItem(ItemStack item) {
        return CustomItemsUtils.isEqual(
                ConfigManager.getMechanicsConfig().getEnchantmentExtractionItem(),
                item
        );
    }

    private @Nullable ItemStack getEnchantedItem(GrindstoneInventory inv) {
        ItemStack upper = inv.getUpperItem();
        ItemStack lower = inv.getLowerItem();

        if (isEnchantable(upper))
            return upper;

        if (isEnchantable(lower))
            return lower;

        return null;
    }

    private boolean isEnchantable(ItemStack item) {
        return item != null && !item.getEnchantments().isEmpty();
    }

    private PrepareResult prepareBook(ItemStack item) {
        List<Map.Entry<Enchantment, Integer>> enchants =
                new ArrayList<>(item.getEnchantments().entrySet());

        Map.Entry<Enchantment, Integer> random =
                enchants.get(ThreadLocalRandom.current().nextInt(enchants.size()));

        ItemStack book = createBook(random);

        return new PrepareResult(book, random.getKey());
    }

    private ItemStack createBook(Map.Entry<Enchantment, Integer> enchant) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchant.getKey(), enchant.getValue(), true);

        meta.lore(List.of(
                Component.empty(),
                LegacyComponentSerializer.legacyAmpersand()
                        .deserialize(ConfigManager.getMessagesConfig().getEnchantmentExtractionBookLore())
        ));

        book.setItemMeta(meta);

        return book;
    }

    private static @Nullable ItemStack getBook(InventoryClickEvent e) {
        ItemStack book = e.getCurrentItem();

        if (book != null && book.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();

            if (meta.hasLore()) {
                List<Component> lore = new ArrayList<>(Objects.requireNonNull(meta.lore()));

                for (int i = 0; i < 2 && !lore.isEmpty(); i++) {
                    lore.removeLast();
                }

                meta.lore(lore);
                book.setItemMeta(meta);
            }
        }
        return book;
    }
}
