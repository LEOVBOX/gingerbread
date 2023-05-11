package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class ResourceWindowController {

    @FXML
    AnchorPane mainPane;

    public Scene scene;

    void initialize() {
        scene = new Scene(mainPane, 400 ,200);

    }
}
