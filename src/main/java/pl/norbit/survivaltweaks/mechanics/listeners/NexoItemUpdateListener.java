package pl.norbit.survivaltweaks.mechanics.listeners;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.api.events.NexoItemsLoadedEvent;
import com.nexomc.nexo.items.UpdateCallback;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import pl.norbit.survivaltweaks.utils.ItemSignatureUtil;

public class NexoItemUpdateListener implements Listener {

    @EventHandler
    public void onItemUpdate(NexoItemsLoadedEvent e){
        NexoItems.registerUpdateCallback(
                Key.key("survivaltweaks", "signature"), new UpdateCallback() {
                    @Override
                    public @NonNull ItemStack postUpdate(String itemId, ItemStack itemStack, ItemStack preUpdateItemStack) {
                        // Restore signature lost when Nexo regenerates item name and lore.
                        ItemSignatureUtil.restoreSignature(preUpdateItemStack, itemStack);
                        return itemStack;
                    }
                }
        );
    }
}
