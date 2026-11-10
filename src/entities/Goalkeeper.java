package entities;

import behaviour.GuardGoal;
import world.PlaySpace;

public class Goalkeeper extends Player {

    public Goalkeeper(int number, double xPos, double yPos, double zPos) {
        super(number, xPos, yPos, zPos);
    }

    public Goalkeeper(
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

        int index = Math.random() < 0.5 ? 1 : 2;
        Player receiver = team.getPlayers()[index];

        // ball.moveTo(receiver.getXPos(), receiver.getYPos(), receiver.getZPos());

        ball.kickTowards(
                receiver.getXPos(),
                receiver.getYPos(),
                1000.0,
                this);
    }

    @Override
    public void configureDefaultBehaviour(
            PlaySpace playSpace, boolean defendingLeft) {

        if (defendingLeft) {
            setMovementBehaviour(
                    new GuardGoal(ball, playSpace.getLeftGoal(), 200.0));

        } else {
            setMovementBehaviour(new GuardGoal(ball, playSpace.getRightGoal(), playSpace.getLength() - 200.0));
        }
    }

}