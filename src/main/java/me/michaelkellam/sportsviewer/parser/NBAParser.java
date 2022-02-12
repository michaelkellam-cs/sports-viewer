package me.michaelkellam.sportsviewer.parser;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.michaelkellam.sportsviewer.fetcher.NBAFetcher;
import me.michaelkellam.sportsviewer.mapper.NBAMapper;
import me.michaelkellam.sportsviewer.matchups.NBAMatchup;
import me.michaelkellam.sportsviewer.teams.NBATeam;

public class NBAParser {

    private NBAFetcher nbaFetcher;

    public NBAParser() throws Exception {
        nbaFetcher = new NBAFetcher();

    }

    public NBAMatchup getMatchupByTeamOnCurrentDay(String team) {
        String currentDayMatchups = nbaFetcher.fetchCurrentDayMatchups();
        if (currentDayMatchups.equalsIgnoreCase("BAD")) {
            return null;
        }
        try {

            NBATeam team1 = null;
            NBATeam team2 = null;

            JsonObject jsonObject = JsonParser.parseString(currentDayMatchups).getAsJsonObject();
            JsonArray games = jsonObject.get("games").getAsJsonArray();
            int status = -1;
            String[] gameDateTime = new String[2];
            int quarter = 0;
            String timeLeft = "";
            boolean valid = false;
            for (JsonElement game : games) {
                JsonObject gameObj = game.getAsJsonObject();
                JsonObject vTeam = gameObj.get("vTeam").getAsJsonObject();
                if (vTeam.get("triCode").getAsString().equalsIgnoreCase(team)) {
                    team1 = NBAMapper.jsonToTeam(vTeam);
                    JsonObject hTeam = gameObj.get("hTeam").getAsJsonObject();
                    team2 = NBAMapper.jsonToTeam(hTeam);
                    valid = true;
                } else {
                    JsonObject hTeam = gameObj.get("hTeam").getAsJsonObject();
                    if (hTeam.get("triCode").getAsString().equalsIgnoreCase(team)) {
                        team1 = NBAMapper.jsonToTeam(hTeam);
                        team2 = NBAMapper.jsonToTeam(vTeam);
                        valid = true;
                    }
                }

                if (valid) {
                    status = gameObj.get("statusNum").getAsInt();
                    gameDateTime[0] = gameObj.get("homeStartDate").getAsString();
                    gameDateTime[1] = gameObj.get("startTimeEastern").getAsString();
                    quarter = gameObj.get("period").getAsJsonObject().get("current").getAsInt();
                    timeLeft = gameObj.get("clock").getAsString();
                    break;
                }
            }

            if (valid) {
                boolean isGameOver = status == 3;
                boolean isGameActive = status == 2;
                return new NBAMatchup(team1, team2, gameDateTime, isGameOver, isGameActive, quarter, timeLeft);
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
