package simulation;

import entities.Ball;
import entities.Player;
import entities.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Match - the SUBJECT in the Observer pattern.
 *
 * Match owns the rules and the state, and announces events to every
 * MatchListener that subscribed. It never calls the scoreboard or the
 * coaches directly - it only knows "a list of listeners".
 */
public class Match {

    private Team redTeam;
    private Team blueTeam;
    private Ball ball;

    private GameState gameState;
    private Ruleset ruleset;

    private final List<GoalEvent> goalEvents = new ArrayList<>();
    private final List<MatchListener> listeners = new ArrayList<>();

    public Match(Team redTeam, Team blueTeam, Ball ball) {
        this(redTeam, blueTeam, ball, new Ruleset());
    }

    public Match(Team redTeam, Team blueTeam, Ball ball, Ruleset ruleset) {

        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.ball = ball;

        this.gameState = new GameState(
                redTeam,
                blueTeam,
                ball);
        this.ruleset = ruleset;
    }

    // ---------- Observer: subscribe / unsubscribe ----------

    public void addListener(MatchListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener cannot be null.");
        }
        listeners.add(listener);
    }

    public void removeListener(MatchListener listener) {
        listeners.remove(listener);
    }

    // ---------- Match lifecycle ----------

    public void start() {

        if (!gameState.isRunning()) {
            gameState.startMatch();
            listeners.forEach(listener -> listener.onMatchStart(gameState));
        }
    }

    public void pause() {

        if (gameState.isRunning() && !gameState.isPaused()) {
            gameState.pauseMatch();
        }
    }

    public void resume() {

        if (gameState.isRunning() && gameState.isPaused()) {
            gameState.resumeMatch();
        }
    }

    public void end() {

        if (gameState.isRunning()) {
            gameState.endMatch();
            listeners.forEach(listener -> listener.onMatchEnd(gameState));
        }
    }

    // Called once per simulated second. Moves the clock and the match phase.
    public void update(int seconds) {

        if (!gameState.isRunning() || gameState.isPaused()) {
            return;
        }

        gameState.updateTime(seconds);

        int elapsed = gameState.getElapsedTime();
        int firstHalf = ruleset.getFirstHalfDuration();
        int fullTime = firstHalf + ruleset.getSecondHalfDuration();

        if (gameState.getCurrentPhase() == MatchPhase.FIRST_HALF && elapsed >= firstHalf) {
            changePhase(MatchPhase.SECOND_HALF);
        }

        if (elapsed >= fullTime) {
            end();
        }
    }

    private void changePhase(MatchPhase newPhase) {
        gameState.setCurrentPhase(newPhase);
        listeners.forEach(listener -> listener.onPhaseChange(newPhase, gameState));
    }

    public void recordGoal(
            Player scorer,
            Team scoringTeam,
            Player assister,
            boolean ownGoal) {

        if (!gameState.isRunning() || gameState.isPaused()) {
            return;
        }

        if (scoringTeam == null
                || (scoringTeam != redTeam && scoringTeam != blueTeam)) {
            throw new IllegalArgumentException("Invalid scoring team.");
        }

        GoalEvent goalEvent = new GoalEvent(
                scorer,
                scoringTeam,
                assister,
                gameState.getElapsedTime(),
                ownGoal);

        goalEvents.add(goalEvent);

        if (scoringTeam == redTeam) {
            gameState.addRedGoal();
        } else {
            gameState.addBlueGoal();
        }

        // Tell every observer. Match doesn't care who they are.
        listeners.forEach(listener -> listener.onGoal(goalEvent, gameState));
    }

    public void reset() {

        gameState.reset();
        goalEvents.clear();
    }

    public GameState getGameState() {
        return gameState;
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

    public Ruleset getRuleset() {
        return ruleset;
    }

    // Read-only view: other classes can look at the goals but not change them.
    public List<GoalEvent> getGoalEvents() {
        return Collections.unmodifiableList(goalEvents);
    }
}