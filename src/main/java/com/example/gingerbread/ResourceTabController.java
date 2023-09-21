package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;

public class ResourceTabController extends TabController{

    public ArrayList<Resource> resources;

    @FXML
    void initialize() throws IOException {
        bindElementsScale();
        resources = Gingerbread.loadResources("resources");
        System.out.println("Init resource tab");
        for (Resource res: resources) {
            loadResourсe(res);
            res.print();
        }

    }

    public void loadResourсe(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-pane.fxml"));
        HBox hBox = loader.load();
        ResourceController controller = loader.getController();
        controller.setResourceTabController(this);
        hBox.prefWidthProperty().bind(contentVbox.widthProperty());
        hBox.prefHeightProperty().bind(contentVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        Label countLabel = (Label) hBox.lookup("#countLabel");
        Label unitsLabel = (Label) hBox.lookup("#unitsLabel") ;
        countLabel.setText(Integer.toString(resource.count));
        unitsLabel.setText(resource.units);
        label.setText(resource.name);
        contentVbox.getChildren().add(hBox);
    }

    @FXML
    void addNewResource() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-window.fxml"));
        Resource newRes = new Resource();
        Parent resChangeWindow = loader.load();
        ResourceWindowController controller = loader.getController();
        controller.setTabController(this);
        controller.showResourceWindow(resChangeWindow, null, "resources");
    }
}