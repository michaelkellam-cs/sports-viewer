package me.michaelkellam.sportsviewer.fetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;

public class NBAFetcher extends Fetcher {

    private String scoreUrl;

    public NBAFetcher() throws Exception {
        super("http://data.nba.net/10s/prod/v1/_/scoreboard.json", '_');
        params = new String[]{LocalDate.now().toString().replace("-", "")};
//        params = new String[]{"20220210"};
        resetUrlObject(url.toString(), blankPlaceHolder, params);
    }

    public String fetchCurrentDayMatchups() {

        try {
//            resetUrlObject("http://data.nba.net/10s/prod/v1/_/scoreboard.json", '_', new String[] {LocalDate.now().toString().replace("-", "")});
            resetUrlObject("http://data.nba.net/10s/prod/v1/_/scoreboard.json", '_',
                            new String[] {LocalDate.now().toString().replace("-", "")});

            String data = fetchData();
            return data;
        } catch (MalformedURLException e) {
            return "BAD";
        } catch (IOException e) {
            return "BAD";
        }
    }

}
