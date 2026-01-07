/**
 * Holds specific data of a player's stats
 * Each player will hold two playerstat objects one for regular and one for
 * playoff season
 * 
 * @author Justin & Joel
 */
public class PlayerStats {
    private int points;
    private int goals;
    private int assists;
    private int gp;
    private double ppg;
    private double ppg100;

    /**
     * Description: Constructs playerstat object
     * 
     * @param points  Total points that season
     * @param goals   Total goals that season
     * @param assists Total assista that season
     * @param gp      Total games played that season
     * @param ppg     Average points per game that season
     * @param ppg100  Point per game value multiplied by 100 to minimize rounding
     *                during graph generation
     * @author Joel Menezes & Justin
     */
    public PlayerStats(int points, int goals, int assists, int gp, double ppg, double ppg100) {

        this.points = points;
        this.goals = goals;
        this.assists = assists;
        this.gp = gp;
        this.ppg = ppg;
        this.ppg100 = ppg100;
    }

    /**
     * Description: Constructs playerstat object based on an old one
     * 
     * @param stats PlayerStat stats
     * @author Joel Menezes
     */
    public PlayerStats(PlayerStats stats) {

        this.points = stats.points;
        this.goals = stats.goals;
        this.assists = stats.assists;
        this.gp = stats.gp;
        this.ppg = stats.ppg;
        this.ppg100 = stats.ppg100;

    }

    /**
     * Description: Returns points
     * 
     * @return int points
     * @author Joel Menezes & Justin
     */
    public int getPoints() {
        return points;
    }

    /**
     * Description: Returns goals
     * 
     * @return int goals
     * @author Joel Menezes & Justin
     */
    public int getGoals() {
        return goals;
    }

    /**
     * Description: Returns assists
     * 
     * @return int assists
     * @author Joel Menezes & Justin
     */
    public int getAssists() {
        return assists;
    }

    /**
     * Description: Returns games played
     * 
     * @return int games played
     * @author Joel Menezes & Justin
     */
    public int getGP() {
        return gp;
    }

    /**
     * Description: Return average points per game
     * 
     * @return double average ppg
     * @author Joel Menezes & Justin
     */
    public double getPPG() {
        return ppg;
    }

    /**
     * Description: Returns the ppg multipled by 100 value to reduce rounding errors
     * when
     * graphing
     * 
     * @return double ppg multipled by 100
     * @author Joel Menezes
     */
    public double getPPGCompare() {
        return ppg100;
    }
}
