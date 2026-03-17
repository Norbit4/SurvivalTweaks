package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.info.ActionHealthMechanic;
import pl.norbit.survivaltweaks.mechanics.info.ClockMechanic;
import pl.norbit.survivaltweaks.mechanics.info.CompassMechanic;
import pl.norbit.survivaltweaks.mechanics.info.RecoveryCompassMechanic;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.BlockerConfig;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
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
        MechanicsConfig mechanicsConfig =  ConfigManager.getMechanicsConfig();
        BlockerConfig blockerConfig = ConfigManager.getBlockerConfig();

        return switch (mechanic) {
            case COMPASS -> mechanicsConfig.isCompassEnabled();
            case RECOVERY_COMPASS -> mechanicsConfig.isRecoveryCompassEnabled();
            case CLOCK -> mechanicsConfig.isClockEnabled();
            case SPYGLASS -> mechanicsConfig.isSpyglassEnabled();
            case SIZE -> mechanicsConfig.isSizeEnabled();
            case TURTLE_HELMET -> mechanicsConfig.isTurtleHelmetEnabled();
            case FIREBALL -> mechanicsConfig.isFireballEnabled();
            case PLAYER_HEAD -> mechanicsConfig.isPlayerHeadEnabled();
            case VOID_TOTEM -> mechanicsConfig.isVoidTotemEnabled();
            case CUSTOM_DEATH_MESSAGES -> mechanicsConfig.isCustomDeathMessageEnabled();
            case MINE_SPAWNERS -> mechanicsConfig.isMineSpawnersEnabled();
            case AMETHYST -> mechanicsConfig.isAmethystEnabled();
            case ENTITY_HP -> mechanicsConfig.isEntityHpEnabled();
            case BLAZE_DROP -> mechanicsConfig.isBlazeWaterDropEnabled();
            case VILLAGER_PROFESSION_CHANGE -> mechanicsConfig.isVillagerProfessionCooldownEnabled();
            case ARMADILLO_BRUSH_COOLDOWN -> blockerConfig.isArmadilloBrushCooldownEnabled();
            case ENDER_PEARL_DESPAWN -> blockerConfig.isEnderPearlDespawnEnabled();
            case BLOCK_BONE_MEAL ->  blockerConfig.isBlockBoneMealEnabled();
            case ELYTRA_GENERATE_DISABLED ->  blockerConfig.isElytraBlockGenerateEnabled();
            case ELYTRA_MENDING_DISABLED ->  blockerConfig.isElytraBlockMendingEnabled();
            case BLOCK_LOOT -> blockerConfig.isBlockLootEnabled();
            case KEEP_ITEMS -> mechanicsConfig.isKeepItemsEnabled();
            case INVISIBLE_ITEM_FRAMES -> mechanicsConfig.isInvisibleItemFramesEnabled();
            case HAPPY_GHOST_BOOST -> mechanicsConfig.isHappyGhostBoostEnabled();
            case ANVIL_TOO_EXPENSIVE -> mechanicsConfig.isAnvilTooExpensive();
            case MACE_NERF -> mechanicsConfig.isMaceNerfEnabled();
            case GRIEF_PROTECTION -> mechanicsConfig.isGriefProtectionEnabled();
            case NETHER_WITHER -> mechanicsConfig.isNetherWitherEnabled();
            case FURNACE_FUEL_NERF -> mechanicsConfig.isFurnaceFuelNerfEnabled();
            case SPEAR_NERF -> mechanicsConfig.isSpearNerfEnabled();
            case SPAWNER_NEAR_SPAWNER -> blockerConfig.isBlockSpawnNearSpawnerEnabled();
            case SPAWNER_EGG_CHANGE -> blockerConfig.isBlockSpawnEggChangeEnabled();
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
