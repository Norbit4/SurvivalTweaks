package pl.norbit.survivaltweaks.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;

import java.util.List;

public class TrackCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length < 2){
            commandSender.sendMessage(ChatUtils.format(Config.getMessageTrackUsage(), null));
            return true;
        }

        if(!(commandSender instanceof Player p)){
            commandSender.sendMessage(ChatUtils.format(Config.getMessageOnlyPlayer(), null));
            return true;
        }

        PlayerInventory inv = p.getInventory();

        ItemStack itemInMainHand = inv.getItemInMainHand();
        Material type = itemInMainHand.getType();

        if(type != Material.COMPASS){
            p.sendMessage(ChatUtils.format(Config.getMessageInvalidMaterial(), p));
            return true;
        }

        String x = args[0];
        String z = args[1];

        World world = p.getWorld();
        double y = p.getY();

        try {
            int xInt = Integer.parseInt(x);
            int zInt = Integer.parseInt(z);
            CompassMeta compassMeta = (CompassMeta) itemInMainHand.getItemMeta();

            compassMeta.setLodestoneTracked(false);
            Location loc = new Location(world, xInt, y, zInt);

            compassMeta.setLodestone(loc);

            String displayName = Config.getMessageTrackItem()
                    .replace("{X}", x)
                    .replace("{Z}", z);

            compassMeta.setDisplayName(ChatUtils.format(displayName, null));

            itemInMainHand.setItemMeta(compassMeta);

            String successMessage = Config.getMessageTrackSuccess()
                    .replace("{X}", x)
                    .replace("{Z}", z);

            p.sendMessage(ChatUtils.format(successMessage, p));
        } catch (NumberFormatException e){
            commandSender.sendMessage(ChatUtils.format(Config.getMessageInvalidNumber(), p));
            return true;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1){
            return List.of("X");
        }

        if(args.length == 2){
            return List.of("Z");
        }

        return List.of();
    }
}
