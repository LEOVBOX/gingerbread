package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesTabController {

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

    private HelloApplication application;

    private ArrayList<Recipe> recipes;


    @FXML
    void initialize() throws IOException {
        scene = new Scene(mainPane, 500, 500);
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

        recipes = Gingerbread.loadRecipes();
        System.out.println("Init resource tab");
        for (Recipe recipe: recipes) {
            addRecipe(recipe);
            recipe.print();
        }

    }

    public void setApplication(HelloApplication application) {
        this.application = application;
    }

    @FXML
    void showResourcesTab() throws IOException {
        application.showResourcesTab();
    }



    @FXML
    public void addRecipe(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-pane.fxml"));
        HBox hBox = loader.load();
        hBox.prefWidthProperty().bind(resoursesVbox.widthProperty());
        hBox.prefHeightProperty().bind(resoursesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        label.setText(recipe.getName());
        resoursesVbox.getChildren().add(hBox);
    }




}