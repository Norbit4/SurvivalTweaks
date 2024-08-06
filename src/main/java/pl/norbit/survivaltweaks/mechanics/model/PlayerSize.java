package pl.norbit.survivaltweaks.mechanics.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlayerSize {
    NORMAL(1.0, 0.4, 0.5, 20),
    SMALL(0.5, 0.4, 0.5, 14),;

    private final double size;
    private final double jumpStrength;
    private final double stepHeight;
    private final double health;
}
