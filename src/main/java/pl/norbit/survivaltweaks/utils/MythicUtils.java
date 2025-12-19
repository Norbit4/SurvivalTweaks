package pl.norbit.survivaltweaks.utils;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import io.lumine.mythic.core.mobs.MobExecutor;
import org.bukkit.entity.Entity;


public class MythicUtils {
    private static MythicBukkit inst;

    private MythicUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void init(){
        inst = MythicBukkit.inst();
    }

    public static MythicResponse getMobName(Entity entity){
        MobExecutor mobManager = inst.getMobManager();

        ActiveMob mythicMob = mobManager.getMythicMobInstance(entity);

        if (mythicMob != null) {
            String displayName = mythicMob.getDisplayName();

            if(displayName != null && !displayName.isEmpty()) {
                return new MythicResponse(MythicResponseType.SUCCESS, displayName);

            }
            return new MythicResponse(MythicResponseType.WRONG_NAME, null);
        }

        return new MythicResponse(MythicResponseType.NOT_FOUND, null);
    }
}
