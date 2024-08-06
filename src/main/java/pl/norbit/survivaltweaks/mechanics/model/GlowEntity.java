package pl.norbit.survivaltweaks.mechanics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class GlowEntity {
    private final Player player;
    private final Entity entity;
    @Setter
    private int duration;
}
