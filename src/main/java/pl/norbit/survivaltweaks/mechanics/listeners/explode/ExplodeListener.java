package pl.norbit.survivaltweaks.mechanics.listeners.explode;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class ExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(ConfigManager.getMechanicsConfig().isGriefProtectionCrystalEnabled()){
            if (e.getEntity() instanceof EnderCrystal) {
                e.blockList().clear();
            }
        }

        if(ConfigManager.getMechanicsConfig().isGriefProtectionTntEnabled()){
            if (e.getEntity() instanceof TNTPrimed) {
                e.blockList().clear();
            }
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(!ConfigManager.getMechanicsConfig().isGriefProtectionAnchorEnabled()){
            return;
        }

        Material type = e.getExplodedBlockState().getType();


        if (type == Material.RESPAWN_ANCHOR) {
            e.blockList().clear();
        }
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(!ConfigManager.getMechanicsConfig().isGriefProtectionAnchorEnabled()){
            return;
        }

        BlockState damagerBlockState = e.getDamagerBlockState();

        if(damagerBlockState == null){
            return;
        }

        Material type = damagerBlockState.getType();

        if (type == Material.RESPAWN_ANCHOR) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(ConfigManager.getMechanicsConfig().isGriefProtectionCrystalEnabled()){
            if (e.getDamager() instanceof EnderCrystal){
                e.setCancelled(true);
            }
        }

        if(ConfigManager.getMechanicsConfig().isGriefProtectionTntEnabled()){
            if (e.getDamager() instanceof TNTPrimed) {
                e.setCancelled(true);
            }
        }
    }
}
