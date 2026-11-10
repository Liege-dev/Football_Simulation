package behaviour;

import entities.Ball;
import entities.Player;

public class HoldFormation implements MovementBehaviour {

    private final Ball ball;
    private final double anchorX;
    private final double anchorY;
    private final double ballInfluence;

    public HoldFormation(
            Ball ball,
            double anchorX,
            double anchorY,
            double ballInfluence) {
        this.ball = ball;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.ballInfluence = ballInfluence;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        double targetX = anchorX
                + (ball.getXPos() - anchorX) * ballInfluence;
        double targetY = anchorY
                + (ball.getYPos() - anchorY) * ballInfluence;

        player.arriveTowards(targetX, targetY, deltaSeconds);
    }

}
