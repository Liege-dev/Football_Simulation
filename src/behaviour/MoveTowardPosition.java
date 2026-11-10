package behaviour;

import entities.Player;

// Responsible for storing destination's X/Y(/Z?) coordinates. 

public class MoveTowardPosition implements MovementBehaviour {

    private final double targetX;
    private final double targetY;

    public MoveTowardPosition(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        player.moveTowards(targetX, targetY, deltaSeconds);
    }

}
