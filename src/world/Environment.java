package world;

import java.awt.Color;

public class Environment {

    private Terrain playingTerrain;
    private Terrain surroundingTerrain;

    public Environment(){
        // Kept your custom UI colors, added the new 3D rolling deceleration physics values
        playingTerrain = new Terrain("Grass", new Color(50, 168, 82), 2.0);
        surroundingTerrain = new Terrain("Surrounding Grass", new Color(0, 110, 0), 5.0);
    }

    public Terrain getPlayingTerrain() {
        return playingTerrain;
    }

    public Terrain getSurroundingTerrain() {
        return surroundingTerrain;
    }
}