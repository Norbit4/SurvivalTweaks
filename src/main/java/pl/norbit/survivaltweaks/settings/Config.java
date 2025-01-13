package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Config {
    @Getter
    private static String reloadMessage;
    //compass
    @Getter
    private static String compass;
    @Getter
    private static boolean blockF3;
    @Getter
    private static boolean compassEnabled;
    @Getter
    private static String messageTrackUsage;
    @Getter
    private static String messageOnlyPlayer;
    @Getter
    private static String messageInvalidMaterial;
    @Getter
    private static String messageInvalidNumber;
    @Getter
    private static String messageTrackSuccess;
    @Getter
    private static String messageTrackItem;

    //clock
    @Getter
    private static String clock;

    @Getter
    private static boolean clockEnabled;

    //recoveryCompass
    @Getter
    private static String recoveryCompass;

    @Getter
    private static String noDeathLocation;

    @Getter
    private static boolean recoveryCompassEnabled;

    //spyglass
    @Getter
    private static String spyglass;

    @Getter
    private static int spyglassCooldown;

    @Getter
    private static boolean spyglassEnabled;

    //size
    @Getter
    private static Material smallSizeItem;

    @Getter
    private static Material normalSizeItem;

    @Getter
    private static boolean sizeEnabled;

    @Getter
    private static boolean campfireEnabled;

    @Getter
    private static boolean turtleHelmetEnabled;

    @Getter
    private static boolean turtleHelmetDurabilityEnabled;

    @Getter
    private static boolean fireballEnabled;

    @Getter
    private static int fireballCooldown;

    @Getter
    private static int fireballYield;

    @Getter
    private static List<Material> fireballBlockedList;

    @Getter
    private static boolean playerHeadEnabled;

    @Getter
    private static double playerHeadDropChance;

    @Getter
    private static boolean voidTotemEnabled;

    @Getter
    private static boolean customDeathMessageEnabled;

    @Getter
    private static String deathMessagePrefix;

    @Getter
    private static String deathMessageDrowning;

    @Getter
    private static String deathMessageSuffocation;

    @Getter
    private static String deathMessageFall;

    @Getter
    private static String deathMessageLava;

    @Getter
    private static String deathMessageFire;

    @Getter
    private static String deathMessageFireTick;

    @Getter
    private static String deathMessageVoid;

    @Getter
    private static String deathMessageLightning;

    @Getter
    private static String deathMessageEntityAttack;

    @Getter
    private static String deathMessageEntityExplosion;

    @Getter
    private static String deathMessageProjectile;

    @Getter
    private static String deathMessageMagic;

    @Getter
    private static String deathMessageWither;

    @Getter
    private static String deathMessageStarvation;

    @Getter
    private static String deathMessagePoison;

    @Getter
    private static String deathMessageThorns;

    @Getter
    private static String deathMessageDragon;

    @Getter
    private static String deathMessageHotFloor;

    @Getter
    private static String deathMessageOther;

    @Getter
    private static boolean mineSpawnersEnabled;

    @Getter
    private static boolean horseTpEnabled;

    @Getter
    private static boolean amethystEnabled;

    @Getter
    private static boolean entityHpEnabled;

    @Getter
    private static boolean blazeWaterDropEnabled;

    @Getter
    private static boolean villagerProfessionCooldownEnabled;

    @Getter
    private static int villagerProfessionCooldown;

    @Getter
    private static String villagerProfessionCooldownMessage;

    @Getter
    private static String villagerProfessionCooldownTimeMinutes;

    @Getter
    private static String villagerProfessionCooldownTimeSeconds;

    @Getter
    private static boolean sleepMechanicEnabled;

    @Getter
    private static double sleepMechanicPercentage;

    @Getter
    private static String sleepMechanicActionBarMessage;

    @Getter
    private static String sleepMechanicTitleMessage;

    @Getter
    private static String sleepMechanicSubtitleMessage;

    @Getter
    private static String sleepMechanicSubtitleSuccessMessage;

    @Getter
    private static String sleepMechanicDenyMessage;

    @Getter
    private static String entityHpDisplay;

    private static Map<String, String> mobNames;

    public static String getMobNameOrDefault(EntityType mobType, String defaultName) {
        return mobNames.getOrDefault(mobType.name().toUpperCase(), defaultName);
    }

    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        SurvivalTweaks instance = SurvivalTweaks.getInstance();

        if (!reload) {
            instance.saveDefaultConfig();
        }

        if (reload) {
            instance.reloadConfig();
        }

        FileConfiguration config = instance.getConfig();

        //reloadMessage
        reloadMessage = config.getString("reload-message");

        //compass
        compass = config.getString("mechanics.compass.display");
        blockF3 = config.getBoolean("mechanics.compass.block-f3");
        compassEnabled = config.getBoolean("mechanics.compass.enabled");

        messageTrackUsage = config.getString("mechanics.compass.messages.track-usage");
        messageOnlyPlayer = config.getString("mechanics.compass.messages.only-player");
        messageInvalidMaterial = config.getString("mechanics.compass.messages.invalid-material");
        messageInvalidNumber = config.getString("mechanics.compass.messages.invalid-number");
        messageTrackSuccess = config.getString("mechanics.compass.messages.track-success");
        messageTrackItem = config.getString("mechanics.compass.messages.track-item");

        //clock
        clock = config.getString("mechanics.clock.display");
        clockEnabled = config.getBoolean("mechanics.clock.enabled");

        //recoveryCompass
        recoveryCompass = config.getString("mechanics.recovery-compass.display");
        noDeathLocation = config.getString("mechanics.recovery-compass.display-no-death-location");
        recoveryCompassEnabled = config.getBoolean("mechanics.recovery-compass.enabled");

        //spyglass
        spyglass = config.getString("mechanics.spyglass.display");
        spyglassCooldown = config.getInt("mechanics.spyglass.cooldown");
        spyglassEnabled = config.getBoolean("mechanics.spyglass.enabled");

        //size
        String small = config.getString("mechanics.size.small");
        String normal = config.getString("mechanics.size.normal");

        if(small == null || normal == null) {
            instance.getLogger().severe("Size items are not set in the config file!");
            instance.getServer().getPluginManager().disablePlugin(instance);
            return;
        }

        smallSizeItem = Material.getMaterial(small.toUpperCase());
        normalSizeItem = Material.getMaterial(normal.toUpperCase());

        sizeEnabled = config.getBoolean("mechanics.size.enabled");

        //campfire
        campfireEnabled = config.getBoolean("mechanics.campfire.enabled");

        //turtleHelmet
        turtleHelmetEnabled = config.getBoolean("mechanics.turtle-helmet.enabled");
        turtleHelmetDurabilityEnabled = config.getBoolean("mechanics.turtle-helmet.durability");

        //fireball
        fireballEnabled = config.getBoolean("mechanics.fireball.enabled");
        fireballCooldown = config.getInt("mechanics.fireball.cooldown");
        fireballYield = config.getInt("mechanics.fireball.yield");

        fireballBlockedList = config.getStringList("mechanics.fireball.blocked-blocks")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .toList();

        //playerHead
        playerHeadEnabled = config.getBoolean("mechanics.player-head.enabled");
        playerHeadDropChance = config.getDouble("mechanics.player-head.chance");

        //void totem
        voidTotemEnabled = config.getBoolean("mechanics.void-totem.enabled");

        //mine spawner
        mineSpawnersEnabled = config.getBoolean("mechanics.mine-spawners.enabled");

        //horse tp
        horseTpEnabled = config.getBoolean("mechanics.horse-tp.enabled");

        //amethyst
        amethystEnabled = config.getBoolean("mechanics.mine-budding-amethyst.enabled");

        //entity hp
        entityHpEnabled = config.getBoolean("mechanics.entity-hp.enabled");
        entityHpDisplay = config.getString("mechanics.entity-hp.display");

        //blaze water drop
        blazeWaterDropEnabled = config.getBoolean("mechanics.blaze-water-drop.enabled");

        //villager profession cooldown
        villagerProfessionCooldownEnabled = config.getBoolean("mechanics.villager-profession-cooldown.enabled");
        villagerProfessionCooldown = config.getInt("mechanics.villager-profession-cooldown.cooldown");
        villagerProfessionCooldownMessage = config.getString("mechanics.villager-profession-cooldown.message");
        villagerProfessionCooldownTimeMinutes = config.getString("mechanics.villager-profession-cooldown.time.minutes");
        villagerProfessionCooldownTimeSeconds = config.getString("mechanics.villager-profession-cooldown.time.seconds");

        //sleep
        sleepMechanicEnabled = config.getBoolean("mechanics.sleep.enabled");
        sleepMechanicPercentage = config.getDouble("mechanics.sleep.percentage");
        sleepMechanicActionBarMessage = config.getString("mechanics.sleep.messages.action-bar");
        sleepMechanicTitleMessage = config.getString("mechanics.sleep.messages.title");
        sleepMechanicSubtitleMessage = config.getString("mechanics.sleep.messages.subtitle");
        sleepMechanicDenyMessage = config.getString("mechanics.sleep.messages.deny");
        sleepMechanicSubtitleSuccessMessage = config.getString("mechanics.sleep.messages.subtitle-skip");

        //custom death message
        customDeathMessageEnabled = config.getBoolean("mechanics.dead-messages.enabled");
        deathMessagePrefix = config.getString("mechanics.dead-messages.prefix");
        deathMessageDrowning = config.getString("mechanics.dead-messages.messages.drowning");
        deathMessageSuffocation = config.getString("mechanics.dead-messages.messages.suffocation");
        deathMessageFall = config.getString("mechanics.dead-messages.messages.fall");
        deathMessageLava = config.getString("mechanics.dead-messages.messages.lava");
        deathMessageFire = config.getString("mechanics.dead-messages.messages.fire");
        deathMessageFireTick = config.getString("mechanics.dead-messages.messages.fire-tick");
        deathMessageVoid = config.getString("mechanics.dead-messages.messages.void");
        deathMessageLightning = config.getString("mechanics.dead-messages.messages.lightning");
        deathMessageEntityAttack = config.getString("mechanics.dead-messages.messages.entity-attack");
        deathMessageEntityExplosion = config.getString("mechanics.dead-messages.messages.entity-explosion");
        deathMessageProjectile = config.getString("mechanics.dead-messages.messages.projectile");
        deathMessageMagic = config.getString("mechanics.dead-messages.messages.magic");
        deathMessageWither = config.getString("mechanics.dead-messages.messages.wither");
        deathMessageStarvation = config.getString("mechanics.dead-messages.messages.starvation");
        deathMessagePoison = config.getString("mechanics.dead-messages.messages.poison");
        deathMessageThorns = config.getString("mechanics.dead-messages.messages.thorns");
        deathMessageDragon = config.getString("mechanics.dead-messages.messages.dragon");
        deathMessageHotFloor = config.getString("mechanics.dead-messages.messages.hot-floor");
        deathMessageOther = config.getString("mechanics.dead-messages.messages.other");

        mobNames = new HashMap<>();

        for (String mob : config.getConfigurationSection("mob-names").getKeys(false)) {
            String name = config.getString("mob-names." + mob);
            if (name == null) {
                continue;
            }
            mobNames.put(mob.toUpperCase(), name);
        }
    }
}
