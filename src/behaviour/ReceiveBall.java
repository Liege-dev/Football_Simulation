package behaviour;

import entities.Ball;
import entities.Player;

public class ReceiveBall implements MovementBehaviour {

    private final Ball ball;
    private boolean received = false;

    private final Player expectedPasser;
    private final MovementBehaviour afterReceiving;
    private boolean passStarted = false;

    public ReceiveBall(Ball ball, Player expectedPasser) {
        this(ball, expectedPasser, null);
    }

    public ReceiveBall(
            Ball ball,
            Player expectedPasser,
            MovementBehaviour afterReceiving) {
        this.ball = ball;
        this.expectedPasser = expectedPasser;
        this.afterReceiving = afterReceiving;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        Player lastToucher = ball.getLastToucher();

        if (!received
                && lastToucher != null
                && lastToucher != expectedPasser
                && lastToucher != player) {
            player.setMovementBehaviour(
                    new HoldFormation(ball, player.getXPos(), player.getYPos(), 0.2));

            player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);
            return;
        }

        if (!passStarted) {
            if (ball.isMoving() && ball.getLastKicker() == expectedPasser) {
                passStarted = true;
            } else {
                player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);
                return;
            }
        }

        if (!received) {
            received = player.tryControlBall();
        }

        if (received) {
            player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);

            if (afterReceiving != null) {
                player.setMovementBehaviour(afterReceiving);
            }
        } else {
            player.arriveTowards(ball.getXPos(), ball.getYPos(), deltaSeconds);
        }
    }
}
