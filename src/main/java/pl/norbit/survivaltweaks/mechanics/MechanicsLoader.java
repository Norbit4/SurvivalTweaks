package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.info.ActionHealthMechanic;
import pl.norbit.survivaltweaks.mechanics.info.ClockMechanic;
import pl.norbit.survivaltweaks.mechanics.info.CompassMechanic;
import pl.norbit.survivaltweaks.mechanics.info.RecoveryCompassMechanic;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class MechanicsLoader {

    private MechanicsLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        Config.load(reload);
        CompassMechanic.load();
        if(!reload) {
            asyncTimer(MechanicsLoader::heldItemTask, 30L, 20L);
            asyncTimer(MechanicsLoader::headItemTask, 30L, 25L);
        }
    }

    public static void unload() {
        CompassMechanic.unload();
    }

    public static boolean isDisabled(Mechanic mechanic) {
        return !isEnabled(mechanic);
    }

    public static boolean isEnabled(Mechanic mechanic) {
        return switch (mechanic) {
            case COMPASS -> Config.isCompassEnabled();
            case RECOVERY_COMPASS -> Config.isRecoveryCompassEnabled();
            case CLOCK -> Config.isClockEnabled();
            case SPYGLASS -> Config.isSpyglassEnabled();
            case SIZE -> Config.isSizeEnabled();
            case CAMPFIRE -> Config.isCampfireEnabled();
            case TURTLE_HELMET -> Config.isTurtleHelmetEnabled();
            case FIREBALL -> Config.isFireballEnabled();
            case PLAYER_HEAD -> Config.isPlayerHeadEnabled();
            case VOID_TOTEM -> Config.isVoidTotemEnabled();
            case CUSTOM_DEATH_MESSAGES -> Config.isCustomDeathMessageEnabled();
            case MINE_SPAWNERS -> Config.isMineSpawnersEnabled();
            case HORSE_TP -> Config.isHorseTpEnabled();
            case AMETHYST -> Config.isAmethystEnabled();
            case ENTITY_HP -> Config.isEntityHpEnabled();
            case BLAZE_DROP -> Config.isBlazeWaterDropEnabled();
            case VIILAGER_PROFESSION_CHANGE -> Config.isVillagerProfessionCooldownEnabled();
            case ARMADILLO_BRUSH_COOLDOWN -> Config.isArmadilloBrushCooldownEnabled();
            case ENDER_PEARL_DESPAWN -> Config.isEnderPearlDespawnEnabled();
            case BLOCK_BONE_MEAL -> Config.isBlockBoneMealEnabled();
            case ELYTRA_GENERATE_DISABLED -> Config.isElytraBlockGenerateEnabled();
            case ELYTRA_MENDING_DISABLED -> Config.isElytraBlockMendingEnabled();
            case BLOCK_LOOT -> Config.isBlockLootEnabled();
            case KEEP_ITEMS -> Config.isKeepItemsEnabled();
            case INVISIBLE_ITEM_FRAMES -> Config.isInvisibleItemFramesEnabled();
            case HAPPY_GHOST_BOOST -> Config.isHappyGhostBoostEnabled();
            case ANVIL_TOO_EXPENSIVE -> Config.isAnvilTooExpensive();
            case MACE_NERF -> Config.isMaceNerfEnabled();
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
