package pl.norbit.survivaltweaks.mechanics.listeners.spawner;

import org.bukkit.Location;
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
import org.bukkit.inventory.meta.Damageable;
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
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

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
        Location loc = b.getLocation();

        if (type == null) {
            onFailureDrop(mechanicsConfig, w, loc);
            return;
        }

        double chance = type.getChance();
        PersistentDataContainer container = spawner.getPersistentDataContainer();

        boolean placedByPlayer = container.has(spawnerKey, PersistentDataType.BYTE);

        //placed by player
        if(mechanicsConfig.isMineSpawnersAlwaysDropPlacedByPlayer() && placedByPlayer) {
            PluginDebug.debug("Spawner placed by player - 100% drop");
            chance = 1.0;
        }

        if (!RandomUtils.chance(chance)) {
            onFailureDrop(mechanicsConfig, w, loc);
            return;
        }

        EntityType entityType = spawner.getSpawnedType();
        ItemStack spawnerItem = getSpawnerItem(entityType);

        w.dropItemNaturally(loc, spawnerItem);

        e.setDropItems(false);
        e.setExpToDrop(0);

        if (placedByPlayer) {
            refundDurability(tool);
        }
    }

    private void refundDurability(ItemStack tool) {
        if (tool == null || tool.getType().getMaxDurability() <= 0) return;

        if (tool.getItemMeta() instanceof Damageable damageable) {
            int dmg = damageable.getDamage();
            if (dmg > 0) {
                damageable.setDamage(dmg - 1);
                tool.setItemMeta(damageable);
            }
        }
    }

    private void onFailureDrop(MechanicsConfig mechanicsConfig, World w, Location loc){
        if(!mechanicsConfig.isFailureDropEnabled()){
            return;
        }

        int failureDropAmount = mechanicsConfig.getFailureDropAmount();
        String failureDropItem = mechanicsConfig.getFailureDropItem();

        ItemStack item = ItemsUtils.getItem(failureDropItem);

        if(item == null){
            return;
        }

        item.setAmount(failureDropAmount);

        w.dropItemNaturally(loc, item);
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
