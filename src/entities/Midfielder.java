package entities;

import behaviour.Behaviours;
import world.PlaySpace;

public class Midfielder extends Player {

    public Midfielder(int number, double xPos, double yPos, double zPos) {
        super(number, xPos, yPos, zPos);
    }

    public Midfielder(
            int number,
            double xPos,
            double yPos,
            double zPos,
            double maxSpeed,
            double maxAcceleration) {
        super(number, xPos, yPos, zPos, maxSpeed, maxAcceleration);
    }

    @Override
    public void pass() {
        if (!hasPossession(ball)) {
            return;
        }

        Player receiver = team.getPlayers()[5];

        ball.kickTowards(
                receiver.getXPos(),
                receiver.getYPos(),
                1000.0,
                this);
    }

    @Override
    public void configureDefaultBehaviour(
        PlaySpace playSpace, boolean defendingLeft) {
        setMovementBehaviour(
                Behaviours.holdFormation(
                        ball,
                        spawnX,
                        spawnY,
                        getBaseBallInfluence()));
    }
        @Override
    public double getBaseBallInfluence() {
        return 0.30;   // Midfielder: 0.30   Attacker: 0.40
    }

}