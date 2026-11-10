package app;

import java.awt.*;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.Timer;

import entities.Ball;
import entities.Player;
import entities.Team;
import entities.TeamComp;
import entities.TeamLoader;

import ui.SimulationPanel;

import world.GameMap;
import world.MapPreset;
import world.PlaySpace;
import world.Terrain;

public class Main {

    public static void main(String[] args) {

        GameMap map = new GameMap(MapPreset.DEFAULT);
        PlaySpace playSpace = map.getPlaySpace();

        Player[] redPlayers = loadTeamOrDefault("teams/presets/average.txt", playSpace, true);
        Player[] bluePlayers = loadTeamOrDefault("teams/presets/average.txt", playSpace, false);
        Team redTeam = new Team("Red Team", Color.RED, redPlayers);
        Team blueTeam = new Team("Blue Team", Color.BLUE, bluePlayers);

        Ball ball = new Ball(
                playSpace.getLength() / 2,
                playSpace.getWidth() / 2,
                0.0);

        for (Player player : redPlayers) {
            player.setMatchDetails(ball, redTeam, blueTeam);
            player.initialiseDefaultBehaviour(playSpace, true);
        }

        for (Player player : bluePlayers) {
            player.setMatchDetails(ball, blueTeam, redTeam);
            player.initialiseDefaultBehaviour(playSpace, false);
        }

        EventQueue.invokeLater(() -> { 
            // Ensures that the GUI is created on the Event Dispatch Thread (EDT)
            JFrame frame = new JFrame("Football Simulation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            SimulationPanel panel = new SimulationPanel(
                    map,
                    redTeam,
                    blueTeam,
                    ball);

            // Set the preferred size of the panel to 1280x720 pixels due to map scaling issues
            panel.setPreferredSize(new Dimension(1280, 720)); 

            frame.add(panel);
            frame.pack();
            frame.setVisible(true);

            SimulationSetup.setupAttackingDemo(ball, redPlayers, bluePlayers, map);
            blueTeam.assignChallenger(ball, map, 200.0, playSpace.getWidth() / 2);

            Timer timer = new Timer(1000, e -> {
                Player attacker;
                if (Math.random() < 0.5) {
                    attacker = redPlayers[5];
                } else {
                    attacker = bluePlayers[5];
                }
                ball.moveTo(attacker.getXPos(), attacker.getYPos(), 0.0);
            });
            timer.setRepeats(false);
            timer.start();

            Timer passingTimer = new Timer(2000, e -> {
                Player ballHolder = null;
                for (Player player : redPlayers) {
                    if (player.hasPossession(ball)) {
                        ballHolder = player;
                        break;
                    }
                }
                if (ballHolder != null) {
                    ballHolder.pass();
                }
            });
            passingTimer.start();

            Timer movementTimer = new Timer(20, e -> {
                for (Player player : redPlayers) {
                    player.update(0.02);
                }

                for (Player player : bluePlayers) {
                    player.update(0.02);
                }

                Terrain surface;

                if (playSpace.contains(ball.getXPos(), ball.getYPos())) {
                    surface = map.getEnvironment().getPlayingTerrain();
                } else {
                    surface = map.getEnvironment().getSurroundingTerrain();
                }

                // Update the ball's position every 20 milliseconds (0.02 seconds)
                ball.update(0.02, surface.getRollingDeceleration());

                boolean goalScored = SimulationSetup.isBallInGoal(ball, playSpace.getLeftGoal())
                        || SimulationSetup.isBallInGoal(ball, playSpace.getRightGoal());

                if (goalScored) {
                    blueTeam.releaseChallenger();
                    SimulationSetup.resetDemo(ball, redPlayers, bluePlayers, map);
                    blueTeam.assignChallenger(ball, map, 200.0, playSpace.getWidth() / 2);
                    System.out.println("Goal! Restarting simulation.");
                }
                panel.repaint();
            });
            movementTimer.start();
        });
    }

    private static Player[] loadTeamOrDefault(
            String filename,
            PlaySpace playSpace,
            boolean leftSide) {

        try {
            return TeamLoader.loadTeam(filename, playSpace, leftSide);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not load " + filename + ": " + e.getMessage());
            System.out.println("Using the default team.");
        }

        if (leftSide) {
            return TeamComp.createLeftTeam(playSpace);
        } else {
            return TeamComp.createRightTeam(playSpace);
        }
    }
}