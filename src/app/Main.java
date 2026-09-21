package app;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer; // added to try get the ball to Update every n milliseconds

import entities.Ball;
import entities.Player;
import entities.TeamComp;
import entities.Team;
import ui.SimulationPanel;

import world.GameMap;
import world.PlaySpace;
import world.MapPreset;

import java.io.IOException;
import entities.TeamLoader;

public class Main {

        public static void main(String[] args) {

                GameMap map = new GameMap(MapPreset.DEFAULT);
                PlaySpace playSpace = map.getPlaySpace();

                Frame frame = new Frame("Football Simulation");

                Player[] redPlayers = loadTeamOrDefault("red-team.txt", playSpace, true);

                Player[] bluePlayers = loadTeamOrDefault("blue-team.txt", playSpace, false);

                Team redTeam = new Team("Red Team", Color.RED, redPlayers);
                Team blueTeam = new Team("Blue Team", Color.BLUE, bluePlayers);

                Ball ball = new Ball(
                                playSpace.getWidth() / 2,
                                playSpace.getHeight() / 2);

                for (Player player : redPlayers) {
                        player.setMatchDetails(ball, redTeam);
                }

                for (Player player : bluePlayers) {
                        player.setMatchDetails(ball, blueTeam);
                }

                SimulationPanel panel = new SimulationPanel(
                                map,
                                redTeam,
                                blueTeam,
                                ball);

                panel.setPreferredSize(
                                new Dimension(
                                                (int) (playSpace.getWidth() + map.getMarginX() * 2),
                                                (int) (playSpace.getHeight() + map.getMarginY() * 2)));

                frame.add(panel);
                frame.pack();
                frame.setVisible(true);

                Timer timer = new Timer(1000, e -> {
                        Player attacker;

                        if (Math.random() < 0.5) {
                                attacker = redPlayers[5];
                        } else {
                                attacker = bluePlayers[5];
                        }

                        ball.moveTo(attacker.getXPos(), attacker.getYPos());
                        panel.repaint();
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

                passingTimer.start();

                frame.addWindowListener(
                                new WindowAdapter() {
                                        public void windowClosing(WindowEvent we) {
                                                System.exit(0);
                                        }
                                });
        }

        private static Player[] loadTeamOrDefault(
                        String filename,
                        PlaySpace playSpace,
                        boolean leftSide) {

                try {
                        return TeamLoader.loadTeam(filename, playSpace, leftSide);
                } catch (IOException | IllegalArgumentException e) {
                        System.out.println(
                                        "Could not load " + filename + ": " + e.getMessage());
                        System.out.println("Using the default team.");
                }

                if (leftSide) {
                        return TeamComp.createLeftTeam(playSpace);
                } else {
                        return TeamComp.createRightTeam(playSpace);
                }
        }
}