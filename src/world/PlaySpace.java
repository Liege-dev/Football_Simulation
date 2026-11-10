package world;

/**
 * Describes the legal playing region and it's goal zones.
 * These boundaries inform rule decisions; they do not physically
 * prevent players or the ball from moving outside of them.
 * 
 * World Axis: X = Field Length, Y = Field Width
 * Z = Height (now being used!)
 * 
 * All positions and Dimensions are measured in centimeters (cm).
 * 
 */

public class PlaySpace {

    private double length; // Was "width" but changed to "length" to match the field length terminology
    private double width; // Was "height" but changed to "width" to match the field width terminology

    private Goal leftGoal;
    private Goal rightGoal;

    private final FieldMarkings fieldMarkings;

    public PlaySpace(double length, double width, FieldMarkings fieldMarkings) {
        this.length = length;
        this.width = width;
        this.fieldMarkings = fieldMarkings;

        double goalDepth = 200.0; // Chosen depth of the goal in cm (2 meters), it could be anything really but
                                  // idk what the standard is.
        double goalWidth = 732.0; // Standard goal width in cm (7.32 meters)
        double goalHeight = 244.0; // Standard goal height in cm (2.44 meters)

        double goalY = (width - goalWidth) / 2;

        leftGoal = new Goal(
                -goalDepth,
                goalY,
                goalDepth,
                goalWidth,
                goalHeight);

        rightGoal = new Goal(
                length,
                goalY,
                goalDepth,
                goalWidth,
                goalHeight);
    }

    public boolean contains(double x, double y) {
        return x >= 0.0
                && x <= length
                && y >= 0.0
                && y <= width;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public Goal getLeftGoal() {
        return leftGoal;
    }

    public Goal getRightGoal() {
        return rightGoal;
    }

    public FieldMarkings getFieldMarkings() {
        return fieldMarkings;
    }

}