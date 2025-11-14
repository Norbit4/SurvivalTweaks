package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncLater;

public class PlayerTotemVoidListener implements Listener {

    private final List<UUID> playersWithTotem = new ArrayList<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.VOID_TOTEM)){
            return;
        }

        if (!(e.getEntity() instanceof Player p)) {
            return;
        }

        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) {
            return;
        }

        UUID playerUUID = p.getUniqueId();

        if(playersWithTotem.contains(playerUUID)){
            e.setCancelled(true);
            return;
        }

        PlayerInventory inventory = p.getInventory();

        if (!removeTotem(inventory)) {
            return;
        }
        e.setCancelled(true);

        addTotemEffect(p);
        sendAnimation(p);

        playersWithTotem.add(playerUUID);

        asyncLater(() -> playersWithTotem.remove(playerUUID), 20 * 15);
    }

    private void sendAnimation(Player p){
        p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 1.0F, 1.0F);
        p.playEffect(EntityEffect.TOTEM_RESURRECT);
    }

    private void addTotemEffect(Player p){
        int effectDuration = 20 * 32;
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, effectDuration, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, effectDuration, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, effectDuration, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effectDuration, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 25, 7));
    }

    private boolean removeTotem(PlayerInventory p){
        ItemStack itemInMainHand = p.getItemInMainHand();

        if(itemInMainHand.getType() == Material.TOTEM_OF_UNDYING){
            p.setItemInMainHand(null);
            return true;
        }

        ItemStack itemInOffHand = p.getItemInOffHand();

        if(itemInOffHand.getType() == Material.TOTEM_OF_UNDYING){
            p.setItemInOffHand(null);
            return true;
        }

        return false;
    }
}
