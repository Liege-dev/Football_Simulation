package simulation;

import entities.Player;
import entities.Team;

public class GoalEvent {

    private Player scorer;

    private Team scoringTeam;

    private Player assister;

    private int timeScored;

    private boolean ownGoal;

    public GoalEvent(
            Player scorer,
            Team scoringTeam,
            Player assister,
            int timeScored) {

        this.scorer = scorer;
        this.scoringTeam = scoringTeam;
        this.assister = assister;
        this.timeScored = timeScored;
        this.ownGoal = false;
    }

    public GoalEvent(
            Player scorer,
            Team scoringTeam,
            Player assister,
            int timeScored,
            boolean ownGoal) {

        this.scorer = scorer;
        this.scoringTeam = scoringTeam;
        this.assister = assister;
        this.timeScored = timeScored;
        this.ownGoal = ownGoal;
    }

    public Player getScorer() {
        return scorer;
    }

    public Team getScoringTeam() {
        return scoringTeam;
    }

    public Player getAssister() {
        return assister;
    }

    public int getTimeScored() {
        return timeScored;
    }

    public boolean isOwnGoal() {
        return ownGoal;
    }

    public boolean hasAssist() {
        return assister != null;
    }

    public String toString() {

        String result = "Goal scored at " + timeScored + " seconds";

        if (ownGoal) {
            result += " (Own Goal)";
        }

        return result;
    }
}