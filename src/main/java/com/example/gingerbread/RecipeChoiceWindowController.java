package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeChoiceWindowController {
    @FXML
    private VBox mainVbox;
    @FXML
    private Label windowLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox recipesVbox;

    private Scene scene;

    private Stage stage;

    private OrderWindowController orderWindowController;

    public void setOrderWindowController(OrderWindowController controller) {
        orderWindowController = controller;
    }

    @FXML
    void initialize() {
        scene = new Scene(mainVbox, 600 ,600);
        windowLabel.prefWidthProperty().bind(mainVbox.prefWidthProperty());
        scrollPane.prefHeightProperty().bind(mainVbox.prefWidthProperty());
        recipesVbox.prefWidthProperty().bind(scrollPane.widthProperty());
    }

    void showRecipeChoiceWindow(Parent recipeWindow) throws IOException {
        scene = recipeWindow.getScene();
        stage = new Stage();
        //String css = getClass().getResource("recipe_window.css").toExternalForm();



        //recipeScene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("Выбор рецептов");
        stage.show();
    }






}
