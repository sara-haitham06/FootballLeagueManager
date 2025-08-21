package models;

public class Match {
    private Team teamA;
    private Team teamB;
    private League league;
    private int goalsA;
    private int goalsB;
    private boolean played;

    public Match(Team teamA, Team teamB ) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.played = false;
    }

    public Team getTeamA() {
        return teamA; }

    public Team getTeamB() {
        return teamB; }

    public League getLeague() {
        return league;}

    public boolean isPlayed() {
        return played; }

    public int getGoalsA() {
        return goalsA; }

    public int getGoalsB() {
        return goalsB; }

    public void playMatchAuto() {
        goalsA = (int) (Math.random() * 6);
        goalsB = (int) (Math.random() * 6);
        updateTeams();
        played = true;
    }

    public void playMatchManual(int gA, int gB) {
        this.goalsA = gA;
        this.goalsB = gB;
        updateTeams();
        played = true;
    }

    private void updateTeams() {
        teamA.setGoalsFor(teamA.getGoalsFor() + goalsA);
        teamA.setGoalsAgainst(teamA.getGoalsAgainst() + goalsB);
        teamB.setGoalsFor(teamB.getGoalsFor() + goalsB);
        teamB.setGoalsAgainst(teamB.getGoalsAgainst() + goalsA);
        teamA.setMatchPlayed(teamA.getMatchPlayed()+1);
        teamB.setMatchPlayed(teamB.getMatchPlayed()+1);
        if (goalsA > goalsB) {
            teamA.setPts(teamA.getPts() + 3);
        } else if (goalsB > goalsA) {
            teamB.setPts(teamB.getPts() + 3);
        } else {
            teamA.setPts(teamA.getPts() + 1);
            teamB.setPts(teamB.getPts() + 1);
        }
    }

    public String getResult() {
        if (!played) {
            return teamA.getName() + " vs " + teamB.getName() + " Not played";
        }
        return teamA.getName() + " " + goalsA + " - " + goalsB + " " + teamB.getName();
    }
}