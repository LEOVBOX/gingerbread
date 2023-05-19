package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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

    private ArrayList<Recipe> recipes;

    public void setOrderWindowController(OrderWindowController controller) {
        orderWindowController = controller;
    }

    @FXML
    void initialize() throws IOException {
        scene = new Scene(mainVbox, 600 ,600);
        windowLabel.prefWidthProperty().bind(mainVbox.prefWidthProperty());
        scrollPane.prefHeightProperty().bind(mainVbox.prefWidthProperty());
        recipesVbox.prefWidthProperty().bind(scrollPane.widthProperty());
        recipes = Gingerbread.loadRecipes();
        for (Recipe recipe: recipes) {
            addRecipe(recipe);
        }
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

    public void addRecipe(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-pane-choice.fxml"));
        HBox hBox = loader.load();

        String css = getClass().getResource("recipe_pane_choice.css").toExternalForm();
        hBox.getStylesheets().add(css);

        hBox.prefWidthProperty().bind(recipesVbox.widthProperty());
        hBox.prefHeightProperty().bind(recipesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        label.setText(recipe.getName());
        recipesVbox.getChildren().add(hBox);
    }




}
