package me.michaelkellam.sportsviewer.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class TeamsConfig {

    private static HashMap<String, String> teams = new HashMap<>();

    public TeamsConfig() throws IOException {
        InputStream is = TeamsConfig.class.getResourceAsStream("/teams.json");
        assert is != null;
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        is.close();

        JsonArray mainArray = JsonParser.parseString(result).getAsJsonArray();

        for (JsonElement teamElement : mainArray) {
            JsonObject team = teamElement.getAsJsonObject();
            teams.put(team.get("abbreviation").getAsString(), team.get("teamName").getAsString());
        }

    }

    public static String getTeamNameByTriCode(String triCode) {
        return teams.get(triCode);
    }
}
