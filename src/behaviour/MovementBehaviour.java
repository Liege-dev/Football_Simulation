package behaviour;

import entities.Player;

// helps the players know how they need to move during updates.

public interface MovementBehaviour {

    void update(Player player, double deltaSeconds);

}
