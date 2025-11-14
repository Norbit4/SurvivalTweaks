package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class PlayerFrameInteractListener implements Listener {

    @EventHandler
    public void onItemFrameDamage(EntityDamageByEntityEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.INVISIBLE_ITEM_FRAMES)){
            return;
        }

        if (e.getEntity() instanceof ItemFrame frame) {
            if (frame.getItem().getType().isAir()) {
                frame.setVisible(true);
            }
        }
    }

    @EventHandler
    public void onItemFrameBreak(HangingBreakByEntityEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.INVISIBLE_ITEM_FRAMES)){
            return;
        }

        if (e.getEntity().getType() == EntityType.ITEM_FRAME) {
            ItemFrame frame = (ItemFrame) e.getEntity();
            if (frame.getItem().getType().isAir()) {
                frame.setVisible(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.INVISIBLE_ITEM_FRAMES)){
            return;
        }

        if (!(e.getRightClicked() instanceof ItemFrame frame)){
            return;
        }
        if (!e.getPlayer().isSneaking()){
            return;
        }

        frame.setVisible(!frame.isVisible());
        e.setCancelled(true);
    }
}
