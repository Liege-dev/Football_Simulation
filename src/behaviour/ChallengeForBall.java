package behaviour;

import entities.Ball;
import entities.Player;
import world.GameMap;

public class ChallengeForBall implements MovementBehaviour {

    private final Ball ball;
    private final GameMap map;
    private final double counterattackX;
    private final double counterattackY;

    public ChallengeForBall(
            Ball ball,
            GameMap map,
            double counterattackX,
            double counterattackY) {
        this.ball = ball;
        this.map = map;
        this.counterattackX = counterattackX;
        this.counterattackY = counterattackY;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        if (player.tryControlBall()) {
            player.setMovementBehaviour(
                new AttackWithBall(
                    ball,
                    map,
                    counterattackX,
                    counterattackY));

            player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);

        } else {
            player.arriveTowards(
                    ball.getXPos(),
                    ball.getYPos(),
                    deltaSeconds);
        }
    }

}
