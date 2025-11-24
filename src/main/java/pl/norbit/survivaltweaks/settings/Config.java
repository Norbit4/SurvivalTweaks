package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.io.File;
import java.util.*;

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
    private static String entityHpDisplay;

    private static Map<String, String> mobNames;

    @Getter
    private static boolean armadilloBrushCooldownEnabled;

    @Getter
    private static int armadilloBrushCooldown;

    @Getter
    private static boolean enderPearlDespawnEnabled;

    @Getter
    private static boolean blockBoneMealEnabled;
    @Getter
    private static boolean blockBoneMealDispenserEnabled;

    @Getter
    private static String blockBoneMealMessage;

    @Getter
    private static List<Material> blockedBoneMeal;

    @Getter
    private static boolean elytraBlockGenerateEnabled;

    @Getter
    private static boolean elytraBlockMendingEnabled;

    @Getter
    private static boolean blockLootEnabled;

    @Getter
    private static List<Material> blockLootItems;

    @Getter
    private static boolean invisibleItemFramesEnabled;

    @Getter
    private static boolean keepItemsEnabled;

    @Getter
    private static List<String> disableKeepItemsWorlds;

    @Getter
    private static String respawnTitle;

    @Getter
    private static String respawnSubtitle;

    @Getter
    private static boolean maceNerfEnabled;

    @Getter
    private static double maceNerfPercentage;

    @Getter
    private static List<String> maceNerfDisabledWorlds;


    @Getter
    private static boolean happyGhostBoostEnabled;

    @Getter
    private static double happyGhostSpeedMultiplier;

    @Getter
    private static double happyGhostHp;

    @Getter
    private static boolean anvilTooExpensive;

    @Getter
    private static boolean griefProtectionEnabled;

    @Getter
    private static boolean griefProtectionTntEnabled;

    @Getter
    private static boolean griefProtectionCrystalEnabled;

    @Getter
    private static boolean griefProtectionAnchorEnabled;

    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isBlockedBoneMeal(Material mat) {
        return blockedBoneMeal.contains(mat);
    }

    public static boolean isDisabledMaceNerfWorld(String worldName) {
        return maceNerfDisabledWorlds.stream().anyMatch(s -> s.equals(worldName));
    }

    public static boolean isWorldDisabledForKeepItems(String worldName) {
        return disableKeepItemsWorlds.stream().anyMatch(s -> s.equals(worldName));
    }

    public static String getMobNameOrDefault(EntityType mobType, String defaultName) {
        return mobNames.getOrDefault(mobType.name().toUpperCase(), defaultName);
    }

    public static void load(boolean reload) {
        SurvivalTweaks instance = SurvivalTweaks.getInstance();

        File messagesFile = new File(instance.getDataFolder(), "messages.yml");
        File blockedFile = new File(instance.getDataFolder(), "blocked.yml");

        if (!reload) {
            instance.saveDefaultConfig();
        }

        if (reload) {
            instance.reloadConfig();
        }

        if (!messagesFile.exists()) {
            instance.saveResource("messages.yml", false);
            instance.saveResource("blocked.yml", false);
        }

        FileConfiguration config = instance.getConfig();
        FileConfiguration messages = YamlConfiguration.loadConfiguration(messagesFile);
        FileConfiguration blocked = YamlConfiguration.loadConfiguration(blockedFile);

        loadMessages(messages);
        loadBlocked(instance, blocked);

        blockF3 = config.getBoolean("mechanics.compass.block-f3");
        compassEnabled = config.getBoolean("mechanics.compass.enabled");

        //clock
        clockEnabled = config.getBoolean("mechanics.clock.enabled");

        //recoveryCompass
        recoveryCompassEnabled = config.getBoolean("mechanics.recovery-compass.enabled");

        //spyglass
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

        //blaze water drop
        blazeWaterDropEnabled = config.getBoolean("mechanics.blaze-water-drop.enabled");

        //villager profession cooldown
        villagerProfessionCooldownEnabled = config.getBoolean("mechanics.villager-profession-cooldown.enabled");
        villagerProfessionCooldown = config.getInt("mechanics.villager-profession-cooldown.cooldown");

        //blocker
        armadilloBrushCooldownEnabled = config.getBoolean("mechanics.blocker.armadillo-brush-cooldown.enabled", false);
        armadilloBrushCooldown = config.getInt("mechanics.blocker.armadillo-brush-cooldown.cooldown", 30);

        enderPearlDespawnEnabled = config.getBoolean("mechanics.blocker.ender-pearl-despawn.enabled", false);

        blockBoneMealEnabled = config.getBoolean("mechanics.blocker.block-bone-meal.enabled", false);
        blockBoneMealDispenserEnabled = config.getBoolean("mechanics.blocker.block-bone-meal.block-dispenser", false);

        elytraBlockGenerateEnabled = config.getBoolean("mechanics.blocker.elytra.block-generate", false);
        elytraBlockMendingEnabled = config.getBoolean("mechanics.blocker.elytra.block-mending", false);

        blockLootEnabled = config.getBoolean("mechanics.blocker.block-loot.enabled", false);
        blockLootItems = config.getStringList("mechanics.blocker.block-loot.items")
                .stream()
                .map(s -> {
                    try {
                        return Material.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        instance.getLogger().warning("Invalid material in blocker.block-loot.items: " + s);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        invisibleItemFramesEnabled = config.getBoolean("mechanics.invisible-item-frames.enabled", true);

        keepItemsEnabled = config.getBoolean("mechanics.keep-items.enabled", false);
        disableKeepItemsWorlds = config.getStringList("mechanics.keep-items.disabled-worlds");

        customDeathMessageEnabled = config.getBoolean("mechanics.dead-messages.enabled");

        //mace nerf
        maceNerfEnabled = config.getBoolean("mechanics.mace-nerf.enabled");
        maceNerfPercentage = config.getDouble("mechanics.mace-nerf.percentage");
        maceNerfDisabledWorlds = config.getStringList("mechanics.mace-nerf.disabled-world");

        //happy ghost
        happyGhostBoostEnabled = config.getBoolean("mechanics.happy-ghost-boost.enabled");
        happyGhostSpeedMultiplier = config.getDouble("mechanics.happy-ghost-boost.speed-multiplier");
        happyGhostHp = config.getDouble("mechanics.happy-ghost-boost.hp");

        //anvil too expensive
        anvilTooExpensive = config.getBoolean("mechanics.anvil-too-expensive-fix.enabled");

        //grief protection
        griefProtectionEnabled = config.getBoolean("mechanics.grief-protection.enabled");
        griefProtectionTntEnabled = config.getBoolean("mechanics.grief-protection.block-tnt-explosion");
        griefProtectionCrystalEnabled = config.getBoolean("mechanics.grief-protection.block-crystal-explosion");
        griefProtectionAnchorEnabled = config.getBoolean("mechanics.grief-protection.block-anchor-explosion");
    }

    private static void loadBlocked(SurvivalTweaks instance, FileConfiguration config) {
        blockedBoneMeal = config.getStringList("blocker.block-bone-meal.blocked")
                .stream()
                .map(s -> {
                    try {
                        return Material.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        instance.getLogger().warning("Invalid material in blocker.block-bone-meal.items: " + s);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        fireballBlockedList = config.getStringList("fireball.blocked-blocks")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .toList();
    }

    private static void loadMessages(FileConfiguration config) {
        //reloadMessage
        reloadMessage = config.getString("reload-message");

        //compass
        compass = config.getString("compass.display");
        messageTrackUsage = config.getString("compass.messages.track-usage");
        messageOnlyPlayer = config.getString("compass.messages.only-player");
        messageInvalidMaterial = config.getString("compass.messages.invalid-material");
        messageInvalidNumber = config.getString("compass.messages.invalid-number");
        messageTrackSuccess = config.getString("compass.messages.track-success");
        messageTrackItem = config.getString("compass.messages.track-item");

        //clock
        clock = config.getString("clock.display");

        //
        spyglass = config.getString("spyglass.display");

        //recoveryCompass
        recoveryCompass = config.getString("recovery-compass.display");
        noDeathLocation = config.getString("recovery-compass.display-no-death-location");

        //entity hp
        entityHpDisplay = config.getString("entity-hp.display");

        //villager profession cooldown
        villagerProfessionCooldownMessage = config.getString("villager-profession-cooldown.message");
        villagerProfessionCooldownTimeMinutes = config.getString("villager-profession-cooldown.time.minutes");
        villagerProfessionCooldownTimeSeconds = config.getString("villager-profession-cooldown.time.seconds");

        //block bone meal
        blockBoneMealMessage = config.getString("blocker.block-bone-meal.messages");

        //keep items
        respawnTitle = config.getString("keep-items.messages.title", "&c&l☠ You died!");
        respawnSubtitle = config.getString("keep-items.messages.subtitle", "&7You kept &a50% &7items!");

        //custom death message
        deathMessagePrefix = config.getString("dead-messages.prefix");
        deathMessageDrowning = config.getString("dead-messages.messages.drowning");
        deathMessageSuffocation = config.getString("dead-messages.messages.suffocation");
        deathMessageFall = config.getString("dead-messages.messages.fall");
        deathMessageLava = config.getString("dead-messages.messages.lava");
        deathMessageFire = config.getString("dead-messages.messages.fire");
        deathMessageFireTick = config.getString("dead-messages.messages.fire-tick");
        deathMessageVoid = config.getString("dead-messages.messages.void");
        deathMessageLightning = config.getString("dead-messages.messages.lightning");
        deathMessageEntityAttack = config.getString("dead-messages.messages.entity-attack");
        deathMessageEntityExplosion = config.getString("dead-messages.messages.entity-explosion");
        deathMessageProjectile = config.getString("dead-messages.messages.projectile");
        deathMessageMagic = config.getString("dead-messages.messages.magic");
        deathMessageWither = config.getString("dead-messages.messages.wither");
        deathMessageStarvation = config.getString("dead-messages.messages.starvation");
        deathMessagePoison = config.getString("dead-messages.messages.poison");
        deathMessageThorns = config.getString("dead-messages.messages.thorns");
        deathMessageDragon = config.getString("dead-messages.messages.dragon");
        deathMessageHotFloor = config.getString("dead-messages.messages.hot-floor");
        deathMessageOther = config.getString("dead-messages.messages.other");

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
