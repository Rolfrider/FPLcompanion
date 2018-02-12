package com.Rolfrider.GUI;

import com.Rolfrider.Data.DataBase;
import com.Rolfrider.Data.Player;
import com.Rolfrider.Data.UpdateTask;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WindowController implements Initializable {

    private DataBase dataBase ;

    private ArrayList<Player> players ;

    @FXML
    private ProgressBar updateProgress;
    @FXML
    private  Label updateLabel;
    @FXML
    private TextField nameText;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private ChoiceBox<String> dropDownClub, dropDownPosition, dropDownPrice ;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> clubColumn;
    @FXML
    private TableColumn<Player, String> newsColumn;
    @FXML
    private TableColumn<Player, Float> pointsPerGameColumn;
    @FXML
    private TableColumn<Player, String> positionColumn;
    @FXML
    private TableColumn<Player, Float> costColumn;
    @FXML
    private TableColumn<Player, Float> formColumn;
    @FXML
    private TableColumn<Player, Integer> totalPointsColumn;
    @FXML
    private TableColumn<Player, Float> ictIndexColumn;
    @FXML
    private TableColumn<Player, Float> selectedByColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBase = new DataBase( updateLabel, this);
        players =  DataBase.selectData();
        updateDate();
        initTable();
        intitDropDownPrice();
        intitDropDownClub();
        intitDropDownPosition();

    }

    public void showDetailedWindow(Event event) throws IOException{
        Parent detailedWindowParent = FXMLLoader.load(getClass().getResource("detailedWindow.fxml"));
        Scene detailedWindowScene = new Scene(detailedWindowParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(detailedWindowScene);
        window.show();
    }

    public void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(TimeUnit.SECONDS.toMillis(DataBase.selectTime()));
        String text = updateLabel.getText();
        updateLabel.setText(text.split(":")[0] + ": " + sdf.format(date) );
    }

    public void updateData(){

        UpdateTask task = new UpdateTask( updateProgress, updateLabel, this);
        updateProgress.setVisible(true);
        updateProgress.progressProperty().bind(task.progressProperty());
        Thread thread = new Thread(task);
        thread.start();
        updateDate();
    }

    private ObservableList<Player> getPlayers(){
        ObservableList<Player> players = FXCollections.observableArrayList();
        players.addAll(this.players);
        return players;
    }
    private ObservableList<Player> getPlayers(ArrayList<Player> players){
        ObservableList<Player> observableList = FXCollections.observableArrayList();
        observableList.addAll(players);
        return observableList;
    }

    private void initTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("web_name"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("now_cost"));
        formColumn.setCellValueFactory(new PropertyValueFactory<>("form"));
        totalPointsColumn.setCellValueFactory(new PropertyValueFactory<>("total_points"));
        ictIndexColumn.setCellValueFactory(new PropertyValueFactory<>("ict_index"));
        selectedByColumn.setCellValueFactory(new PropertyValueFactory<>("selected_by_percent"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("element_type"));
        pointsPerGameColumn.setCellValueFactory(new PropertyValueFactory<>("points_per_game"));
        newsColumn.setCellValueFactory(new PropertyValueFactory<>("news"));
        playerTable.setItems(getPlayers());

        Label tsbLabel = new Label("TSB");
        tsbLabel.setTooltip(new Tooltip("Team selected by %"));
        selectedByColumn.setGraphic(tsbLabel);

        Label ictLabel = new Label("ICT");
        ictLabel.setTooltip(new Tooltip("Combination of influence, creativity and threat of a player"));
        ictIndexColumn.setGraphic(ictLabel);

        Label ppgLabel = new Label("PPG");
        ppgLabel.setTooltip(new Tooltip("Points per game"));
        pointsPerGameColumn.setGraphic(ppgLabel);

        playerTable.setTooltip(new Tooltip("Double click or press Enter to see details"));

        playerTable.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2) {
                try {
                    showDetailedWindow(event);
                }catch (IOException err){
                    System.out.println(err.getMessage());
                }
            }
        });

        playerTable.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                try {
                    showDetailedWindow(event);
                }catch (IOException err){
                    System.out.println(err.getMessage());
                }
            }
        });
    }

    private void addTooltip(){
        TableColumnHeader header = (TableColumnHeader) playerTable.lookup("#" + selectedByColumn.getId());
        Label label = (Label) header.lookup(".label");
        label.setTooltip(new Tooltip("Team selected by %"));
    }

    public void updateTable(ArrayList<Player> subList){

        playerTable.setItems(getPlayers(subList));
    }



    private void intitDropDownPrice(){
        dropDownPrice.getItems().addAll("Unlimited", "12.0", "11.0", "10.0", "9.0", "8.0",
                "7.0", "6.0", "5.0", "4.0");
        dropDownPrice.setValue("Unlimited");

        dropDownPrice.getSelectionModel().selectedIndexProperty().addListener( (v, oldVal ,newVal)->
                onDropDownPrice(dropDownPrice.getItems().get(newVal.intValue())));

    }
    private void intitDropDownPosition(){
        dropDownPosition.getItems().addAll("ALL", "GKP" , "DEF", "MID", "FWD" );
        dropDownPosition.setValue("ALL");

        dropDownPosition.getSelectionModel().selectedIndexProperty().addListener( (v, oldVal ,newVal)->
                onDropDownPosition(dropDownPosition.getItems().get(newVal.intValue())));

    }

    private void intitDropDownClub(){
        dropDownClub.getItems().addAll("ALL","ARS", "BOU", "BHA", "BUR", "CHE", "CRY",
                "EVE", "HUD", "LEI", "LIV", "MCI", "MUN", "NEW", "SOU", "STK", "SWA", "TOT",
                "WAT", "WBA", "WHU");
        dropDownClub.setValue("ALL");

        dropDownClub.getSelectionModel().selectedIndexProperty().addListener( (v, oldVal ,newVal)->
                onDropDownClub(dropDownClub.getItems().get(newVal.intValue())) );

    }

    private void onDropDownPrice(String value){
        if(value.equals("Unlimited"))
            updateTable(createSubList(14f, dropDownClub.getValue(), dropDownPosition.getValue()));
        else
            updateTable(createSubList(Float.parseFloat(value),dropDownClub.getValue(), dropDownPosition.getValue() ));
    }
    private void onDropDownPosition(String value){
        float priceVal;
        if(dropDownPrice.getValue().equals("Unlimited"))
            priceVal = 14f;
        else
            priceVal =Float.parseFloat(dropDownPrice.getValue());
        updateTable(createSubList(priceVal, dropDownClub.getValue(), value));
    }
    private void onDropDownClub(String value){
        float priceVal;
        if(dropDownPrice.getValue().equals("Unlimited"))
            priceVal = 14f;
        else
            priceVal =Float.parseFloat(dropDownPrice.getValue());
        updateTable(createSubList(priceVal, value, dropDownPosition.getValue() ));
    }

    private void setDropDowns(){
        dropDownPrice.setValue("Unlimited");
        dropDownPosition.setValue("ALL");
        dropDownClub.setValue("ALL");
    }


    private ArrayList<Player> createSubList(float priceVal, String clubVal, String positionVal){
        ArrayList<Player> players = new ArrayList<>();
        for(Player p : this.players){
            if(p.getNow_cost() <= priceVal &&
                    ( p.getTeam().equals(clubVal) || clubVal.equals("ALL") ) &&
                    ( p.getElement_type().equals(positionVal) || positionVal.equals("ALL") )
                    ) {
                players.add(p);
            }
        }
        return players;
    }

    public void nameTyping(){
        String text = nameText.getText();
        updateTable(dataBase.selectData(text + "%", "like", "web_name"));
        setDropDowns();
    }

}
