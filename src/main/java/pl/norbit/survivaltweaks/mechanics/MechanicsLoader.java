package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.BlockUtils;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import java.util.List;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class MechanicsLoader {

    private MechanicsLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        Config.load(reload);
        CompassMechanic.load();
        if(!reload) {
            SpyGlassMechanic.start();

            asyncTimer(MechanicsLoader::heldItemTask, 30L, 6L);
            asyncTimer(MechanicsLoader::blocksTask, 30L, 45L);
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
        };
    }

    private static void heldItemTask(){
        PlayerUtils.getOnlinePlayers().forEach(p -> {
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = p.getInventory().getItemInOffHand();

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

    private static void blocksTask(){
        PlayerUtils.getOnlinePlayers().forEach(p -> {
            List<Block> blocks = BlockUtils.getNearBlocks(p, 2);
            CampfireMechanic.applyEffect(p, blocks);
        });
    }
}
