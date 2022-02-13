package me.michaelkellam.sportsviewer;

import me.michaelkellam.sportsviewer.config.TeamsConfig;
import me.michaelkellam.sportsviewer.matchups.NBAMatchup;
import me.michaelkellam.sportsviewer.parser.NBAParser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class SportsViewer extends JavaPlugin implements CommandExecutor {

    private NBAParser nbaParser;
    private TeamsConfig teamsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Sports Viewer plugin has been enabled.");

        try {
            nbaParser = new NBAParser();
            teamsConfig = new TeamsConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getCommand("add").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Sports Viewer plugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("nba")) {
            try {
                String team = args[0];
                NBAMatchup matchup = nbaParser.getMatchupByTeamOnCurrentDay(team);
                int status = matchup.getStatus();
                String message = matchup.statusUpdate();
//                message = assignColors(message);
                String[] split = message.split(" ");
                String test = ChatColor.YELLOW + assignColors(message);
                sender.sendMessage(test);
                return true;
            } catch (Exception e) {
                System.out.println("Invalid command");
            }
        }

        return false;
    }

    private String assignColors(String message) {
        message = message
                .replace("$YELLOW", "" + ChatColor.YELLOW)
                .replace("$GOLD", "" + ChatColor.GOLD)
                .replace("$GREEN", "" + ChatColor.GREEN)
                .replace("$RED", "" + ChatColor.RED)
                .replace("$LIGHT_PURPLE", "" + ChatColor.LIGHT_PURPLE);

        return message;
    }
//
//    private String coloredText(String[] text, int status) {
//        if (status == 1) {
//
//        } else if (status == 2) {
//            int backIndex = 8;
//            int frontIndex = Arrays.asList(text).indexOf("the");
//
//            String retStr =
//                    text[0];
//        } else {
//
//        }
//    }

}
