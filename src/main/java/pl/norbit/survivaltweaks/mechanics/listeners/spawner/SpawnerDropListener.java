package pl.norbit.survivaltweaks.mechanics.listeners.spawner;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.utils.RandomUtils;
import pl.norbit.survivaltweaks.settings.model.SpawnerType;

public class SpawnerDropListener implements Listener {
    private final NamespacedKey spawnerKey = new NamespacedKey(SurvivalTweaks.getInstance(), "spawner");

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.isCancelled()) {
            return;
        }

        if(MechanicsLoader.isDisabled(Mechanic.MINE_SPAWNERS)){
            return;
        }

        Block b = e.getBlock();
        BlockState state = b.getState();

        if (!(state instanceof CreatureSpawner spawner)) {
            return;
        }
        Player p = e.getPlayer();
        ItemStack tool = p.getInventory().getItemInMainHand();
        World w = b.getWorld();
        String worldName = w.getName();

        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();
        SpawnerType type = mechanicsConfig.getSpawnerType(worldName, tool);

        if (type == null) {
            return;
        }

        double chance = type.getChance();
        PersistentDataContainer container = spawner.getPersistentDataContainer();

        System.out.println(container.has(spawnerKey, PersistentDataType.BYTE));

        //placed by player
        if(mechanicsConfig.isMineSpawnersAlwaysDropPlacedByPlayer()
                && container.has(spawnerKey, PersistentDataType.BYTE)) {
            PluginDebug.debug("Spawner placed by player - 100% drop");
            chance = 1.0;
        }

        if (!RandomUtils.chance(chance)) {
            return;
        }

        EntityType entityType = spawner.getSpawnedType();
        ItemStack spawnerItem = getSpawnerItem(entityType);

        w.dropItemNaturally(b.getLocation(), spawnerItem);

        e.setDropItems(false);
        e.setExpToDrop(0);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.MINE_SPAWNERS)){
            return;
        }

        Block b = e.getBlock();
        BlockState state = b.getState();

        if (!(state instanceof CreatureSpawner placedSpawner)) {
            return;
        }

        ItemStack item = e.getItemInHand();
        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();
        EntityType entityType = spawner.getSpawnedType();

        if(entityType == null){
            return;
        }

        placedSpawner.getPersistentDataContainer().set(spawnerKey, PersistentDataType.BYTE, (byte) 1);
        placedSpawner.setSpawnedType(entityType);
        placedSpawner.update(true);
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
