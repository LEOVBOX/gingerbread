package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesTabController extends TabController {

    private ArrayList<Recipe> recipes;


    @FXML
    void initialize() throws IOException {
        bindElementsScale();
        recipes = Gingerbread.loadRecipes();
        System.out.println("Init recipes tab");
        for (Recipe recipe: recipes) {
            addRecipe(recipe);
            recipe.print();
        }
    }

    public void setApplication(Application application) {
        this.application = application;
    }


    @FXML
    public void addRecipe(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-pane.fxml"));
        HBox hBox = loader.load();
        hBox.prefWidthProperty().bind(contentVbox.widthProperty());
        hBox.prefHeightProperty().bind(contentVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        label.setText(recipe.getName());
        contentVbox.getChildren().add(hBox);
    }

    @FXML
    void addNewRecipe() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-window.fxml"));
        Parent recipeWindow = loader.load();
        RecipeWindowController controller = loader.getController();
        controller.setTabController(this);
        controller.showRecipeWindow(recipeWindow, null);
    }


}