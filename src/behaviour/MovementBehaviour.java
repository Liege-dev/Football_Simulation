package behaviour;

import entities.Player;

// helps the players know how they need to move during updates.
// Strategy pattern: a Player holds one MovementBehaviour and can swap it at runtime.
// @FunctionalInterface: exactly one abstract method, so simple behaviours can be
// written as lambdas (see Behaviours.java). The compiler enforces this.

@FunctionalInterface
public interface MovementBehaviour {

    void update(Player player, double deltaSeconds);

}
