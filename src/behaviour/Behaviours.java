package behaviour;

import entities.Ball;
import entities.Entity;
import world.Goal;

/*
 * Behaviours - a small "menu" of simple movement behaviours, built with lambdas.
 *
 * Before: each of these was its own class (MoveTowardPosition, FollowEntity,
 * GuardGoal, HoldFormation). Each class was ~20 lines of boilerplate:
 * fields, a constructor, and ONE method that did the real work.
 *
 * Because MovementBehaviour has exactly one abstract method, it is a
 * functional interface. That means a lambda can BE a MovementBehaviour.
 * The values a class used to store in fields (target, ball, goal...) are
 * now captured by the lambda from the method's parameters.
 *
 * Kept as classes on purpose: AttackWithBall, ChallengeForBall,
 * DribbleTowardsPosition, PassToPlayer and ReceiveBall. Those behaviours
 * remember things between ticks (e.g. "have I passed yet?"), and a lambda
 * cannot hold mutable fields of its own. A class is the better tool there.
 */
public final class Behaviours {

    private Behaviours() {
        // utility class - never instantiated
    }

    // Move straight towards a fixed point on the field.
    public static MovementBehaviour moveTo(double targetX, double targetY) {
        return (player, deltaSeconds) -> player.moveTowards(targetX, targetY, deltaSeconds);
    }

    // Chase any entity - the ball, another player, the referee...
    public static MovementBehaviour follow(Entity target) {
        return (player, deltaSeconds) -> player.moveTowards(
                target.getXPos(),
                target.getYPos(),
                deltaSeconds);
    }

    // Stand on the goal line at positionX and slide sideways to track the ball,
    // but never leave the width of the goal mouth.
    public static MovementBehaviour guardGoal(Ball ball, Goal goal, double positionX) {
        return (player, deltaSeconds) -> {
            double targetY = Math.max(
                    goal.getY(),
                    Math.min(ball.getYPos(), goal.getY() + goal.getWidth()));

            player.arriveTowards(positionX, targetY, deltaSeconds);
        };
    }

    // Stay near an anchor point, but get pulled towards the ball.
    // ballInfluence 0.0 = never move from anchor, 1.0 = stand on the ball.
    public static MovementBehaviour holdFormation(
            Ball ball,
            double anchorX,
            double anchorY,
            double ballInfluence) {

        return (player, deltaSeconds) -> {
            double targetX = anchorX + (ball.getXPos() - anchorX) * ballInfluence;
            double targetY = anchorY + (ball.getYPos() - anchorY) * ballInfluence;

            player.arriveTowards(targetX, targetY, deltaSeconds);
        };
    }
}