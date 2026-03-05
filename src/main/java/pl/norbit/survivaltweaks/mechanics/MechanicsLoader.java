package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.info.ActionHealthMechanic;
import pl.norbit.survivaltweaks.mechanics.info.ClockMechanic;
import pl.norbit.survivaltweaks.mechanics.info.CompassMechanic;
import pl.norbit.survivaltweaks.mechanics.info.RecoveryCompassMechanic;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class MechanicsLoader {

    private MechanicsLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        if(!reload) {
            asyncTimer(MechanicsLoader::heldItemTask, 30L, 20L);
            asyncTimer(MechanicsLoader::headItemTask, 30L, 25L);
        }
    }

    public static boolean isDisabled(Mechanic mechanic) {
        return !isEnabled(mechanic);
    }

    public static boolean isEnabled(Mechanic mechanic) {
        return switch (mechanic) {
            case COMPASS -> ConfigManager.getMechanicsConfig().isCompassEnabled();
            case RECOVERY_COMPASS -> ConfigManager.getMechanicsConfig().isRecoveryCompassEnabled();
            case CLOCK -> ConfigManager.getMechanicsConfig().isClockEnabled();
            case SPYGLASS -> ConfigManager.getMechanicsConfig().isSpyglassEnabled();
            case SIZE -> ConfigManager.getMechanicsConfig().isSizeEnabled();
            case TURTLE_HELMET -> ConfigManager.getMechanicsConfig().isTurtleHelmetEnabled();
            case FIREBALL -> ConfigManager.getMechanicsConfig().isFireballEnabled();
            case PLAYER_HEAD -> ConfigManager.getMechanicsConfig().isPlayerHeadEnabled();
            case VOID_TOTEM -> ConfigManager.getMechanicsConfig().isVoidTotemEnabled();
            case CUSTOM_DEATH_MESSAGES -> ConfigManager.getMechanicsConfig().isCustomDeathMessageEnabled();
            case MINE_SPAWNERS -> ConfigManager.getMechanicsConfig().isMineSpawnersEnabled();
            case AMETHYST -> ConfigManager.getMechanicsConfig().isAmethystEnabled();
            case ENTITY_HP -> ConfigManager.getMechanicsConfig().isEntityHpEnabled();
            case BLAZE_DROP -> ConfigManager.getMechanicsConfig().isBlazeWaterDropEnabled();
            case VILLAGER_PROFESSION_CHANGE -> ConfigManager.getMechanicsConfig().isVillagerProfessionCooldownEnabled();
            case ARMADILLO_BRUSH_COOLDOWN -> ConfigManager.getBlockerConfig().isArmadilloBrushCooldownEnabled();
            case ENDER_PEARL_DESPAWN -> ConfigManager.getBlockerConfig().isEnderPearlDespawnEnabled();
            case BLOCK_BONE_MEAL ->  ConfigManager.getBlockerConfig().isBlockBoneMealEnabled();
            case ELYTRA_GENERATE_DISABLED ->  ConfigManager.getBlockerConfig().isElytraBlockGenerateEnabled();
            case ELYTRA_MENDING_DISABLED ->  ConfigManager.getBlockerConfig().isElytraBlockMendingEnabled();
            case BLOCK_LOOT ->  ConfigManager.getBlockerConfig().isBlockLootEnabled();
            case KEEP_ITEMS -> ConfigManager.getMechanicsConfig().isKeepItemsEnabled();
            case INVISIBLE_ITEM_FRAMES -> ConfigManager.getMechanicsConfig().isInvisibleItemFramesEnabled();
            case HAPPY_GHOST_BOOST -> ConfigManager.getMechanicsConfig().isHappyGhostBoostEnabled();
            case ANVIL_TOO_EXPENSIVE -> ConfigManager.getMechanicsConfig().isAnvilTooExpensive();
            case MACE_NERF -> ConfigManager.getMechanicsConfig().isMaceNerfEnabled();
            case GRIEF_PROTECTION -> ConfigManager.getMechanicsConfig().isGriefProtectionEnabled();
            case NETHER_WITHER -> ConfigManager.getMechanicsConfig().isNetherWitherEnabled();
            case FURNACE_FUEL_NERF -> ConfigManager.getMechanicsConfig().isFurnaceFuelNerfEnabled();
        };
    }

    private static void heldItemTask(){
        PlayerUtils.getOnlinePlayers().forEach(p -> {
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = p.getInventory().getItemInOffHand();

            ActionHealthMechanic.check(p, itemInMainHand, itemInOffHand);
            CompassMechanic.check(p, itemInMainHand, itemInOffHand);
            ClockMechanic.check(p, itemInMainHand, itemInOffHand);
            RecoveryCompassMechanic.check(p, itemInMainHand, itemInOffHand);
        });
    }

    private static void headItemTask(){
        PlayerUtils.getOnlinePlayers().forEach(p -> {
            ItemStack headItem = p.getInventory().getHelmet();

            if(headItem == null){
                return;
            }

            TurtleHelmetMechanic.check(p, headItem);
        });
    }
}
