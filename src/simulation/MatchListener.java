package simulation;

/*
 * MatchListener - the OBSERVER side of the Observer pattern.
 *
 * Match is the SUBJECT: it knows when something important happens
 * (a goal, kick-off, half-time, full-time) and it tells everyone who
 * subscribed with addListener(). Match does NOT know who is listening
 * or what they will do - a scoreboard, a coach, a console logger...
 * New reactions can be added without touching Match at all.
 *
 * Only onGoal is abstract. The others are default methods, so a listener
 * only overrides what it cares about. Because there is exactly ONE
 * abstract method, this is also a functional interface: a quick listener
 * can be a lambda, e.g. match.addListener((goal, state) -> ...).
 */
@FunctionalInterface
public interface MatchListener {

    void onGoal(GoalEvent goal, GameState state);

    default void onMatchStart(GameState state) {
    }

    default void onPhaseChange(MatchPhase newPhase, GameState state) {
    }

    default void onMatchEnd(GameState state) {
    }
}