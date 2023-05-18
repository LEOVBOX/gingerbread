package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeWindowController {
    @FXML
    private ImageView image;

    @FXML
    private VBox mainVbox;

    @FXML
    private HBox titleHbox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField recipeName;

    @FXML
    private TextArea recipeDescription;

    @FXML
    private ScrollPane resourcesScrollPane;

    @FXML
    private VBox resourcesVbox;

    @FXML
    private HBox resourcesLabelHbox;

    @FXML
    private Label resourcesLabel;

    @FXML
    private Button addResourceButton;

    private RecipeController recipeController;
    private RecipesTabController tabController;

    private Scene recipeScene;

    private Stage recipeStage;

    private Recipe recipe;

    private ArrayList<Resource> resources;

    @FXML
    void initialize() throws IOException {
        recipeScene = new Scene(mainVbox, 700, 700);
        titleHbox.prefWidthProperty().bind(mainVbox.widthProperty());

        scrollPane.prefHeightProperty().bind(mainVbox.widthProperty());
        scrollPane.prefHeightProperty().bind(mainVbox.heightProperty().multiply(0.5));

        recipeDescription.prefWidthProperty().bind(scrollPane.widthProperty());
        recipeDescription.prefHeightProperty().bind(scrollPane.heightProperty());

        resourcesLabelHbox.prefWidthProperty().bind(mainVbox.widthProperty());

        resourcesScrollPane.prefWidthProperty().bind(mainVbox.widthProperty());

        resourcesVbox.prefWidthProperty().bind(resourcesScrollPane.widthProperty());

        recipeName.prefWidthProperty().bind(titleHbox.prefWidthProperty());


    }

    void setResourceController(RecipeController recipeController) {
        this.recipeController = recipeController;
    }

    void setTabController(RecipesTabController recipesTabController) {
        tabController = recipesTabController;
    }

    public String getRecipeName() {
        return recipeName.getText();
    }

    void showRecipeWindow(Parent recipeWindow, String name) throws IOException {
        recipeScene = recipeWindow.getScene();
        recipeStage = new Stage();
        //String css = getClass().getResource("recipe_window.css").toExternalForm();
        if (name != null) {
            recipe = Gingerbread.getRecipeByName(name) ;
            if (recipe != null)
            {
                recipeName.setText(name);
                recipeDescription.setText(recipe.getDescription());
                // Добавить загрузку картинки в ImageView
            }
        }
        if (name != null) {
            resources = Gingerbread.loadResources(recipeName.getText() + "_RecipeResources");
            System.out.println("Init resources");
            for (Resource res: resources) {
                addResource(res);
                res.print();
            }
        }


        //recipeScene.getStylesheets().add(css);
        recipeStage.setScene(recipeScene);
        recipeStage.setTitle(name);
        recipeStage.show();
    }

    @FXML
    void saveChanges() throws IOException {
        if (recipe == null) {
            recipe = new Recipe();
        }
        recipe.setName(recipeName.getText());
        recipe.setDescription(recipeDescription.getText());

        recipe.save();
        Stage stage = (Stage) mainVbox.getScene().getWindow();
        stage.close();
        if (tabController != null)
        {
            tabController.addRecipe(recipe);
        }
        if (recipeController != null)
        {
            recipeController.saveChanges(recipe.getName());
        }
    }


    public void addResource(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-pane.fxml"));
        HBox hBox = loader.load();
        ResourceController controller = loader.getController();
        controller.setRecipeWindowController(this);
        hBox.prefWidthProperty().bind(resourcesVbox.widthProperty());
        hBox.prefHeightProperty().bind(resourcesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        Label countLabel = (Label) hBox.lookup("#countLabel");
        Label unitsLabel = (Label) hBox.lookup("#unitsLabel") ;
        countLabel.setText(Integer.toString(resource.count));
        unitsLabel.setText(resource.units);
        label.setText(resource.name);
        resourcesVbox.getChildren().add(hBox);
    }

    @FXML
    void addNewResource() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-window.fxml"));
        Parent resChangeWindow = loader.load();
        ResourceWindowController controller = loader.getController();
        controller.setRecipeWindowController(this);
        controller.showResourceWindow(resChangeWindow, null, this.recipeName.getText() + "_RecipeResources");
    }

}
