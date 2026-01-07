/**
 * The player object is the object that will be representing all nhl playoff
 * players in our project
 * It contains important details about the player including their stats that are
 * held as PlayerStat objects
 * 
 * @author Justin & Joel
 */
public class Player {

    private String name;
    private String team;
    private String position;
    private int salary;
    private PlayerStats regularStats;
    private PlayerStats playoffStats;
    private String teamCode;

    /**
     * Description: Constructs player object
     * 
     * @param name     Player's name
     * @param team     Player's team name
     * @param position Player's position on team
     * @param salary   Player's salary
     * @param teamCode Player's team acronym
     * @author Joel Menezes & Justin
     */
    public Player(String name, String team, String position, int salary, String teamCode) {
        this.name = name;
        this.team = team;
        this.position = position;
        this.salary = salary;
        this.teamCode = teamCode;
    }

    /**
     * Description: Constructs player object based on an old one.
     * 
     * @param player player object
     * @author Joel Menezes 
     */
    public Player(Player player) {
        this.name = player.name;
        this.team = player.team;
        this.position = player.position;
        this.salary = player.salary;
        this.regularStats = new PlayerStats(player.regularStats);
        this.playoffStats = new PlayerStats(player.playoffStats);
        this.teamCode = player.teamCode;
    }

    /**
     * Description: Adds PlayerStat object to regularstat variable
     * 
     * @param regularStats
     * @author Joel Menezes & Justin
     */
    public void addRegularStats(PlayerStats regularStats) {
        this.regularStats = regularStats;
    }

    /**
     * Description: Adds PlayerStat object to playoffstat variable
     * 
     * @param playoffStats
     * @author Joel Menezes & Justin
     */
    public void addPlayoffStats(PlayerStats playoffStats) {
        this.playoffStats = playoffStats;
    }

    /**
     * Description: Returns the player's full name
     * 
     * @return String Player's name
     * @author Joel Menezes & Justin
     */
    public String getName() {
        return name;
    }

    /**
     * Description: Returns the player's team name
     * 
     * @return String team name
     * @author Joel Menezes & Justin
     */
    public String getTeam() {
        return team;
    }

    /**
     * Description: Returns the player's team acronym
     * 
     * @return String team acronym
     * @author Joel Menezes 
     */
    public String getTeamCode() {
        return teamCode;
    }

    /**
     * Description: Returns player's position on team
     * 
     * @return String player's position
     * @author Joel Menezes & Justin
     */
    public String getPosition() {
        return position;
    }

    /**
     * Description: Returns player's salary
     * 
     * @return int player's salary
     * @author Joel Menezes & Justin
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Description: Returns player's regular stats as playerstat object
     * 
     * @return PlayerStats player's regular stats
     * @author Joel Menezes & Justin
     */
    public PlayerStats getRegularStats() {
        return regularStats;
    }

    /**
     * Description: Returns player's playoff stats as playerstat object
     * 
     * @return PlayerStats player's playoff stat
     * @author Joel Menezes & Justin
     */
    public PlayerStats getPlayoffStats() {
        return playoffStats;
    }

    /**
     * Description: Returns player's ppg swing value (Explained more in readme +
     * program text)
     * 
     * @return double of player's ppg swing value
     * @author Justin
     */
    public Double getPPGSwing() {
        double raw = playoffStats.getPPG() - regularStats.getPPG();
        return Math.round(raw * 100.0) / 100.0;
    }
}
