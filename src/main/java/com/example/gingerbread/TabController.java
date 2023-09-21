package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TabController{

    protected Scene scene;

    protected Application application;

    @FXML
    protected SplitPane mainPane;

    @FXML
    protected Pane labelBar;

    @FXML
    protected Pane sideBar;

    @FXML
    protected Pane leftPane;

    @FXML
    protected Button addButton;

    @FXML
    protected Label tabLabel;

    @FXML
    protected Label resourcesTab;

    @FXML
    protected Label recipesTab;

    @FXML
    protected Label ordersTab;

    @FXML
    protected ScrollPane scrollPane;

    @FXML
    protected Pane rightVertPane;


    @FXML
    protected Pane rightPane;

    @FXML
    protected Pane contentVbox;

    public void setApplication(Application application) {
        this.application = application;
    }

    public void bindElementsScale() {
        scene = new Scene(mainPane, 700, 700);
        SplitPane.setResizableWithParent(rightPane, true);
        rightPane.prefWidthProperty().bind(mainPane.widthProperty());
        rightPane.prefHeightProperty().bind(mainPane.heightProperty());
        SplitPane.setResizableWithParent(leftPane, true);


        rightVertPane.prefHeightProperty().bind(rightPane.heightProperty());
        rightVertPane.prefWidthProperty().bind(rightPane.widthProperty());

        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());
        scrollPane.prefHeightProperty().bind(rightPane.heightProperty());

        contentVbox.prefWidthProperty().bind(scrollPane.widthProperty());
        contentVbox.prefHeightProperty().bind(scrollPane.heightProperty());


        labelBar.prefWidthProperty().bind(rightPane.widthProperty());

        sideBar.prefWidthProperty().bind(leftPane.widthProperty());
        sideBar.prefHeightProperty().bind(leftPane.heightProperty());

        resourcesTab.prefWidthProperty().bind(sideBar.widthProperty());
        recipesTab.prefWidthProperty().bind(sideBar.widthProperty());
        ordersTab.prefWidthProperty().bind(sideBar.widthProperty());

        DoubleBinding tabLabelBind = labelBar.widthProperty().multiply(0.8);
        tabLabel.prefWidthProperty().bind(tabLabelBind);

        DoubleBinding addButtonBind = labelBar.widthProperty().multiply(0.2);
        addButton.prefWidthProperty().bind(addButtonBind);

        mainPane.prefWidthProperty().bind(scene.widthProperty());
    }

    @FXML
    protected void showResourcesTab() throws IOException {
        application.showTab("resources-tab.fxml", "resources-stage.css");
    }

    @FXML
    protected void showRecipesTab() throws IOException {
        application.showTab("recipes-tab.fxml", "resources-stage.css");
    }

    @FXML
    void showOrdersTab() throws IOException {
        application.showTab("orders-tab.fxml", "resources-stage.css");
    }


}
