package entities;

/**
 * all positions and Dimensions are measured in centimeters (cm).
 */

public class Entity {

    double xPos;
    double yPos;
    double zPos;

    Entity(double xPos, double yPos, double zPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double getZPos() {
        return zPos;
    }

}
