package app;

import behaviour.DribbleTowardsPosition;
import entities.Ball;
import entities.Player;
import world.GameMap;
import world.PlaySpace;
import javax.swing.Timer;
import ui.SimulationPanel;

import behaviour.AttackWithBall;
import world.Goal;

public class SimulationSetup {

    public static void setupDribblingTest(
            Ball ball,
            Player player,
            PlaySpace playSpace,
            GameMap map) {
        ball.stop();
        ball.moveTo(
                player.getXPos() + 70.0,
                player.getYPos(),
                player.getZPos());

        player.setMovementBehaviour(
                new DribbleTowardsPosition(ball, playSpace.getLength() * 0.75, player.getYPos(), map));
    }

    public static void setupRollingTest(
            Ball ball,
            Player player,
            PlaySpace playSpace) {
        player.setMovementBehaviour(null);
        player.resetPosition();

        double startX = playSpace.getLength() / 2;
        double startY = playSpace.getWidth() / 2;

        ball.stop();
        ball.moveTo(startX, startY, 0.0);
        ball.kickTowards(startX + 2000.0, startY, 350.0, player);

        System.out.println("Rolling test starts at X = " + startX);
        System.out.println("Expected Stopping X = " + (startX + 612.5));
    }

    public static void setupLegacyPassingDemo(
            Ball ball,
            Player[] redPlayers,
            Player[] bluePlayers,
            SimulationPanel panel) {

        Timer timer = new Timer(1000, e -> {
            Player attacker;

            if (Math.random() < 0.5) {
                attacker = redPlayers[5];
            } else {
                attacker = bluePlayers[5];
            }

            ball.moveTo(attacker.getXPos(), attacker.getYPos(), attacker.getZPos());
            panel.repaint();
        });

        timer.setRepeats(false);
        // timer.start();

        Timer passingTimer = new Timer(2000, e -> {
            Player ballHolder = null;

            for (Player player : redPlayers) {
                if (player.hasPossession(ball)) {
                    ballHolder = player;
                    break;
                }
            }

            if (ballHolder == null) {
                for (Player player : bluePlayers) {
                    if (player.hasPossession(ball)) {
                        ballHolder = player;
                        break;
                    }
                }
            }

            if (ballHolder != null) {
                ballHolder.pass();
                panel.repaint();
            }
        });
        passingTimer.start(); // <-- This line clears your final warning!
    }

    public static void setupAttackingDemo(
            Ball ball,
            Player[] redPlayers,
            Player[] bluePlayers,
            GameMap map) {
        PlaySpace playSpace = map.getPlaySpace();

        redPlayers[5].setMovementBehaviour(
                new AttackWithBall(ball, map, playSpace.getLength() - 200,
                        playSpace.getWidth() / 2));
    }

    public static boolean isBallInGoal(Ball ball, Goal goal) {
        double radius = ball.getRadiusCm();

        return ball.getXPos() - radius >= goal.getX()
                && ball.getXPos() + radius <= goal.getX() + goal.getDepth()
                && ball.getYPos() - radius >= goal.getY()
                && ball.getYPos() + radius <= goal.getY() + goal.getWidth()
                && ball.getZPos() + radius <= goal.getHeight();
    }

    public static void resetDemo(
            Ball ball,
            Player[] redPlayers,
            Player[] bluePlayers,
            GameMap map) {

        ball.stop();
        ball.moveTo(
                map.getPlaySpace().getLength() / 2,
                map.getPlaySpace().getWidth() / 2,
                0.0);
        ball.recordTouch(null);

        for (Player player : redPlayers) {
            player.resetPosition();
            player.restoreDefaultBehaviour();
        }

        for (Player player : bluePlayers) {
            player.resetPosition();
            player.restoreDefaultBehaviour();
        }

        setupAttackingDemo(ball, redPlayers, bluePlayers, map);
    }

    /*
     * OLD PASSING CODE
     * private static boolean receiveBall(Ball ball, Player[] players) {
     * for (Player player : players) {
     * if (player != ball.getLastKicker() && player.canReceive(ball)) {
     * ball.stop();
     * ball.moveTo(
     * player.getXPos(),
     * player.getYPos(),
     * player.getZPos());
     * return true;
     * }
     * }
     * return false;
     * }
     */
}