package world;

public class GameMap {

    private PlaySpace playSpace;
    private Environment environment;

    private double marginX; // World Distances in cm, rather than pixels, to allow for scaling of the map
    private double marginY; // World Distances in cm, rather than pixels, to allow for scaling of the map

    public GameMap(MapPreset preset) {

        switch (preset) {

            case FIFA_PRO:
                playSpace = new PlaySpace(10500.0, 6800.0, new FieldMarkings());
                environment = new Environment();
                marginX = 1000.0;
                marginY = 1000.0;
                break;

            case DEFAULT:
            default:
                playSpace = new PlaySpace(10000.0, 6500.0, new FieldMarkings());
                environment = new Environment();
                marginX = 1500.0;
                marginY = 1000.0;
                break;
        }
    }

    public PlaySpace getPlaySpace() {
        return playSpace;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public double getMarginX() {
        return marginX;
    }

    public double getMarginY() {
        return marginY;
    }

}
