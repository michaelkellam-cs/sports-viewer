package me.michaelkellam.sportsviewer.teams;

public class NBATeam {

    private String teamName;

    private boolean hasGameToday;
    private boolean isGameOver;
    private boolean isGameActive;

    private int wins;
    private int losses;
    private int score;

    public NBATeam(String teamName, boolean hasGameToday, boolean isGameOver, boolean isGameActive, int wins, int losses, int score) {
        this.teamName = teamName;
        this.hasGameToday = hasGameToday;
        this.isGameOver = isGameOver;
        this.isGameActive = isGameActive;
        this.wins = wins;
        this.losses = losses;
        this.score = score;
    }

    public int getScore() {
        if (isGameActive) {
            return score;
        }
        else return -1;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamSummary() {
        return teamName + " (" + wins + "-" + losses + ")";
    }
}
