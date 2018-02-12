package com.Rolfrider.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailedWindowController implements Initializable{


    @FXML
    private ImageView playerPhoto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerPhoto.setImage(new Image("https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p111457.png"));
    }

    public void showMainWindow(ActionEvent event) throws IOException {
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("window.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }


}
