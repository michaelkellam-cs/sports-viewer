package me.michaelkellam.sportsviewer.matchups;

import me.michaelkellam.sportsviewer.teams.NBATeam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NBAMatchup {

    private NBATeam mainTeam;
    private NBATeam otherTeam;

    // first value is date,
    // second value is time
    private String[] gameDateTime;

    private boolean isGameOver;
    private boolean isGameActive;

    private int quarter;
    private String timeLeft;

    public NBAMatchup(NBATeam mainTeam, NBATeam otherTeam, String[] gameDateTime, boolean isGameOver, boolean isGameActive, int quarter, String timeLeft) throws ParseException {
        this.mainTeam = mainTeam;
        this.otherTeam = otherTeam;
        this.gameDateTime = gameDateTime;
        this.isGameOver = isGameOver;
        this.isGameActive = isGameActive;
        this.quarter = quarter;
        this.timeLeft = timeLeft;

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dateFormat.parse(gameDateTime[0]);
        DateFormat dateFormat2 = new SimpleDateFormat("MMMMM dd, yyyy");
        gameDateTime[0] = dateFormat2.format(date);
    }

    public String statusUpdate() {
        if (!isGameActive && !isGameOver) {
            return "The game between the " + mainTeam.getTeamSummary() + " and the " + otherTeam.getTeamSummary() + " will start " + gameDateTime[0] + " at " + gameDateTime[1];
        }

        if (isGameActive) {
            int isWinning = isWinning();
            String status = "";

            switch (isWinning) {
                case -1: {
                    status = "are TIED with";
                    break;
                }
                case 0: {
                    status = "are LOSING" + " to";
                    break;
                }
                case 1: {
                    status = "are BEATING the";
                    break;
                }
            }

            return "The " + mainTeam.getTeamSummary() + " " + status + " " + "the " + otherTeam.getTeamSummary() + " " +
                    mainTeam.getScore() + "-" + otherTeam.getScore() +
                    " with " + timeLeft + " to go in Q" + quarter;
        }

        if (isGameOver) {
            int isWinning = isWinning();
            String status = "";

            switch (isWinning) {
                case 0: {
                    status = "LOST to";
                    break;
                }
                case 1: {
                    status = "DEFEATED";
                }
            }
            return "The " + mainTeam.getTeamSummary() + " " + status + " " + "the " + otherTeam.getTeamSummary() + " " + mainTeam.getScore() + "-" + otherTeam.getScore();
        }

        return "idk";
    }

    public int getStatus() {
        if (isGameOver)
            return 3;

        if (isGameActive)
            return 2;

        return 1;
    }

    // -1 : tied, 0 : losing, 1 : winning
    private int isWinning() {
        if (mainTeam.getScore() > otherTeam.getScore()) {
            return 1;
        } else if (mainTeam.getScore() < otherTeam.getScore()) {
            return 0;
        }

        return -1;
    }
}
