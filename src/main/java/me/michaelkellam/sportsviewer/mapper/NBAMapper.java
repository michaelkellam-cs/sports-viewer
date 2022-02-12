package me.michaelkellam.sportsviewer.mapper;

import com.google.gson.JsonObject;
import me.michaelkellam.sportsviewer.config.TeamsConfig;
import me.michaelkellam.sportsviewer.teams.NBATeam;

public class NBAMapper {

    public static NBATeam jsonToTeam(JsonObject team) {
        String triCode = team.get("triCode").getAsString();

        String teamName = TeamsConfig.getTeamNameByTriCode(triCode);
        int wins = Integer.parseInt(team.get("win").getAsString());
        int losses = Integer.parseInt(team.get("loss").getAsString());
        String strScore = team.get("score").getAsString();
        int score = strScore.isEmpty() ? 0 : Integer.parseInt(strScore);

        return new NBATeam(teamName, true, true, true, wins, losses, score);
    }
}
