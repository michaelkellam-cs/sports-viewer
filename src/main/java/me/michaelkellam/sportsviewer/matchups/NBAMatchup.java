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
            return "$YELLOWThe game between the $GOLD" + mainTeam.getTeamSummary() + " $YELLOWand the $GOLD" + otherTeam.getTeamSummary() + " $YELLOWwill start $GOLD" + gameDateTime[0] + " $YELLOWat $GOLD" + gameDateTime[1];
        }

        if (isGameActive) {
            int isWinning = isWinning();
            String status = "";

            switch (isWinning) {
                case -1: {
                    status = "are $YELLOWTIED with";
                    break;
                }
                case 0: {
                    status = "are $REDLOSING" + " $YELLOWto";
                    break;
                }
                case 1: {
                    status = "are $GREENBEATING $YELLOW";
                    break;
                }
            }

            return "$YELLOWThe $GOLD" + mainTeam.getTeamSummary() + " " + status + " " + "the $GOLD" + otherTeam.getTeamSummary() + " $LIGHT_PURPLE" +
                    mainTeam.getScore() + "-" + otherTeam.getScore() +
                    " $YELLOWwith $GOLD" + timeLeft + " $YELLOWto go in $LIGHT_PURPLEQ" + quarter;
        }

        if (isGameOver) {
            int isWinning = isWinning();
            String status = "";

            switch (isWinning) {
                case 0: {
                    status = "$REDLOST $YELLOWto";
                    break;
                }
                case 1: {
                    status = "$GREENDEFEATED $YELLOW";
                }
            }
            return "$YELLOWThe $GOLD" + mainTeam.getTeamSummary() + " " + status + " " + "the $GOLD" + otherTeam.getTeamSummary() + " $LIGHT_PURPLE" + mainTeam.getScore() + "-" + otherTeam.getScore();
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
