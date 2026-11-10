package world;

import java.awt.Color;

public class Terrain {

    private String name;
    private Color displayColor;

    private final double rollingDeceleration;

    public Terrain(String name, Color displayColor, double rollingDeceleration) {
        if (!Double.isFinite(rollingDeceleration) || rollingDeceleration < 0.0) {
            throw new IllegalArgumentException(
                    " !! UH OH GAMER !! Rolling deceleration must be a (1) finite, and (2) non-negative value");
        }

        this.name = name;
        this.displayColor = displayColor;
        this.rollingDeceleration = rollingDeceleration;
    }

    public String getName() {
        return name;
    }

    public Color getDisplayColor() {
        return displayColor;
    }

    public double getRollingDeceleration() {
        return rollingDeceleration;
    }

}
