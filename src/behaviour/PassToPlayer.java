package behaviour;

import entities.Ball;
import entities.Player;
import world.GameMap;
import world.Terrain;

public class PassToPlayer implements MovementBehaviour {

    private final Ball ball;
    private final Player receiver;
    private final GameMap map;
    private boolean passed = false;

    public PassToPlayer(Ball ball, Player receiver, GameMap map) {
        this.ball = ball;
        this.receiver = receiver;
        this.map = map;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        if (passed) {
            player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);
            return;
        }

        Terrain surface;

        if (map.getPlaySpace().contains(
                ball.getXPos(), ball.getYPos())) {
            surface = map.getEnvironment().getPlayingTerrain();
        } else {
            surface = map.getEnvironment().getSurroundingTerrain();
        }

        passed = player.tryPassTo(
                receiver,
                surface.getRollingDeceleration());

        if (passed) {
            player.updateMovement(0.0, 0.0, 0.0, deltaSeconds);
        } else {
            player.moveTowards(
                    ball.getXPos(),
                    ball.getYPos(),
                    deltaSeconds);
        }

    }

}
