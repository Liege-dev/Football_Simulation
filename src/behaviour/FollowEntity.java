package behaviour;

import entities.Entity;
import entities.Player;

// allows one entity to follow another, whether that be ball, player, referee... anything!

public class FollowEntity implements MovementBehaviour {

    private final Entity target;

    public FollowEntity(Entity target) {
        this.target = target;
    }

    @Override
    public void update(Player player, double deltaSeconds) {
        player.moveTowards(
                target.getXPos(),
                target.getYPos(),
                deltaSeconds);
    }

}
