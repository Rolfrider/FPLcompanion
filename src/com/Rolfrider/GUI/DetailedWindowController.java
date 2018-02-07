package com.Rolfrider.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class DetailedWindowController {


    public void showMainWindow(ActionEvent event) throws IOException {
        Parent mainWindowParent = FXMLLoader.load(getClass().getResource("window.fxml"));
        Scene mainWindowScene = new Scene(mainWindowParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
