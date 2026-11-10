package behaviour;

import entities.Ball;
import entities.Player;
import world.GameMap;
import world.Terrain;

// dribbling da ball!

public class DribbleTowardsPosition implements MovementBehaviour {

    private final Ball ball;
    private final double targetX;
    private final double targetY;
    private final GameMap map;

    public DribbleTowardsPosition(
            Ball ball,
            double targetX,
            double targetY,
            GameMap map) {
        this.ball = ball;
        this.targetX = targetX;
        this.targetY = targetY;
        this.map = map;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        double directionX = targetX - ball.getXPos();
        double directionY = targetY - ball.getYPos();
        double remainingDistance = Math.hypot(directionX, directionY);

        // be sure to stop requesting new touches near the destination!

        if (remainingDistance > 50.0) {
            Terrain surface;

            if (map.getPlaySpace().contains(ball.getXPos(), ball.getYPos())) {
                surface = map.getEnvironment().getPlayingTerrain();
            } else {
                surface = map.getEnvironment().getSurroundingTerrain();
            }

            // EACH TOUCH! aims the balls next position on the field.

            double unitX = directionX / remainingDistance;
            double unitY = directionY / remainingDistance;

            // project running velocity into the intended kick direction
            double forwardSpeed = Math.max(
                0.0,
                player.getVelocityX() * unitX
                + player.getVelocityY() * unitY);

                double kickSpeed = forwardSpeed + player.getDribbleLeadSpeed();

                double deceleration = surface.getRollingDeceleration();

                if (deceleration > 0.0) {
                    double stoppingSpeed = player.calculateGroundKickSpeed(remainingDistance, deceleration);

                    kickSpeed = Math.min(kickSpeed, stoppingSpeed);
                }

            player.tryKickTowards(targetX, targetY, kickSpeed);

        }

        if (remainingDistance > 50.0 && player.canTouch(ball)) {
            player.moveTowards(targetX, targetY, deltaSeconds);
        } else {
            player.moveTowards(ball.getXPos(), ball.getYPos(), deltaSeconds);
        }

    }

}
