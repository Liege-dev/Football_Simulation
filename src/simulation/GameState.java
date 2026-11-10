package simulation;

import entities.Ball;
import entities.Team;

public class GameState {

    private Team redTeam;
    private Team blueTeam;
    private Ball ball;

    private Score score;

    private int elapsedTime;

    private boolean running;
    private boolean paused;

    private MatchPhase currentPhase;

    public GameState(Team redTeam, Team blueTeam, Ball ball) {

        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.ball = ball;

        this.score = new Score();

        this.elapsedTime = 0;

        this.running = false;
        this.paused = false;

        this.currentPhase = MatchPhase.PRE_GAME;
    }

    public Team getRedTeam() {
        return redTeam;
    }

    public Team getBlueTeam() {
        return blueTeam;
    }

    public Ball getBall() {
        return ball;
    }

    public Score getScore() {
        return score;
    }

    public int getRedScore() {
        return score.getRedScore();
    }

    public int getBlueScore() {
        return score.getBlueScore();
    }

    public void addRedGoal() {
        score.addRedGoal();
    }

    public void addBlueGoal() {
        score.addBlueGoal();
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void updateTime(int seconds) {
        if (running && !paused && seconds > 0) {
            elapsedTime += seconds;
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

    public void startMatch() {
        running = true;
        paused = false;
        currentPhase = MatchPhase.FIRST_HALF;
    }

    public void pauseMatch() {
        if (running) {
            paused = true;
        }
    }

    public void resumeMatch() {
        if (running) {
            paused = false;
        }
    }

    public void endMatch() {
        running = false;
        paused = false;
        currentPhase = MatchPhase.POST_GAME;
    }

    public MatchPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(MatchPhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public void reset() {

        score.reset();

        elapsedTime = 0;

        running = false;
        paused = false;

        currentPhase = MatchPhase.PRE_GAME;
    }

    public String toString() {
        return "GameState{" +
                "score=" + score +
                ", elapsedTime=" + elapsedTime +
                ", running=" + running +
                ", paused=" + paused +
                ", currentPhase=" + currentPhase +
                '}';
    }
}