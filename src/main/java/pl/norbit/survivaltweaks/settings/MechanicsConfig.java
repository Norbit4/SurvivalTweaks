package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MechanicsConfig extends ConfigFile {
    //compass
    @Getter
    private boolean compassEnabled;

    //clock
    @Getter
    private boolean clockEnabled;

    //recoveryCompass
    @Getter
    private boolean recoveryCompassEnabled;

    //spyglass
    @Getter
    private int spyglassCooldown;

    @Getter
    private boolean spyglassEnabled;

    //size
    @Getter
    private String smallSizeItem;

    @Getter
    private String normalSizeItem;

    @Getter
    private boolean sizeEnabled;

    //turtle helmet
    @Getter
    private boolean turtleHelmetEnabled;

    @Getter
    private boolean turtleHelmetDurabilityEnabled;

    //fireball
    @Getter
    private boolean fireballEnabled;

    @Getter
    private int fireballCooldown;

    @Getter
    private int fireballYield;

    //player heads
    @Getter
    private boolean playerHeadEnabled;

    @Getter
    private double playerHeadDropChance;

    //void totem
    @Getter
    private boolean voidTotemEnabled;

    //spawners
    @Getter
    private boolean mineSpawnersEnabled;

    //amethyst
    @Getter
    private boolean amethystEnabled;

    //entity hp
    @Getter
    private boolean entityHpEnabled;

    //blaze water drop
    @Getter
    private boolean blazeWaterDropEnabled;

    //villager profession cooldown
    @Getter
    private boolean villagerProfessionCooldownEnabled;

    @Getter
    private int villagerProfessionCooldown;

    //inv frames shift
    @Getter
    private boolean invisibleItemFramesEnabled;

    //jeep items
    @Getter
    private boolean keepItemsEnabled;

    @Getter
    private List<String> disableKeepItemsWorlds;

    //mace nerf
    @Getter
    private boolean maceNerfEnabled;

    @Getter
    private double maceNerfPercentage;

    @Getter
    private List<String> maceNerfDisabledWorlds;

    //happy ghost speed
    @Getter
    private boolean happyGhostBoostEnabled;

    @Getter
    private double happyGhostSpeedMultiplier;

    @Getter
    private double happyGhostHp;

    //anvil
    @Getter
    private boolean anvilTooExpensive;

    //custom dead messages
    @Getter
    private boolean customDeathMessageEnabled;

    //grief
    @Getter
    private boolean griefProtectionEnabled;

    @Getter
    private boolean griefProtectionTntEnabled;

    @Getter
    private boolean griefProtectionCrystalEnabled;

    @Getter
    private boolean griefProtectionAnchorEnabled;
    @Getter
    private boolean furnaceFuelNerfEnabled;

    //nether wither
    @Getter
    private boolean netherWitherEnabled;

    public MechanicsConfig(JavaPlugin plugin) {
        super(plugin, "mechanics.yml");
    }

    public void load(FileConfiguration config) {
        //compass
        compassEnabled = config.getBoolean("mechanics.compass.enabled");

        //clock
        clockEnabled = config.getBoolean("mechanics.clock.enabled");

        //recoveryCompass
        recoveryCompassEnabled = config.getBoolean("mechanics.recovery-compass.enabled");

        //spyglass
        spyglassCooldown = config.getInt("mechanics.spyglass.cooldown");
        spyglassEnabled = config.getBoolean("mechanics.spyglass.enabled");

        //size
        sizeEnabled = config.getBoolean("mechanics.size.enabled");
        smallSizeItem  = config.getString("mechanics.size.small");
        normalSizeItem = config.getString("mechanics.size.normal");

        //turtleHelmet
        turtleHelmetEnabled = config.getBoolean("mechanics.turtle-helmet.enabled");
        turtleHelmetDurabilityEnabled = config.getBoolean("mechanics.turtle-helmet.durability");

        //fireball
        fireballEnabled = config.getBoolean("mechanics.fireball.enabled");
        fireballCooldown = config.getInt("mechanics.fireball.cooldown");
        fireballYield = config.getInt("mechanics.fireball.yield");

        //playerHead
        playerHeadEnabled = config.getBoolean("mechanics.player-head.enabled");
        playerHeadDropChance = config.getDouble("mechanics.player-head.chance");

        //void totem
        voidTotemEnabled = config.getBoolean("mechanics.void-totem.enabled");

        //mine spawner
        mineSpawnersEnabled = config.getBoolean("mechanics.mine-spawners.enabled");

        //amethyst
        amethystEnabled = config.getBoolean("mechanics.mine-budding-amethyst.enabled");

        //entity hp
        entityHpEnabled = config.getBoolean("mechanics.entity-hp.enabled");

        //blaze water drop
        blazeWaterDropEnabled = config.getBoolean("mechanics.blaze-water-drop.enabled");

        //villager profession cooldown
        villagerProfessionCooldownEnabled = config.getBoolean("mechanics.villager-profession-cooldown.enabled");
        villagerProfessionCooldown = config.getInt("mechanics.villager-profession-cooldown.cooldown");

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

        furnaceFuelNerfEnabled = config.getBoolean("mechanics.furnace-fuel.nerf-enabled");
        netherWitherEnabled = config.getBoolean("mechanics.nether-wither.enabled");
    }

    public boolean isDisabledMaceNerfWorld(String worldName) {
        return maceNerfDisabledWorlds.stream().anyMatch(s -> s.equals(worldName));
    }

    public boolean isWorldDisabledForKeepItems(String worldName) {
        return disableKeepItemsWorlds.stream().anyMatch(s -> s.equals(worldName));
    }
}
