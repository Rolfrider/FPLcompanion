package com.Rolfrider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class WindowController implements Initializable {

    private DataBase dataBase = new DataBase();
    private ArrayList<Player> players = new ArrayList<>(dataBase.selectData());
    @FXML
    private Label updateLabel;
    @FXML
    private TextField nameText;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private ChoiceBox<String> dropDownPrice;
    @FXML
    private ChoiceBox<String> dropDownClub;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> clubColumn;
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

        updateDate();
        initTable();
        intitDropDownPrice();
        intitDropDownClub();

    }

    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(TimeUnit.SECONDS.toMillis(dataBase.selectTime()));
        String text = updateLabel.getText();
        updateLabel.setText(text.split(":")[0] + ": " + sdf.format(date) );
    }

    public void updateData(){
            for(Player p : DataReader.Read())
                dataBase.insertData(p);
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
        playerTable.setItems(getPlayers());
    }

    private void updateTable(ArrayList<Player> subList){
        playerTable.setItems(getPlayers(subList));
    }



    private void intitDropDownPrice(){
        dropDownPrice.getItems().addAll("Unlimited", "12.0", "11.0", "10.0", "9.0", "8.0",
                "7.0", "6.0", "5.0", "4.0");
        dropDownPrice.setValue("Unlimited");

        dropDownPrice.getSelectionModel().selectedIndexProperty().addListener( (v, oldVal ,newVal)->
                onDropDownPrice(dropDownPrice.getItems().get(newVal.intValue())));

    }

    private void intitDropDownClub(){
        dropDownClub.getItems().addAll("ALL","ARS", "BOU", "BHA", "BUR", "CHE", "CRY",
                "EVE", "HUD", "LEI", "LIV", "MCI", "MUN", "NEW", "SOU", "STK", "SWA", "TOT",
                "WAT", "WBA", "WHU");
        dropDownClub.setValue("ALL");

        dropDownClub.getSelectionModel().selectedIndexProperty().addListener( (v, oldVal ,newVal)->{
            String value = dropDownClub.getItems().get(newVal.intValue());
            if(value.equals("ALL")){
                updateTable(this.players);
            }else {
                ArrayList<Player> players = new ArrayList<>();
                for (Player p : this.players) {
                    if (p.getTeam().equals(value))
                        players.add(p);
                }
                updateTable(players);
            }
                });

    }

    private void onDropDownPrice(String value){
        if(value.equals("Unlimited"))
            updateTable(players);
        else
            updateTable(createSubList(Float.parseFloat(value)));


    }


    private ArrayList<Player> createSubList(float value){
        ArrayList<Player> players = new ArrayList<>();
        for(Player p : this.players){
            if(p.getNow_cost() <= value)
                players.add(p);
        }
        return players;
    }

    public void nameTyping(){
        String text = nameText.getText();
        updateTable(dataBase.selectData(text + "%", "like", "web_name"));
    }

}
