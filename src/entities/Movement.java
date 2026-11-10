package entities;

// this class will hold movement data.
// Such things as speed, acceleration, and direction will be stored here.
// For consistency, centimeters and seconds will be used.

public class Movement {

    private double velocityX = 0.0;
    private double velocityY = 0.0;
    private double velocityZ = 0.0;

    private final double maxSpeed;
    private final double maxAcceleration;

    public Movement(double maxSpeed, double maxAcceleration) {
        if (!Double.isFinite(maxSpeed) || maxSpeed < 0.0
                || !Double.isFinite(maxAcceleration)
                || maxAcceleration < 0.0) {
            throw new IllegalArgumentException("Movement limits must be finite and non-negative!");
        }

        this.maxSpeed = maxSpeed;
        this.maxAcceleration = maxAcceleration;
    }

    // Code for updating the Velocity (Speed/Direction) of the entity.

    public void updateVelocity(double desiredVelocityX, double desiredVelocityY, double desiredVelocityZ,
            double deltaSeconds) {

        if (deltaSeconds <= 0.0) {
            return;
        }

        double targetSpeed = Math.sqrt(desiredVelocityX * desiredVelocityX + desiredVelocityY * desiredVelocityY
                + desiredVelocityZ * desiredVelocityZ);

        if (targetSpeed > maxSpeed) {
            double scale = maxSpeed / targetSpeed;
            desiredVelocityX *= scale;
            desiredVelocityY *= scale;
            desiredVelocityZ *= scale;
        }

        double changeX = desiredVelocityX - velocityX;
        double changeY = desiredVelocityY - velocityY;
        double changeZ = desiredVelocityZ - velocityZ;

        double changeMagnitude = Math.sqrt(changeX * changeX + changeY * changeY + changeZ * changeZ);

        double maxChange = maxAcceleration * deltaSeconds;

        if (changeMagnitude > maxChange) {
            double scale = maxChange / changeMagnitude;
            changeX *= scale;
            changeY *= scale;
            changeZ *= scale;
        }

        velocityX += changeX;
        velocityY += changeY;
        velocityZ += changeZ;

    }

    public void stop() {
        velocityX = 0.0;
        velocityY = 0.0;
        velocityZ = 0.0;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getVelocityZ() {
        return velocityZ;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }
}
