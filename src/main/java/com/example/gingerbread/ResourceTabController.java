package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;

public class ResourceTabController {

    @FXML
    private Label resourseLabel;

    @FXML
    private Button addButton;

    @FXML
    private SplitPane mainPane;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane labelBar;

    @FXML
    private Pane sideBar;

    @FXML
    private Pane rightVertPane;


    @FXML
    private Pane rightPane;

    @FXML
    public Pane resoursesVbox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label resourcesTab;

    @FXML
    private Label recipesTab;

    @FXML
    private Label ordersTab;

    private Scene scene;

    private Application application;


    public ArrayList<Resource> resources;

    @FXML
    void initialize() throws IOException {
        scene = new Scene(mainPane, 700, 700);
        SplitPane.setResizableWithParent(rightPane, true);
        rightPane.prefWidthProperty().bind(mainPane.widthProperty());
        rightPane.prefHeightProperty().bind(mainPane.heightProperty());
        SplitPane.setResizableWithParent(leftPane, true);


        rightVertPane.prefHeightProperty().bind(rightPane.heightProperty());
        rightVertPane.prefWidthProperty().bind(rightPane.widthProperty());

        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());
        scrollPane.prefHeightProperty().bind(rightPane.heightProperty());

        resoursesVbox.prefWidthProperty().bind(scrollPane.widthProperty());
        resoursesVbox.prefHeightProperty().bind(scrollPane.heightProperty());


        labelBar.prefWidthProperty().bind(rightPane.widthProperty());

        sideBar.prefWidthProperty().bind(leftPane.widthProperty());
        sideBar.prefHeightProperty().bind(leftPane.heightProperty());

        resourcesTab.prefWidthProperty().bind(sideBar.widthProperty());
        recipesTab.prefWidthProperty().bind(sideBar.widthProperty());
        ordersTab.prefWidthProperty().bind(sideBar.widthProperty());

        DoubleBinding resourseLabelBind = labelBar.widthProperty().multiply(0.8);
        resourseLabel.prefWidthProperty().bind(resourseLabelBind);

        DoubleBinding addButtonBind = labelBar.widthProperty().multiply(0.2);
        addButton.prefWidthProperty().bind(addButtonBind);

        mainPane.prefWidthProperty().bind(scene.widthProperty());

        resources = Gingerbread.loadResources("resources");
        System.out.println("Init resource tab");
        for (Resource res: resources) {
            addResourse(res);
            res.print();
        }

    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void addResourse(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-pane.fxml"));
        HBox hBox = loader.load();
        ResourceController controller = loader.getController();
        controller.setResourceTabController(this);
        hBox.prefWidthProperty().bind(resoursesVbox.widthProperty());
        hBox.prefHeightProperty().bind(resoursesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        Label countLabel = (Label) hBox.lookup("#countLabel");
        Label unitsLabel = (Label) hBox.lookup("#unitsLabel") ;
        countLabel.setText(Integer.toString(resource.count));
        unitsLabel.setText(resource.units);
        label.setText(resource.name);
        resoursesVbox.getChildren().add(hBox);
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

    @FXML
    void openRecipesTab() throws IOException {
        application.showRecepiesTab();
    }
}