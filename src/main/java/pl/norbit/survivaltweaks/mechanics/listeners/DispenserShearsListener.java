package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

import java.util.concurrent.ThreadLocalRandom;

public class DispenserShearsListener implements Listener {

    @EventHandler
    public void onDispense(BlockDispenseEvent e){
        if(MechanicsLoader.isDisabled(Mechanic.DISPENSER_BERRY_HARVEST)){
            return;
        }

        ItemStack item = e.getItem();

        if (item.getType() != Material.SHEARS) {
            return;
        }

        Block dispenserBlock = e.getBlock();

        if (!(dispenserBlock.getBlockData() instanceof Directional directional)) {
            return;
        }

        Block target = dispenserBlock.getRelative(directional.getFacing());

        if (target.getType() == Material.SWEET_BERRY_BUSH) {

            if (!(target.getBlockData() instanceof Ageable ageable)) {
                return;
            }

            if (ageable.getAge() < 2) {
                return;
            }

            int amount = ThreadLocalRandom.current().nextInt(1, 4);

            target.getWorld().dropItemNaturally(
                    target.getLocation().add(0.5, 0.2, 0.5),
                    new ItemStack(Material.SWEET_BERRIES, amount)
            );

            ageable.setAge(1);
            target.setBlockData(ageable, false);
        }else if (target.getType() == Material.CAVE_VINES
                || target.getType() == Material.CAVE_VINES_PLANT) {

            if (!(target.getBlockData() instanceof CaveVines caveVines)) {
                return;
            }

            if (!caveVines.isBerries()) {
                return;
            }

            target.getWorld().dropItemNaturally(
                    target.getLocation().add(0.5, 0.2, 0.5),
                    new ItemStack(Material.GLOW_BERRIES, 1)
            );

            caveVines.setBerries(false);
            target.setBlockData(caveVines, false);
        }
    }
}
