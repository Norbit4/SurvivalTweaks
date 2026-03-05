package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;


public class MessagesConfig extends ConfigFile {
    @Getter
    private String reloadMessage;
    //compass
    @Getter
    private String compass;
    @Getter
    private String messageTrackUsage;
    @Getter
    private String messageOnlyPlayer;
    @Getter
    private String messageInvalidMaterial;
    @Getter
    private String messageInvalidNumber;
    @Getter
    private String messageTrackSuccess;
    @Getter
    private String messageTrackItem;

    //clock
    @Getter
    private String clock;

    //recoveryCompass
    @Getter
    private String recoveryCompass;

    @Getter
    private String noDeathLocation;

    //spyglass
    @Getter
    private String spyglass;

    @Getter
    private String deathMessagePrefix;

    @Getter
    private String deathMessageDrowning;

    @Getter
    private String deathMessageSuffocation;

    @Getter
    private String deathMessageFall;

    @Getter
    private String deathMessageLava;

    @Getter
    private String deathMessageFire;

    @Getter
    private String deathMessageFireTick;

    @Getter
    private String deathMessageVoid;

    @Getter
    private String deathMessageLightning;

    @Getter
    private String deathMessageEntityAttack;

    @Getter
    private String deathMessageEntityExplosion;

    @Getter
    private String deathMessageProjectile;

    @Getter
    private String deathMessageMagic;

    @Getter
    private String deathMessageWither;

    @Getter
    private String deathMessageStarvation;

    @Getter
    private String deathMessagePoison;

    @Getter
    private String deathMessageThorns;

    @Getter
    private String deathMessageDragon;

    @Getter
    private String deathMessageHotFloor;

    @Getter
    private String deathMessageOther;

    @Getter
    private String villagerProfessionCooldownMessage;

    @Getter
    private String villagerProfessionCooldownTimeMinutes;

    @Getter
    private String villagerProfessionCooldownTimeSeconds;

    @Getter
    private String entityHpDisplay;

    private Map<String, String> mobNames;

    @Getter
    private String blockBoneMealMessage;

    @Getter
    private String respawnTitle;

    @Getter
    private String respawnSubtitle;

    public MessagesConfig(JavaPlugin plugin) {
        super(plugin, "messages.yml");
    }

    public void load(FileConfiguration config) {
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
        blockBoneMealMessage = config.getString("block-bone-meal.messages");

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
    public String getMobNameOrDefault(EntityType mobType, String defaultName) {
        if(mobType == null || mobType == EntityType.ARMOR_STAND) {
            return defaultName;
        }

        return mobNames.getOrDefault(mobType.name().toUpperCase(), defaultName);
    }
}
