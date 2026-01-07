import java.util.ArrayList;
import java.util.Arrays;

public class PlayerSorter {
    // Variables used for sort by Win Percentage
    public static ArrayList<String> TEAM_CODES = new ArrayList<>(
            Arrays.asList("DAL", "VGK", "WPG", "COL", "EDM", "LAK", "VAN", "NSH", "FLA", "TBL", "BOS",
                    "TOR", "NYR", "WSH", "CAR", "NYI"));
    public static ArrayList<Double> TEAM_WINPERCENTAGE = new ArrayList<>(
            Arrays.asList(0.636, 0.566, 0.622, 0.598, 0.640, 0.556, 0.622, 0.588, 0.646, 0.558,
                    0.610, 0.575, 0.667, 0.506, 0.644, 0.541));

    /**
     * Description: Sorts players based on Playoff PPG. Bubble Sort Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortByPlayoffSeasonPPG(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players.get(j).getPlayoffStats()
                        .getPPGCompare() < players.get(j + 1).getPlayoffStats().getPPGCompare()) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Description: Sorts Players based on Regular Season PPG. Bubble Sort Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortByRegularSeasonPPG(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players.get(j).getRegularStats()
                        .getPPGCompare() < players.get(j + 1).getRegularStats().getPPGCompare()) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Description: Sorts players based on PPG Swing Least To Greatest. Bubble Sort
     * Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortByPlayoffFallerPPG(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players.get(j).getRegularStats().getPPGCompare()
                        - players.get(j).getPlayoffStats().getPPGCompare() < players.get(j + 1)
                                .getRegularStats().getPPGCompare()
                                - players.get(j + 1).getPlayoffStats().getPPGCompare()) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Description: Sorts players based on PPG Swing Greatest to Least. Bubble Sort
     * Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortByPlayoffRiserPPG(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players.get(j).getRegularStats().getPPGCompare()
                        - players.get(j).getPlayoffStats().getPPGCompare() > players.get(j + 1)
                                .getRegularStats().getPPGCompare()
                                - players.get(j + 1).getPlayoffStats().getPPGCompare()) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Description: Sorts players based on a Players Salary. Bubble Sort Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortBySalary(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (players.get(j).getSalary() < players.get(j + 1).getSalary()) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Description: Sorts players based Team Win Percentage. Bubble Sort Method.
     * 
     * @param players
     * @author Joel Menezes
     */
    public static void sortByWinPercentage(ArrayList<Player> players) {
        int n = players.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (TEAM_WINPERCENTAGE.get(TEAM_CODES.indexOf(players.get(j).getTeamCode())) < TEAM_WINPERCENTAGE
                        .get(TEAM_CODES.indexOf(players.get(j + 1).getTeamCode()))) {
                    Player temp = new Player(players.get(j));
                    players.set(j, players.get(j + 1));
                    players.set(j + 1, temp);

                }
            }
        }
    }
}
