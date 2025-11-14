package pl.norbit.survivaltweaks.mechanics.listeners.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class BoneMealListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.BLOCK_BONE_MEAL)){
            return;
        }

        Action action = e.getAction();

        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = e.getItem();
        Block clickedBlock = e.getClickedBlock();

        if(item == null){
            return;
        }

        if(clickedBlock == null){
            return;
        }

        if(e.getPlayer().isOp()){
            return;
        }

        if(e.getPlayer().hasPermission("survivaltweaks.bonemeal.bypass")){
            return;
        }

        Material type = clickedBlock.getType();

        if (item.getType() == Material.BONE_MEAL && Config.isBlockedBoneMeal(type)) {
            e.getPlayer().sendMessage(ChatUtils.format(Config.getBlockBoneMealMessage()));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispenserUse(BlockDispenseEvent e) {
        ItemStack item = e.getItem();
        Block block = e.getBlock();

        if (item.getType() == Material.BONE_MEAL && block.getState() instanceof Dispenser) {
            e.setCancelled(true);
        }
    }
}
