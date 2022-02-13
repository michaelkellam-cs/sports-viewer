package me.michaelkellam.sportsviewer.fetcher;

import me.michaelkellam.sportsviewer.config.TeamsConfig;
import me.michaelkellam.sportsviewer.matchups.NBAMatchup;
import me.michaelkellam.sportsviewer.parser.NBAParser;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class NBAFetcherTest {

    NBAParser nbaParser;
    NBAMatchup nbaMatchup;

    @Before
    public void beforeAllTestMethods() throws Exception {
        nbaParser = new NBAParser();
    }

    @Test
    public void testUrl() throws Exception {
//        NBAFetcher nbaFetcher = new NBAFetcher();
//
//        String str = nbaFetcher.fetchData();
        TeamsConfig config = new TeamsConfig();

        nbaMatchup = nbaParser.getMatchupByTeamOnCurrentDay("NYK");
        String str = nbaMatchup.statusUpdate();
        System.out.println("TEST");
        System.out.println(str);

//        System.out.println(TeamsConfig.getTeamNameByTriCode("NYK"));

    }

}
