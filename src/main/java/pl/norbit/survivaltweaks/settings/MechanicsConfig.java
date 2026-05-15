package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.settings.model.InfinityFood;
import pl.norbit.survivaltweaks.settings.model.SpawnerType;
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

import java.util.*;

public class MechanicsConfig extends ConfigFile {
    private final Random random = new Random();
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
    private boolean playerHeadAntiAbuse;


    @Getter
    private double playerHeadDropChance;

    //void totem
    @Getter
    private boolean voidTotemEnabled;

    //spawners
    @Getter
    private boolean mineSpawnersEnabled;
    @Getter
    private boolean mineSpawnersAlwaysDropPlacedByPlayer;
    @Getter
    private List<SpawnerType> spawnerTypes;

    @Getter
    private boolean failureDropEnabled;

    @Getter
    private String failureDropItem;

    @Getter
    private int failureDropAmount;

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

    //infinity food
    @Getter
    private boolean infinityFoodEnabled;

    @Getter
    private Map<String, InfinityFood> itemsFoodMap;

    //keep items
    @Getter
    private boolean keepItemsEnabled;

    @Getter
    private List<String> disableKeepItemsWorlds;

    @Getter
    private List<String> keepItemsAlwaysKeep;

    public boolean isAlwaysKeepItem(ItemStack itemStack){
        for (String id : keepItemsAlwaysKeep) {
            if(ItemsUtils.isValidItem(itemStack, id)){
                return true;
            }
        }
        return false;
    }

    //mace nerf
    @Getter
    private boolean maceNerfEnabled;

    @Getter
    private double maceMaxDamage;
    @Getter
    private double maceNerfPercentage;

    @Getter
    private List<String> maceNerfDisabledWorlds;

    //nerf spear
    @Getter
    private boolean spearNerfEnabled;

    @Getter
    private double spearNerfPercentage;

    @Getter
    private double spearMaxDamage;

    @Getter
    private int spearCooldown;

    @Getter
    private List<String> spearNerfDisabledWorlds;

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
    @Getter
    private boolean customDeathMessageAntiAbuse;

    @Getter
    private List<String> customDeathMessageDisabledWorlds;

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
    private boolean griefProtectionTntMinecraftEnabled;
    @Getter
    private boolean furnaceFuelNerfEnabled;

    //nether wither
    @Getter
    private boolean netherWitherEnabled;

    @Getter
    private boolean villagersRandomNamesEnabled;
    @Getter
    private List<String> villagersRandomNames;

    @Getter
    private boolean disableEqDropsForSpawnerMobs;

    public MechanicsConfig(JavaPlugin plugin) {
        super(plugin, "mechanics.yml");
    }

    public boolean isDisabledDeathMessageWorld(String worldName){
        return customDeathMessageDisabledWorlds.contains(worldName);
    }

    public InfinityFood getInfinityFood(String id){
        return itemsFoodMap.get(id);
    }

    public SpawnerType getSpawnerType(String world, ItemStack tool) {
        for (SpawnerType type : spawnerTypes) {
            if (!type.isCorrectWorld(world)) {
                continue;
            }

            if (!type.isCorrectTool(tool)) {
                continue;
            }

            return type;
        }
        return null;
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
        playerHeadAntiAbuse = config.getBoolean("mechanics.player-head.anti-abuse");

        //void totem
        voidTotemEnabled = config.getBoolean("mechanics.void-totem.enabled");

        //mine spawner
        mineSpawnersEnabled = config.getBoolean("mechanics.mine-spawners.enabled");
        mineSpawnersAlwaysDropPlacedByPlayer = config.getBoolean("mechanics.mine-spawners.always-drop-placed-by-players");
        spawnerTypes = loadSpawnerTypes(config);

        failureDropEnabled = config.getBoolean("mechanics.mine-spawners.failure-drop.enabled");
        failureDropItem = config.getString("mechanics.mine-spawners.failure-drop.item");
        failureDropAmount  = config.getInt("mechanics.mine-spawners.failure-drop.amount");

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
        keepItemsAlwaysKeep = config.getStringList("mechanics.keep-items.always-keep");

        customDeathMessageEnabled = config.getBoolean("mechanics.dead-messages.enabled");
        customDeathMessageAntiAbuse = config.getBoolean("mechanics.dead-messages.anti-abuse");
        customDeathMessageDisabledWorlds = config.getStringList("mechanics.dead-messages.disabled-worlds");

        //mace nerf
        maceNerfEnabled = config.getBoolean("mechanics.mace-nerf.enabled");
        maceMaxDamage = config.getDouble("mechanics.mace-nerf.max-damage");
        maceNerfPercentage = config.getDouble("mechanics.mace-nerf.percentage");
        maceNerfDisabledWorlds = config.getStringList("mechanics.mace-nerf.disabled-worlds");

        //spear nerf
        spearNerfEnabled = config.getBoolean("mechanics.spear-nerf.enabled");
        spearMaxDamage = config.getDouble("mechanics.spear-nerf.max-damage");
        spearNerfPercentage = config.getDouble("mechanics.spear-nerf.percentage");
        spearCooldown = config.getInt("mechanics.spear-nerf.cooldown");
        spearNerfDisabledWorlds = config.getStringList("mechanics.spear-nerf.disabled-worlds");

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
        griefProtectionTntMinecraftEnabled = config.getBoolean("mechanics.grief-protection.block-tnt-minecart");

        furnaceFuelNerfEnabled = config.getBoolean("mechanics.furnace-fuel-nerf.enabled");
        netherWitherEnabled = config.getBoolean("mechanics.nether-wither.enabled");

        disableEqDropsForSpawnerMobs = config.getBoolean("mechanics.disable-equipment-drops-from-spawner-mobs.enabled");

        infinityFoodEnabled = config.getBoolean("mechanics.infinity-food.enabled");
        itemsFoodMap = loadInfinityFood(config);

        villagersRandomNamesEnabled = config.getBoolean("mechanics.villagers-random-names.enabled");
        villagersRandomNames = config.getStringList("mechanics.villagers-random-names.names");
    }
    private Map<String, InfinityFood> loadInfinityFood(FileConfiguration config) {
        Map<String, InfinityFood> infinityFoodsMap = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("mechanics.infinity-food.items");

        if(section == null){
            warn("infinity-food section not found");
            return infinityFoodsMap;
        }

        for (String key : section.getKeys(false)) {
            ConfigurationSection typeSection = section.getConfigurationSection(key);

            if (typeSection == null) {
                continue;
            }

            InfinityFood infinityFood = new InfinityFood();

            String id = typeSection.getString("id");

            infinityFood.setId(id);
            infinityFood.setCooldown(typeSection.getInt("cooldown"));

            infinityFoodsMap.put(id, infinityFood);
        }

        return infinityFoodsMap;
    }

    private List<SpawnerType> loadSpawnerTypes(FileConfiguration config) {
        List<SpawnerType> spawners = new ArrayList<>();

        ConfigurationSection section = config.getConfigurationSection("mechanics.mine-spawners.types");
        if (section == null) {
            warn("spawner-types section not found");
            return spawners;
        }

        for (String key : section.getKeys(false)) {
            ConfigurationSection typeSection = section.getConfigurationSection(key);
            if (typeSection == null) {
                continue;
            }

            SpawnerType spawnerType = new SpawnerType();
            spawnerType.setSilkTouch(typeSection.getBoolean("silk-touch"));
            spawnerType.setChance(typeSection.getDouble("chance"));
            spawnerType.setTools(typeSection.getStringList("tools"));
            spawnerType.setWorlds(new HashSet<>(typeSection.getStringList("worlds")));

            spawners.add(spawnerType);
        }
        return spawners;
    }

    public String getRandomVillagerName() {
        return villagersRandomNames.get(random.nextInt(villagersRandomNames.size()));
    }

    public boolean isDisabledMaceNerfWorld(String worldName) {
        return maceNerfDisabledWorlds.stream().anyMatch(s -> s.equals(worldName));
    }

    public boolean isDisabledSpearNerfWorld(String worldName) {
        return spearNerfDisabledWorlds.stream().anyMatch(s -> s.equals(worldName));
    }

    public boolean isWorldDisabledForKeepItems(String worldName) {
        return disableKeepItemsWorlds.stream().anyMatch(s -> s.equals(worldName));
    }
}
