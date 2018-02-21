package com.Rolfrider.GUI;

import com.Rolfrider.Data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailedWindowController implements Initializable{


    private Player player;
    private PlayerDetails playerDetails;

    @FXML
    private ImageView playerPhoto;
    @FXML
    private ImageView backArrow;
    @FXML
    private PieChart passChart;
    @FXML
    private Label statOne;
    @FXML
    private Label statTwo;
    @FXML
    private Label statThree;
    @FXML
    private Label statOneVal;
    @FXML
    private Label statTwoVal;
    @FXML
    private Label statThreeVal;
    @FXML
    private TableColumn<Fixture, Integer> fixGwColumn;
    @FXML
    private TableColumn<Fixture, String> fixOppColumn;
    @FXML
    private TableColumn<Fixture, Integer> fixDifColumn;
    @FXML
    private TableView<Fixture> fixtureTable;
    @FXML
    private TableView<GameStats> historyTable;
    @FXML
    private TableColumn<GameStats,Integer> csColumn;
    @FXML
    private TableColumn<GameStats,Integer> goalColumn;
    @FXML
    private TableColumn<GameStats,Integer> ntColumn;
    @FXML
    private TableColumn<GameStats,Float> ictColumn;
    @FXML
    private TableColumn<GameStats,Integer> rcColumn;
    @FXML
    private TableColumn<GameStats,Integer> gcColumn;
    @FXML
    private TableColumn<GameStats,Integer> pointsColumn;
    @FXML
    private TableColumn<GameStats,Integer> ycColumn;
    @FXML
    private TableColumn<GameStats,Integer> pmColumn;
    @FXML
    private TableColumn<GameStats,Integer> bColumn;
    @FXML
    private TableColumn<GameStats,Integer> gwColumn;
    @FXML
    private TableColumn<GameStats,Integer> assistColumn;
    @FXML
    private TableColumn<GameStats,Integer> ogColumn;
    @FXML
    private TableColumn<GameStats,Integer> sbColumn;
    @FXML
    private TableColumn<GameStats,Integer> psColumn;
    @FXML
    private TableColumn<GameStats,Float> valColumn;
    @FXML
    private TableColumn<GameStats,Integer> sColumn;
    @FXML
    private TableColumn<GameStats,String> oppositionColumn;
    @FXML
    private Label assistLabel;
    @FXML
    private Label formLabel;
    @FXML
    private Label goalLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label posLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ptsLabel;
    @FXML
    private Label sbLabel;
    @FXML
    private Label teamLabel;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TitledPane overallPane;
    @FXML
    private TitledPane historyPane;
    @FXML
    private TitledPane statsPane;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fixOppColumn.setCellValueFactory(new PropertyValueFactory<>("opponent_short_name"));
        fixGwColumn.setCellValueFactory(new PropertyValueFactory<>("event"));
        fixDifColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        initHistoryTable();

        progressIndicator.progressProperty().addListener((observable, oldVal, newVal) -> {
            if((Double)newVal == 1.0)
                progressIndicator.setVisible(false);
        });

        historyTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        historyTable.getSelectionModel().selectedItemProperty().addListener((o, oldVal, newVal) ->
                {
                    statsPane.setExpanded(true);
                    GameStats [] gs = new GameStats[historyTable.getSelectionModel().getSelectedItems().size()];
                    int i =0;
                    int sPasses = 0;
                    int fPasses = 0;
                    for (GameStats s:  historyTable.getSelectionModel().getSelectedItems()) {
                        gs[i] = s;
                        i++;
                        sPasses += s.getCompleted_passes();
                        fPasses += s.getFailedPasses();
                    }
                    setStats(this.player.getElement_type(), gs) ;
                    setPieChartData(fPasses, sPasses);
                }
        );

        overallPane.expandedProperty().addListener(((observable, oldValue, newValue) ->
        {
            if(newValue) {//newValue true when is now expanded
                setPieChartData(playerDetails.getFailedPasses(), playerDetails.getCompletedPasses());
                setStats(player.getElement_type(), playerDetails.getHistory());
                historyPane.setExpanded(false);
            }
        }));

        historyPane.expandedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue){
                overallPane.setExpanded(false);
            }
        }));


    }

    public void showMainWindow(Event event) throws IOException {
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("window.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }



    private void setPieChartData(int fPasses, int sPasses){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Failed passes", fPasses),
                new PieChart.Data("Successful passes", sPasses));
        passChart.setData(pieChartData);
    }


    public void initData(Player player){
        this.player = player;
        initOverall();
        playerDetails = DataReader.ReadPlayersDetails(player.getId() +"");
        setPieChartData( playerDetails.getFailedPasses(), playerDetails.getCompletedPasses());
        setStats(player.getElement_type(), playerDetails.getHistory());
        ObservableList<Fixture> fixtureData = FXCollections.observableArrayList();
        ObservableList<GameStats> historyData = FXCollections.observableArrayList();
        fixtureData.addAll(playerDetails.getFixtures());
        historyData.addAll(playerDetails.getHistory());
        fixtureTable.setItems(fixtureData);
        historyTable.setItems(historyData);

        Image image = DataReader.getPhoto(player.getPhoto().replace("jpg", "png"));
        progressIndicator.progressProperty().bind(image.progressProperty());
        playerPhoto.setImage(image);
    }

    private void initOverall(){
        assistLabel.setText(player.getAssists() + "");
        formLabel.setText(player.getForm() + "");
        goalLabel.setText(player.getGoals_scored() + "");
        nameLabel.setText(player.getFirst_name() + " " + player.getSecond_name());
        posLabel.setText(player.getElement_type());
        priceLabel.setText(player.getNow_cost() + "");
        ptsLabel.setText(player.getTotal_points() + "");
        sbLabel.setText(player.getSelected_by_percent() + "");
        teamLabel.setText(player.getTeam());
    }

    private void setStats(String pos, GameStats gs []){
        switch (pos){
            case "GKP":
                statsGKP(gs);
                break;
            case "DEF":
                statsDEF(gs);
                break;
            case "MID":
                statsMID(gs);
                break;
            case "FWD":
                statsFWD(gs);
                break;
        }
    }

    private void statsGKP(GameStats gs []){
        this.statOne.setText("Saves");
        this.statTwo.setText("Goal errors");
        this.statThree.setText("Penalties saved");
        int saves = 0, errors =  0, penSaved = 0;
        for (GameStats s : gs){
            saves += s.getSaves();
            errors += s.getErrors_leading_to_goal();
            penSaved += s.getPenalties_saved();
        }

        statOneVal.setText(saves + "");
        statTwoVal.setText(errors + "");
        statThreeVal.setText(penSaved + "");
    }

    private void statsDEF(GameStats gs []){
        this.statOne.setText("Interceptions & Blocks");
        this.statTwo.setText("Goal errors");
        this.statThree.setText("Tackles");
        int iB = 0, errors =  0, tackles = 0;
        for (GameStats s : gs){
            iB += s.getClearances_blocks_interceptions();
            errors += s.getErrors_leading_to_goal();
            tackles += s.getTackles();
        }

        statOneVal.setText(iB + "");
        statTwoVal.setText(errors + "");
        statThreeVal.setText(tackles + "");

    }

    private void statsMID(GameStats gs []){
        this.statOne.setText("Key passes");
        this.statTwo.setText("Dribbles");
        this.statThree.setText("Chances created");
        int passes = 0, dribbles =  0, chances = 0;
        for (GameStats s : gs){
            passes += s.getKey_passes();
            dribbles += s.getDribbles();
            chances += s.getBig_chances_created();
        }

        statOneVal.setText(passes + "");
        statTwoVal.setText(dribbles + "");
        statThreeVal.setText(chances + "");
    }

    private void statsFWD(GameStats gs []){
        this.statOne.setText("Shots off target");
        this.statTwo.setText("Chances wasted");
        this.statThree.setText("Chances created");
        int shotsOff = 0, wasted =  0, chances = 0;
        for (GameStats s : gs){
            shotsOff += s.getTarget_missed();
            wasted += s.getBig_chances_missed();
            chances += s.getBig_chances_created();
        }

        statOneVal.setText(shotsOff + "");
        statTwoVal.setText(wasted + "");
        statThreeVal.setText(chances + "");

    }




    private void initHistoryTable(){
        csColumn.setCellValueFactory(new PropertyValueFactory<>("clean_sheets"));
        addLabel(csColumn,"CS", "Clean sheets");
        goalColumn.setCellValueFactory(new PropertyValueFactory<>("goals_scored"));
        addLabel(goalColumn,"GS", "Goals scored");
        ntColumn.setCellValueFactory(new PropertyValueFactory<>("transfers_balance"));
        addLabel(ntColumn,"NT", "Net transfers");
        ictColumn.setCellValueFactory(new PropertyValueFactory<>("ict_index"));
        addLabel(ictColumn, "ICT", "Combination of influence, creativity and threat of a player");
        rcColumn.setCellValueFactory(new PropertyValueFactory<>("red_cards"));
        addLabel(rcColumn, "RC", "Red cards");
        gcColumn.setCellValueFactory(new PropertyValueFactory<>("goals_conceded"));
        addLabel(gcColumn, "GC", "Goals conceded");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("total_points"));
        ycColumn.setCellValueFactory(new PropertyValueFactory<>("yellow_cards"));
        addLabel(ycColumn, "YC", "Yellow cards");
        pmColumn.setCellValueFactory(new PropertyValueFactory<>("penalties_missed"));
        addLabel(pmColumn, "PM", "Penalties missed");
        bColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));
        addLabel(bColumn, "B", "Bonus");
        gwColumn.setCellValueFactory(new PropertyValueFactory<>("round"));
        assistColumn.setCellValueFactory(new PropertyValueFactory<>("assists"));
        addLabel(assistColumn, "A", "Assists");
        ogColumn.setCellValueFactory(new PropertyValueFactory<>("own_goals"));
        addLabel(ogColumn, "OG", "Own goals");
        sbColumn.setCellValueFactory(new PropertyValueFactory<>("selected"));
        addLabel(sbColumn, "SB", "Selected by");
        psColumn.setCellValueFactory(new PropertyValueFactory<>("penalties_saved"));
        addLabel(psColumn, "PS", "Penalties saved");
        valColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        addLabel(valColumn, "V", "Value");
        sColumn.setCellValueFactory(new PropertyValueFactory<>("saves"));
        addLabel(sColumn, "S", "Saves");
        oppositionColumn.setCellValueFactory(new PropertyValueFactory<>("Opponent_team"));


    }

    private void addLabel(TableColumn column, String labelText, String tipText){
        Label label = new Label(labelText);
        label.setTooltip(new Tooltip(tipText));
        column.setGraphic(label);
    }
}
