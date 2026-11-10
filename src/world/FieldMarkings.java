package world;

/**
 * Measurements used to construct the markings for the field.
 * All distances are in centimeters (cm) for consistency.
 */

public class FieldMarkings {

    private final double centreCircleRadius = 915.0;
    private final double penaltyArcRadius = 915.0;
    private final double penaltySpotDistance = 1100.0;
    private final double penaltyAreaDepth = 1650.0;
    private final double goalAreaDepth = 550.0;
    private final double cornerArcRadius = 100.0;

    public double getCentreCircleRadius() {
        return centreCircleRadius;
    }

    public double getPenaltyArcRadius() {
        return penaltyArcRadius;
    }

    public double getPenaltySpotDistance() {
        return penaltySpotDistance;
    }

    public double getPenaltyAreaDepth() {
        return penaltyAreaDepth;
    }

    public double getGoalAreaDepth() {
        return goalAreaDepth;
    }

    public double getCornerArcRadius() {
        return cornerArcRadius;
    }
}
