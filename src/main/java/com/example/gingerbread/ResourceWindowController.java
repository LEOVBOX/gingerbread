package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ResourceWindowController {

    @FXML
    AnchorPane mainPane;

    @FXML
    Button saveButton;

    @FXML
    TextField nameText;

    @FXML
    TextField countText;

    @FXML
    TextField unitsText;

    public Scene scene;

    ResourceTabController mainController;

    void setMainController(ResourceTabController resourceTabController) {
        this.mainController = resourceTabController;
    }


    @FXML
    void initialize() {

        scene = new Scene(mainPane, 400 ,200);

    }

    @FXML
    void saveChanges() throws IOException {
        Resource resource = new Resource();
        resource.name = nameText.getText();
        resource.units = unitsText.getText();
        resource.save();
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        mainController.addResourse(resource);
    }


}
