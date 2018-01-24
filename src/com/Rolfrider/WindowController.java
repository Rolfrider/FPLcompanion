package com.Rolfrider;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class WindowController implements Initializable {

    private DataBase dataBase;
    @FXML
    private Label updateLabel;
    @FXML
    private TableView<Player> playerTable;
    @FXML
    private TableColumn<Player, String> nameColumn;
    @FXML
    private TableColumn<Player, String> clubColumn;
    @FXML
    private TableColumn<Player, Float> costColumn;
    @FXML
    private TableColumn<Player, String> formColumn;
    @FXML
    private TableColumn<Player, Integer> totalPointsColumn;
    @FXML
    private TableColumn<Player, String> ictIndexColumn;
    @FXML
    private TableColumn<Player, String> selectedByColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBase = new DataBase();
        updateDate();
        initTable();
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
        players.addAll(dataBase.selectData());
        return players;
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


}
