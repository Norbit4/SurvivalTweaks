package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class SpawnerBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.MINE_SPAWNERS)){
            return;
        }

        Block b = e.getBlock();

        if(b.getType() != Material.SPAWNER){
            return;
        }

        Player p = e.getPlayer();
        ItemStack tool = p.getInventory().getItemInMainHand();

        if (!tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }
        World w = b.getWorld();

        CreatureSpawner spawner = (CreatureSpawner) b.getState();
        EntityType entityType = spawner.getSpawnedType();

        ItemStack spawnerItem = getSpawnerItem(entityType);

        w.dropItemNaturally(b.getLocation(), spawnerItem);
        b.setType(Material.AIR);

        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.MINE_SPAWNERS)){
            return;
        }

        Block b = e.getBlock();

        if(b.getType() != Material.SPAWNER){
            return;
        }

        ItemStack item = e.getItemInHand();
        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();
        EntityType entityType = spawner.getSpawnedType();

        if(entityType == null){
            return;
        }

        CreatureSpawner placedSpawner = (CreatureSpawner) b.getState();
        placedSpawner.setSpawnedType(entityType);
        placedSpawner.update();
    }

    private ItemStack getSpawnerItem(EntityType entityType){
        if(entityType == null){
            return new ItemStack(Material.SPAWNER);
        }

        ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();
        spawner.setSpawnedType(entityType);
        meta.setBlockState(spawner);
        spawnerItem.setItemMeta(meta);
        return spawnerItem;
    }
}
