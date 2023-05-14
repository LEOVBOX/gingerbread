package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    private Scene resourceScene;
    private Stage resourceStage;


    private Resource resource;
    private ResourceTabController tabController;
    private ResourceController resourceController;

    void setTabController(ResourceTabController resourceTabController) {
        this.tabController = resourceTabController;
    }

    void setResourceController(ResourceController resourceController) {
        this.resourceController = resourceController;
    }


    @FXML
    void initialize() {
        resourceScene = new Scene(mainPane, 400 ,200);

    }

    @FXML
    void saveChanges() throws IOException {
        if (resource == null) {
            resource = new Resource();
        }
        resource.name = nameText.getText();
        resource.units = unitsText.getText();
        resource.count = Integer.parseInt(countText.getText());
        resource.save();
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
        if (tabController != null)
        {
            tabController.addResourse(resource);
        }
        if (resourceController != null)
        {
            resourceController.saveChanges(resource.name);
        }
    }

    @FXML
    void cancel() {
        resourceStage.close();
    }

    void showResouceWindow(Parent resChangeWindow, String name) throws IOException{
        resourceScene = resChangeWindow.getScene();
        resourceStage = new Stage();
        String css = getClass().getResource("resource_window.css").toExternalForm();
        if (name != null) {
            resource = Gingerbread.getResourseByName(name) ;
            if (resource != null)
            {
                nameText.setText(name);
                countText.setText(Integer.toString(resource.count));
                unitsText.setText(resource.units);
            }
        }

        resourceScene.getStylesheets().add(css);
        resourceStage.setScene(resourceScene);
        resourceStage.setTitle("Resource");
        resourceStage.setResizable(false);
        resourceStage.show();
    }


}
