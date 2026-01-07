import java.util.ArrayList;

import java.util.Collection;

/**
 * Represents a team with unique team details such as acronym, winpercentage,
 * and that team's roster
 * 
 * Helps organize players for team filter function
 * 
 * @author Justin & Joel
 */
public class Team {
    private String teamName;
    private String teamAcronym;
    private double teamWinPercentage;
    private ArrayList<Player> roster;

    /**
     * Description: Constructs a Team object
     * 
     * @param teamName          Team's Full Name
     * @param teamAcronym       Team's Acronym
     * @param teamWinPercentage Team's Winpercentage
     * @author Justin 
     */
    public Team(String teamName, String teamAcronym, double teamWinPercentage) {
        this.teamName = teamName;
        this.teamAcronym = teamAcronym;
        this.teamWinPercentage = teamWinPercentage;

        roster = new ArrayList<Player>();
    }

    /**
     * Description: Adds a player to the roster
     * 
     * @param player Adds this player to roster
     * @author Justin 
     */
    public void addPlayer(Player player) {
        roster.add(player);
    }

    /**
     * Description: Searches the team roster for a matching player using a string
     * 
     * @param playerName Search for this player's name
     * 
     * @return Returns that player as an object
     * @author Joel Menezes
     */
    public Player findPlayer(String playerName) {
        for (Player player : roster)
            if (player.getName().equals(playerName))
                return player;

        return null;

    }

    /**
     * Description: Returns team's full name
     * 
     * @return team full name
     * @author Justin 
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Description: Returns team's acronym
     * 
     * @return team's acronym
     * @author Justin 
     */
    public String getTeamAcronym() {
        return teamAcronym;
    }

    /**
     * Description: Returns team's win percentage
     * 
     * @return team's win percentage
     * @author Justin 
     */
    public double getTeamWinPercentage() {
        return teamWinPercentage;
    }

    /**
     * Description: Returns team's roster as an arraylist
     * 
     * @return team roster
     * @author Justin 
     */
    public ArrayList<Player> getRoster() {
        return roster;
    }

    /**
     * Description: Gets top 3 players on the team, 1 from regular season and 2 from
     * playoffs.
     * Checks to ensure that the regular season player doesn't duplicate and show up
     * as a playoff player
     * 
     * @return Arrayist of top 3 players
     * @author Justin 
     */
    public ArrayList<Player> getTop3PPG() {
        ArrayList<Player> sortedRegular = new ArrayList<>(roster);
        ArrayList<Player> sortedPlayoff = new ArrayList<>(roster);
        PlayerSorter.sortByRegularSeasonPPG(sortedRegular);
        PlayerSorter.sortByPlayoffSeasonPPG(sortedPlayoff);

        ArrayList<Player> top3 = new ArrayList<>();

        // Add highest player from regular season
        if (!sortedRegular.isEmpty()) {
            top3.add(sortedRegular.get(0));
        }

        // Add top 2 people from playoffs without duplicating player from regular season
        int needed = 3 - top3.size();
        int added = 0;
        for (int i = 0; i < sortedPlayoff.size() && added < needed; i++) {
            Player playoffCandidate = sortedPlayoff.get(i);
            boolean alreadyPresent = false;
            for (Player p : top3) {
                if (p.getName().equals(playoffCandidate.getName())) {
                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {
                top3.add(playoffCandidate);
                added++;
            }
        }
        return top3;
    }
}