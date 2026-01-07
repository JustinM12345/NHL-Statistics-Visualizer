import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class MainProgram extends Application {

    public static boolean isFirstLine;
    public static BufferedReader fileReader;
    public static String line;
    public static String[] csvRegularSeasonValues;
    public static String[] csvPlayoffValues;
    public CategoryAxis xAxis = new CategoryAxis();
    public NumberAxis yAxis = new NumberAxis();
    public BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

    // The three arrays below are mapped by index to create team classes
    // Team Abbreviations
    public static String[] TEAM_CODES = { "DAL", "VGK", "WPG", "COL", "EDM", "LAK", "VAN", "NSH", "FLA", "TBL", "BOS",
            "TOR", "NYR", "WSH", "CAR", "NYI" };
    public static String[] POSITIONS = { "C", "L", "R", "D" };
    // Team full names
    public static String[] TEAM_NAMES = { "Dallas Stars", "Vegas Golden Knights", "Winnipeg Jets", "Colorado Avalanche",
            "Edmonton Oilers", "Los Angeles", "Vancouver Canucks", "Nashville Predators", "Florida Panthers",
            "Tampa Bay Lightning", "Boston Bruins",
            "Toronto Maple Leafs", "New York Rangers", "Washington Capitals", "Carolina Huricanes",
            "New York Islanders" };
    // Team win percentage
    public static Double[] TEAM_WINPERCENTAGE = { 0.636, 0.566, 0.622, 0.598, 0.640, 0.556, 0.622, 0.588, 0.646, 0.558,
            0.610, 0.575, 0.667, 0.506, 0.644, 0.541 };

    public static ArrayList<Team> teams = new ArrayList<>();
    public static ArrayList<Player> entireLeague = new ArrayList<>();
    public static ArrayList<String> teamFilter = new ArrayList<>();
    public static ArrayList<String> positionFilter = new ArrayList<>();
    public static ArrayList<String> playerFilter = new ArrayList<>();
    public static ArrayList<Button> buttons = new ArrayList<>();

    private BorderPane root;

    // File reader and object creation is done here
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < TEAM_CODES.length; i++)
            teams.add(new Team(TEAM_NAMES[i], TEAM_CODES[i], TEAM_WINPERCENTAGE[i]));

        isFirstLine = true;
        fileReader = new BufferedReader(new FileReader("src/regular.csv"));
        while ((line = fileReader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
            } else {
                csvRegularSeasonValues = line.split(",");
                PlayerStats regularSeasonStats = new PlayerStats(Integer.parseInt(csvRegularSeasonValues[7]),
                        Integer.parseInt(csvRegularSeasonValues[5]), Integer.parseInt(csvRegularSeasonValues[6]),
                        Integer.parseInt(csvRegularSeasonValues[4]),
                        Double.parseDouble(csvRegularSeasonValues[8]),
                        Double.parseDouble(csvRegularSeasonValues[10]));
                for (Team team : teams)
                    if (team.getTeamAcronym().equals(csvRegularSeasonValues[2])) {
                        Player player = new Player(csvRegularSeasonValues[0],
                                team.getTeamName(),
                                csvRegularSeasonValues[3], Integer.parseInt(csvRegularSeasonValues[9]),
                                csvRegularSeasonValues[2]);
                        player.addRegularStats(regularSeasonStats);
                        team.addPlayer(player);
                        entireLeague.add(player);
                    }

            }
        }
        fileReader.close();
        isFirstLine = true;

        fileReader = new BufferedReader(new FileReader("src/playoffs.csv"));
        while ((line = fileReader.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false;
            } else {
                csvPlayoffValues = line.split(",");
                PlayerStats playoffSeasonStats = new PlayerStats(Integer.parseInt(csvPlayoffValues[7]),
                        Integer.parseInt(csvPlayoffValues[5]), Integer.parseInt(csvPlayoffValues[6]),
                        Integer.parseInt(csvPlayoffValues[4]),
                        Double.parseDouble(csvPlayoffValues[8]),
                        Double.parseDouble(csvPlayoffValues[10]));
                for (Team team : teams)
                    if (team.getTeamAcronym().equals(csvPlayoffValues[2])) {
                        team.findPlayer(csvPlayoffValues[0]).addPlayoffStats(playoffSeasonStats);

                    }
            }
        }
        fileReader.close();
        launch(args);
    }

    /**
     * Description: Contains all code for popup window that appear when a bar is
     * clicked in
     * program
     * 
     * @param playerName player that will be analyzed in popup
     * @author Justin
     */
    public void PlayerDetailsPopup(String playerName) {

        Player playerObject = null;
        for (Team team : teams) {
            if (team.findPlayer(playerName) != null) {
                playerObject = team.findPlayer(playerName);
            }
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(playerName + " Details");

        VBox GeneralInfo = new VBox(10);
        GeneralInfo.setPadding(new Insets(5));

        Label nameLabel = new Label(
                playerName + " \n(" + playerObject.getTeamCode() + ")\nPosition: " + playerObject.getPosition());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nameLabel.setWrapText(true);
        Label salaryLabel = new Label("Salary: $" + playerObject.getSalary());
        Label RegularLabel = new Label("Regular Season Stats");
        RegularLabel.setUnderline(true);
        Label RegularStat1 = new Label("Games Played: " + playerObject.getRegularStats().getGP());
        Label RegularStat2 = new Label("Goals: " + playerObject.getRegularStats().getGoals());
        Label RegularStat3 = new Label("Assists: " + playerObject.getRegularStats().getAssists());
        Label RegularStat4 = new Label("Points: " + playerObject.getRegularStats().getPoints());
        Label RegularStat5 = new Label("PPG: " + playerObject.getRegularStats().getPPG());

        Label PlayoffLabel = new Label("Playoff Season Stats");
        PlayoffLabel.setUnderline(true);
        Label PlayoffStat1 = new Label("Games Played: " + playerObject.getPlayoffStats().getGP());
        Label PlayoffStat2 = new Label("Goals: " + playerObject.getPlayoffStats().getGoals());
        Label PlayoffStat3 = new Label("Assists: " + playerObject.getPlayoffStats().getAssists());
        Label PlayoffStat4 = new Label("Points: " + playerObject.getPlayoffStats().getPoints());
        Label PlayoffStat5 = new Label("PPG: " + playerObject.getPlayoffStats().getPPG());

        GeneralInfo.getChildren().addAll(nameLabel, salaryLabel, RegularLabel, RegularStat1, RegularStat2, RegularStat3,
                RegularStat4, RegularStat5, PlayoffLabel, PlayoffStat1, PlayoffStat2, PlayoffStat3, PlayoffStat4,
                PlayoffStat5);

        // Creating graph
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(playerName);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("PPG");

        BarChart<String, Number> ppgChart = new BarChart<>(xAxis, yAxis);
        ppgChart.setTitle("Regular vs Playoff PPG");

        // Series for Regular PPG
        XYChart.Series<String, Number> regularSeries = new XYChart.Series<>();
        regularSeries.setName("Regular");
        regularSeries.getData().add(
                new XYChart.Data<>("PPG", playerObject.getRegularStats().getPPG()));

        // Series for Playoff PPG
        XYChart.Series<String, Number> playoffSeries = new XYChart.Series<>();
        playoffSeries.setName("Playoffs");
        playoffSeries.getData().add(
                new XYChart.Data<>("PPG", playerObject.getPlayoffStats().getPPG()));

        ppgChart.getData().setAll(regularSeries, playoffSeries);
        ppgChart.setLegendVisible(true);
        ppgChart.setMaxWidth(250);
        ppgChart.setMaxHeight(400);

        // Creating playoff riser or faller value
        // I want to create a new term called "PPG Swing" which is simply playoff ppg -
        // regular ppg

        VBox PPGSwing = new VBox(10);
        PPGSwing.setPadding(new Insets(5));

        Label PPGLabel1 = new Label("PPG Swing");
        PPGLabel1.setFont(Font.font("System", FontWeight.BOLD, 18));
        Label PPGLabel2a = new Label("PPG Swing = Playoff PPG - Regular Season PPG");
        Label PPGLabel2b = new Label("This value helps represent whether or not \na player is a riser or a faller");
        Label PPGLabel3 = new Label(playerName + "'s PPG Swing Value: " + playerObject.getPPGSwing());
        Label PPGLabel4 = new Label();

        if (playerObject.getPPGSwing() >= 0.35) {
            PPGLabel4 = new Label(playerName + " is a MAJOR Playoff Riser (>= 0.35)");
        } else if (playerObject.getPPGSwing() > 0) {
            PPGLabel4 = new Label(playerName + " is a Playoff Riser (> 0)");
        } else if (playerObject.getPPGSwing() <= -0.50) {
            PPGLabel4 = new Label(playerName + " is a MAJOR Playoff Faller (< -0.50)");
        } else if (playerObject.getPPGSwing() < 0) {
            PPGLabel4 = new Label(playerName + " is a Playoff Faller (< 0)");
        } else {
            PPGLabel4 = new Label(playerName + " doesn't seem to be a Playoff Riser or Faller! (Pretty cool!)");
        }

        PPGLabel4.setWrapText(true);
        PPGLabel4.setFont(Font.font("System", FontWeight.BOLD, 12));

        PPGSwing.getChildren().addAll(PPGLabel1, PPGLabel2a, PPGLabel2b, PPGLabel3, PPGLabel4);

        // Creating number line
        NumberAxis swingXAxis = new NumberAxis(-1.0, 1.0, 0.25);
        swingXAxis.setLabel("PPG Swing");

        NumberAxis swingYAxis = new NumberAxis(-1, 1, 1); // Imaginary axis that will have opacity set to zero
        swingYAxis.setOpacity(0);

        ScatterChart<Number, Number> swingChart = new ScatterChart<>(swingXAxis, swingYAxis);
        swingChart.setTitle("All Players' PPG Swing");
        swingChart.setLegendVisible(false);
        swingChart.setHorizontalGridLinesVisible(true);
        swingChart.setVerticalGridLinesVisible(false);
        swingChart.setPrefSize(500, 150);

        // Top 3 players on every team's dot
        XYChart.Series<Number, Number> swingSeries = new XYChart.Series<>();
        for (Team team : teams) {
            for (Player p : team.getTop3PPG()) {
                double swing = p.getPPGSwing();
                swingSeries.getData().add(new XYChart.Data<>(swing, 0));
            }
        }

        // Player's ppg swing dot
        XYChart.Data<Number, Number> playerDot = new XYChart.Data<>(playerObject.getPPGSwing(), 0);
        swingSeries.getData().add(playerDot);

        // League average
        double totalPPGSwing = 0;
        for (Player p : entireLeague) {
            totalPPGSwing += p.getPPGSwing();
        }
        totalPPGSwing = totalPPGSwing / entireLeague.size();
        XYChart.Data<Number, Number> leagueDot = new XYChart.Data<>(totalPPGSwing, 0);
        swingSeries.getData().add(leagueDot);

        swingChart.getData().add(swingSeries);

        // Grabs the node of each data point after the graph has been generated and
        // assigns a colour to each one
        Platform.runLater(() -> {
            for (XYChart.Data<Number, Number> data : swingSeries.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    if (data == playerDot) {
                        node.setStyle("-fx-background-color: red, red;");
                    } else if (data == leagueDot) {
                        node.setStyle("-fx-background-color: blue, blue;");
                    } else {
                        node.setStyle("-fx-background-color: grey, grey;");
                    }
                }
            }
        });

        // Legend below number line
        Rectangle greyRect = new Rectangle(10, 10, Color.GREY);
        Rectangle redRect = new Rectangle(10, 10, Color.RED);
        Rectangle blueRect = new Rectangle(10, 10, Color.BLUE);

        Label othersLabel = new Label("Other Players");
        Label playerLabel = new Label("Selected Player");
        Label leagueLabel = new Label("League Average");

        HBox legend = new HBox(10, new HBox(5, greyRect, othersLabel), new HBox(5, redRect, playerLabel),
                new HBox(5, blueRect, leagueLabel));
        legend.setAlignment(Pos.CENTER);
        legend.setPadding(new Insets(10));

        // Add both number line and legend
        PPGSwing.getChildren().addAll(swingChart, legend);

        VBox chartBox = new VBox(10, ppgChart);
        chartBox.setAlignment(Pos.CENTER);

        HBox mainLayout = new HBox(20);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(GeneralInfo, chartBox, PPGSwing);

        Scene popupScene = new Scene(mainLayout, 1000, 500);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    // Program starts here
    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();

        primaryStage.setTitle("Champion or Choker?");

        xAxis.setLabel("Player Name");
        xAxis.setTickLabelRotation(-90);

        yAxis.setLabel("Points Per Game (PPG)");

        barChart.setTitle("Regular season PPG vs Playoff PPG");
        // Setting up bar chart
        barChart.setCategoryGap(5);
        barChart.setBarGap(1);
        barChart.setAnimated(false);
        barChart.setPrefWidth(800); // previously 550x350
        barChart.setPrefHeight(600);
        barChart.setVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("REGULAR SEASON");

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("PLAYOFFS");

        ArrayList<Player> graphedPlayers = new ArrayList<>();

        String sorters[] = { "About The Program", "sortWinPercentage", "sortSalary", "sortRisers", "sortFallers",
                "sortPlayoffPPG",
                "sortRegularPPG" };
        ComboBox<String> comboBox = new ComboBox();
        comboBox.getItems().addAll(sorters);
        comboBox.setValue("About The Program");

        displayGraph(comboBox.getValue(), graphedPlayers);

        comboBox.setOnAction(event -> {
            String selection = comboBox.getValue();
            displayGraph(selection, graphedPlayers);
        });

        // Set bar chart in the center
        root.setCenter(barChart);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        MenuButton menuButton = new MenuButton("Filter Options");
        Menu teamsButton = new Menu("Teams");
        for (String teamCode : TEAM_CODES) {
            MenuItem item = new MenuItem(teamCode);
            teamsButton.getItems().add(item);
            item.setOnAction(event -> {
                clearFilters(buttonBox);
                teamFilter.add(teamCode);
                displayGraph(comboBox.getValue(), graphedPlayers);
                addFilters(buttonBox, comboBox, graphedPlayers);

            });
        }

        Menu positionsButton = new Menu("Positions");
        for (String position : POSITIONS) {
            MenuItem item = new MenuItem(position);
            positionsButton.getItems().add(item);
            item.setOnAction(event -> {
                clearFilters(buttonBox);
                positionFilter.add(position);
                displayGraph(comboBox.getValue(), graphedPlayers);
                addFilters(buttonBox, comboBox, graphedPlayers);

            });
        }

        menuButton.getItems().addAll(teamsButton, positionsButton);

        TextField playersField = new TextField();
        playersField.setPromptText("Enter Name: ");

        playersField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String player = playersField.getText();
                if (player != "" && !playerFilter.contains(player) && !player.isEmpty() && !player.isBlank()) {
                    playerFilter.add(player);
                    playersField.setText("");
                    clearFilters(buttonBox);
                    addFilters(buttonBox, comboBox, graphedPlayers);

                }
                displayGraph(comboBox.getValue(), graphedPlayers);
            }
        });

        StackPane r = new StackPane();

        r.getChildren().add(playersField);
        Button clearFilterButton = new Button("Clear Filters");
        clearFilterButton.setOnAction(event -> {
            playerFilter.clear();
            teamFilter.clear();
            positionFilter.clear();
            clearFilters(buttonBox);

            displayGraph(comboBox.getValue(), graphedPlayers);

        });

        Button addPlayerButton = new Button("Search");
        addPlayerButton.setOnAction(event -> {
            String player = playersField.getText();
            if (player != "" && !playerFilter.contains(player) && !player.isEmpty() && !player.isBlank()) {
                playerFilter.add(player);
                playersField.setText("");
                clearFilters(buttonBox);
                addFilters(buttonBox, comboBox, graphedPlayers);

            }
            displayGraph(comboBox.getValue(), graphedPlayers);
        });

        buttonBox.getChildren().addAll(comboBox, menuButton, r, addPlayerButton, clearFilterButton);

        // Padding around buttons
        BorderPane.setMargin(buttonBox, new Insets(10, 0, 10, 0));

        // Put the button box at the bottom of the BorderPane
        root.setBottom(buttonBox);

        welcomePage(barChart, graphedPlayers, root);

        // Displays graph
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }

    /**
     * Description: This method is called everytime the filter is switched as the
     * graph must be
     * cleared and regraphed to avoid any previous data mixing in with the data that
     * we want to graph
     * 
     * @param graphedPlayers Arraylist of players that are to be graphed
     * @author Justin & Joel
     */
    public void graphChart(ArrayList<Player> graphedPlayers) {
        // (1) Clear any existing series
        barChart.getData().clear();

        // Pause program so graph can properly clear
        PauseTransition refreshPause = new PauseTransition(Duration.millis(25));

        refreshPause.setOnFinished(ev -> {
            XYChart.Series<String, Number> series1 = new XYChart.Series<>();
            series1.setName("REGULAR SEASON");

            XYChart.Series<String, Number> series2 = new XYChart.Series<>();
            series2.setName("PLAYOFFS");
            for (Player player : graphedPlayers)
                if (player != null) {
                    series1.getData().add(new XYChart.Data<>(player.getName(), player.getRegularStats().getPPG()));
                    series2.getData().add(new XYChart.Data<>(player.getName(), player.getPlayoffStats().getPPG()));
                }

            Platform.runLater(() -> {
                barChart.getData().addAll(series1, series2);
                barChart.setVisible(true);
            });

            // Popups for series1 bar
            Platform.runLater(() -> {
                for (XYChart.Data<String, Number> data : series1.getData()) {
                    Node node = data.getNode();
                    if (node != null) {
                        node.setOnMouseClicked(event -> {
                            String playerName = data.getXValue();
                            PlayerDetailsPopup(playerName);
                            System.out.println(playerName + " has been pressed");
                        });
                    }
                }
            });

            // Popup for series2 bar
            Platform.runLater(() -> {
                for (XYChart.Data<String, Number> data : series2.getData()) {
                    Node node = data.getNode();
                    if (node != null) {
                        node.setOnMouseClicked(event -> {
                            String playerName = data.getXValue();
                            PlayerDetailsPopup(playerName);
                            System.out.println(playerName + " has been pressed (bar 2)");
                        });
                    }
                }
            });

        });

        refreshPause.playFromStart();

        // Ensures that graph is displayed in case the user came from welcome page
        barChart.setVisible(true);
        root.setCenter(barChart);
    }

    /**
     * Description: Filter method to filter through teams, positions, and players
     * 
     * @return ArrayList<Player> returns a list of players that have gone through
     *         the filters of Player, Position and Team
     * @author Joel Menezes
     */
    public static ArrayList<Player> filter() {
        if (teamFilter.isEmpty() && playerFilter.isEmpty() && positionFilter.isEmpty())
            return new ArrayList<>(entireLeague);
        ArrayList<Player> filteredPlayers = new ArrayList<>();

        for (Player player : entireLeague)
            if (teamFilter.contains(player.getTeamCode()))
                filteredPlayers.add(player);

        for (String filter : playerFilter)
            for (Player player : entireLeague)
                if (player.getName().toLowerCase().contains(filter.toLowerCase()) && !filteredPlayers.contains(player))
                    filteredPlayers.add(player);

        for (Player player : entireLeague)
            if (positionFilter.contains(player.getPosition()) && !filteredPlayers.contains(player))
                filteredPlayers.add(player);

        return filteredPlayers;
    }

    /**
     * Description: Decides that to display when dropdown menu is used
     * 
     * @param selection      Selection of dropdown is sent here and appropriate page
     *                       is generated
     * @param graphedPlayers The players that are to be graphed
     * @author Joel Menezes
     */
    public void displayGraph(String selection, ArrayList<Player> graphedPlayers) {
        int count = 0;
        ArrayList<Player> filter = new ArrayList<>();

        switch (selection) {
            case "About The Program":
                System.out.println("Displaying welcome page");
                welcomePage(barChart, graphedPlayers, root);
                break;
            case "sortWinPercentage":
                // Clear all current data
                PlayerSorter.sortByWinPercentage(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();

                count = 0;

                for (Team team : teams) {
                    if (count >= 16)
                        break;
                    for (Player plr : team.getTop3PPG())
                        graphedPlayers.add(new Player(plr));
                    PlayerSorter.sortByWinPercentage(graphedPlayers);
                    count++;
                }

                graphChart(graphedPlayers);

                break;
            case "sortSalary":
                // Clear all current data
                PlayerSorter.sortBySalary(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();
                filter = filter();
                PlayerSorter.sortBySalary(filter);
                count = 0;
                for (Player plr : filter) {
                    if (count >= 16)
                        break;
                    graphedPlayers.add(new Player(plr));
                    count++;
                }

                graphChart(graphedPlayers);

                break;
            case "sortRisers":
                // Clear all current data
                PlayerSorter.sortByPlayoffRiserPPG(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();
                filter = filter();
                PlayerSorter.sortByPlayoffRiserPPG(filter);
                count = 0;
                for (Player plr : filter) {
                    if (count >= 16)
                        break;
                    graphedPlayers.add(new Player(plr));
                    count++;
                }

                graphChart(graphedPlayers);

                break;
            case "sortFallers":
                // Clear all current data
                PlayerSorter.sortByPlayoffFallerPPG(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();
                filter = filter();
                PlayerSorter.sortByPlayoffFallerPPG(filter);
                count = 0;
                for (Player plr : filter) {
                    if (count >= 16)
                        break;
                    graphedPlayers.add(new Player(plr));
                    count++;
                }

                graphChart(graphedPlayers);

                break;
            case "sortPlayoffPPG":
                // Clear all current data
                PlayerSorter.sortByPlayoffSeasonPPG(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();
                filter = filter();
                PlayerSorter.sortByPlayoffSeasonPPG(filter);
                count = 0;
                for (Player plr : filter) {
                    if (count >= 16)
                        break;
                    graphedPlayers.add(new Player(plr));
                    count++;
                }

                graphChart(graphedPlayers);

                break;
            case "sortRegularPPG":
                // Clear all current data
                PlayerSorter.sortByRegularSeasonPPG(entireLeague);

                barChart.getData().clear();
                graphedPlayers.clear();
                filter = filter();
                PlayerSorter.sortByRegularSeasonPPG(filter);
                count = 0;
                for (Player plr : filter) {
                    if (count >= 16)
                        break;
                    graphedPlayers.add(new Player(plr));
                    count++;
                }
                graphChart(graphedPlayers);

                break;
            default:
                break;
        }
    }

    /**
     * Description: Clears all current filters created. Used for refreshing filters
     * as well so
     * there aren't duplicate delete buttons.
     * 
     * @param buttonBox
     * @author Joel Menezes
     */
    public void clearFilters(HBox buttonBox) {
        for (Node button : new ArrayList<>(buttonBox.getChildren()))
            if (button instanceof Button && buttons.contains(button)) {
                buttonBox.getChildren().remove(button);
                buttons.remove(buttons.indexOf(button));
            }
    }

    /**
     * Description: Allows you to add filters and search through players, refreshed
     * on every
     * filter action. All Specific filters are handled within the object that is
     * adding them.
     * 
     * @param buttonBox
     * @param comboBox
     * @param graphedPlayers
     * @author Joel Menezes
     */
    public void addFilters(HBox buttonBox, ComboBox<String> comboBox, ArrayList<Player> graphedPlayers) {
        for (String filter : teamFilter) {
            Button teamFilterButton = new Button(filter + " X");
            buttons.add(teamFilterButton);
            buttonBox.getChildren().addAll(teamFilterButton);

            teamFilterButton.setOnAction(event -> {
                teamFilter.remove(filter);
                buttons.remove(teamFilterButton);
                buttonBox.getChildren().remove(teamFilterButton);
                displayGraph(comboBox.getValue(), graphedPlayers);
            });
        }
        for (String filter : playerFilter) {
            Button playerFilterButton = new Button(filter + " X");
            buttons.add(playerFilterButton);
            buttonBox.getChildren().addAll(playerFilterButton);

            playerFilterButton.setOnAction(event -> {
                playerFilter.remove(filter);
                buttons.remove(playerFilterButton);
                buttonBox.getChildren().remove(playerFilterButton);
                displayGraph(comboBox.getValue(), graphedPlayers);
            });
        }

        for (String filter : positionFilter) {
            Button positionFilterButton = new Button(filter + " X");
            buttons.add(positionFilterButton);
            buttonBox.getChildren().addAll(positionFilterButton);

            positionFilterButton.setOnAction(event -> {
                positionFilter.remove(filter);
                buttons.remove(positionFilterButton);
                buttonBox.getChildren().remove(positionFilterButton);
                displayGraph(comboBox.getValue(), graphedPlayers);
            });
        }
    }

    /**
     * Description: Method for welcome page that will be the first thing that
     * appears when user
     * opens program
     * Method allows for it to be reusable so it can be accessed again even after
     * the user close this page
     * 
     * @param barChart       Disables bar graphs that were generated
     * @param graphedPlayers Disables players that were supposed to be graphed
     * @param root           The window that the welcome page will appear in
     * @author Justin
     */
    public void welcomePage(BarChart<String, Number> barChart, ArrayList<Player> graphedPlayers, BorderPane root) {
        Platform.runLater(() -> {
            barChart.getData().clear();
            graphedPlayers.clear();
            barChart.setVisible(false);

            VBox welcomePage = new VBox(10);
            welcomePage.setPadding(new Insets(5));

            Label line1 = new Label("Welcome to Champions or Chokers");
            line1.setFont(Font.font("System", FontWeight.BOLD, 18));
            Label creatorName = new Label("Created by Joel Menezes and Justin Mui :)");
            creatorName.setFont(Font.font("System", FontWeight.BOLD, 15));
            Label line2 = new Label(
                    "Ever wondered if your favourite player was a choker or a champion? If they can rise to the occasion during the toughest times or if they crumble under all the pressure? Well we decided to graph the data to answer that question\n"
                            +
                            "The program compares player's regular season PPG and playoff season PPG. This is done by generating double bar graphs for each player.\n"
                            +
                            "Playoff Season PPG > Regular Season PPG = Playoff Riser\nRegular Season PPG > Playoff Season PPG = Playoff Faller\n"
                            +
                            "A larger gap between the two stats symbolizes whether or not the player performs better or worse under the playoff pressure.\n");
            Label header1 = new Label("Sorting");
            header1.setFont(Font.font("System", FontWeight.BOLD, 12));
            Label line3 = new Label(
                    "On the bottom left side of the program you will see a dropdown, this will allow you to switch between the various different sorting systems that we have created. \n"
                            +
                            "Each sort allows you to view the data from a different perspective.");
            Label header2 = new Label("Searching + Filtering");
            header2.setFont(Font.font("System", FontWeight.BOLD, 12));
            Label line4 = new Label(
                    "The program also includes filters via its built in search function + filters dropdown, where you can view the data for specific player, players' who share the same name, players on a specific team, player's positions, etc\n"
                            +
                            "Filters ARE stackable and multiple can be applied at once, for instance you can search up 'Auston Matthews' and add Dallas as a filter to compare Auston Matthews to the entire Dallas Stars Roster\n");

            Label header3 = new Label("Individual Player Popups ");
            header3.setFont(Font.font("System", FontWeight.BOLD, 12));
            Label line5 = new Label(
                    "When a player's bar is clicked within the graph, this opens up a popup with more indepth info about that player's stats.\n"
                            +
                            "The popup also includes a number line that displays the ppg swing of top players in the league, highlighting the currently selected player.\n\n");
            Label line6 = new Label(
                    "For a more indepth explaination of the program please go through the README.MD, otherwise please enjoy your time with the program! 😃");
            line2.setWrapText(true);
            line3.setWrapText(true);
            line4.setWrapText(true);
            line5.setWrapText(true);
            line6.setWrapText(true);
            line6.setFont(Font.font("System", FontWeight.BOLD, 12));

            welcomePage.getChildren().addAll(line1, creatorName, line2, header1, line3, header2, line4, header3, line5,
                    line6);
            root.setCenter(welcomePage);
        });
    }

}
