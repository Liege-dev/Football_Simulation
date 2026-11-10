package entities;

import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import behaviour.ChallengeForBall;
import behaviour.MovementBehaviour;
import world.GameMap;

public class Team {

    private String name;
    private Color color;
    private Player[] players;
    private Player nominatedChallenger;
    private MovementBehaviour nominatedChallengeBehaviour;

    public Team(String name, Color color, Player[] players) {
        this.name = name;
        this.color = color;
        this.players = players;
    }

    public Optional<Player> chooseChallenger(Ball ball) {
        return Arrays.stream(players).parallel()
                .filter(player -> player != null)
                .filter(player -> !(player instanceof Goalkeeper))
                .min(Comparator.comparingDouble(player -> Math.hypot(
                        player.getXPos() - ball.getXPos(),
                        player.getYPos() - ball.getYPos())));
    }

    public void assignChallenger(
            Ball ball,
            GameMap map,
            double attackTargetX,
            double attackTargetY) {

        releaseChallenger();

        Optional<Player> choice = chooseChallenger(ball);

        if (choice.isPresent()) {
            nominatedChallenger = choice.get();

            nominatedChallengeBehaviour = new ChallengeForBall(
                    ball,
                    map,
                    attackTargetX,
                    attackTargetY);
            nominatedChallenger.setMovementBehaviour(nominatedChallengeBehaviour);
        }
    }

    public void releaseChallenger() {
        if (nominatedChallenger != null
                && nominatedChallenger.getMovementBehaviour() == nominatedChallengeBehaviour) {
            nominatedChallenger.restoreDefaultBehaviour();
        }

        nominatedChallenger = null;
        nominatedChallengeBehaviour = null;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Player[] getPlayers() {
        return players;
    }

}