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

        // e.g. "02:15  Red Team - #6 Attacker"  or  "...(own goal by #2)"
    public String toString() {

        String clock = String.format("%02d:%02d", timeScored / 60, timeScored % 60);
        String teamName = scoringTeam == null ? "Unknown" : scoringTeam.getName();

        if (scorer == null) {
            return clock + "  " + teamName;
        }

        String who = "#" + scorer.getNumber() + " " + scorer.getClass().getSimpleName();

        if (ownGoal) {
            return clock + "  " + teamName + " (own goal by " + who + ")";
        }

        return clock + "  " + teamName + " - " + who;
    }
}