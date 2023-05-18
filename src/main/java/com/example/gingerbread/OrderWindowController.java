package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class OrderWindowController {
    @FXML
    private VBox mainVbox;

    @FXML
    private HBox titleHbox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField orderName;

    @FXML
    private TextArea orderDescription;

    @FXML
    private ScrollPane recipesScrollPane;

    @FXML
    private VBox recipesVbox;

    @FXML
    private HBox recipesLabelHbox;

    @FXML
    private Label recipesLabel;

    @FXML
    private Button addRecipeButton;

    @FXML
    private TextField deadlineTextField;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane descriptionPane;

    @FXML
    private AnchorPane recipesPane;

    @FXML
    private HBox bottomHbox;

    @FXML
    private VBox mainRecipesVbox;

    private OrderController orderController;
    private OrdersTabController tabController;

    private Scene orderScene;

    private Stage orderStage;

    private Order order;

    private ArrayList<Recipe> recipes;

    @FXML
    void initialize() throws IOException {
        orderScene = new Scene(mainVbox, 700, 700);
        SplitPane.setResizableWithParent(recipesPane, true);
        SplitPane.setResizableWithParent(descriptionPane, true);
        titleHbox.prefWidthProperty().bind(mainVbox.widthProperty());


        descriptionPane.prefWidthProperty().bind(splitPane.widthProperty());

        recipesPane.prefWidthProperty().bind(splitPane.widthProperty());

        bottomHbox.prefWidthProperty().bind(mainVbox.widthProperty());

        splitPane.prefHeightProperty().bind(mainVbox.heightProperty().multiply(0.7));


        orderDescription.prefWidthProperty().bind(descriptionPane.widthProperty());
        orderDescription.prefHeightProperty().bind(descriptionPane.heightProperty());

        scrollPane.prefWidthProperty().bind(descriptionPane.widthProperty());
        scrollPane.prefHeightProperty().bind(descriptionPane.heightProperty());

        recipesScrollPane.prefWidthProperty().bind(recipesPane.widthProperty());
        recipesScrollPane.prefHeightProperty().bind(recipesPane.heightProperty());

        mainRecipesVbox.prefWidthProperty().bind(mainVbox.widthProperty());
        mainRecipesVbox.prefHeightProperty().bind(recipesPane.heightProperty());

        recipesLabelHbox.prefWidthProperty().bind(mainRecipesVbox.widthProperty());
        recipesLabel.prefWidthProperty().bind(recipesLabelHbox.widthProperty());

        recipesVbox.prefWidthProperty().bind(mainRecipesVbox.widthProperty());
        recipesVbox.prefHeightProperty().bind(mainRecipesVbox.heightProperty());

        orderName.prefWidthProperty().bind(titleHbox.prefWidthProperty());


    }

    void setOrderController(OrderController controller) {
        this.orderController = controller;
    }

    void setTabController(OrdersTabController recipesTabController) {
        tabController = recipesTabController;
    }

    public String getOrderName() {
        return orderName.getText();
    }

    void showOrderWindow(Parent orderWindow, String name) throws IOException {
        orderScene = orderWindow.getScene();
        orderStage = new Stage();
        //String css = getClass().getResource("recipe_window.css").toExternalForm();
        if (name != null) {
            order = Gingerbread.getOrderByName(name) ;
            if (order != null)
            {
                orderName.setText(name);
                orderDescription.setText(order.getDescription());
                deadlineTextField.setText(order.getDeadline());
                recipes = Gingerbread.loadRecipes("recipes");
                System.out.println("Init resources");
                for (Recipe recipe: recipes) {
                    addRecipe(recipe);
                    recipe.print();
                }
                // Добавить загрузку картинки в ImageView
            }
        }

        //recipeScene.getStylesheets().add(css);
        orderStage.setScene(orderScene);
        orderStage.setTitle(name);
        orderStage.show();
    }

    @FXML
    void saveChanges() throws IOException {
        if (order == null) {
            order = new Order();
        }
        order.setName(orderName.getText());
        order.setDeadline(order.getDeadline());
        order.setDescription(orderDescription.getText());
        order.setDeadline(deadlineTextField.getText());

        // Сделать подсчет общей суммы заказа

        order.save();
        // Закрытие окна
        Stage stage = (Stage) mainVbox.getScene().getWindow();
        stage.close();

        if (tabController != null)
        {
            tabController.addOrder(order);
        }
        else if (orderController != null)
        {
            orderController.saveChanges(order.getName(), order.getDeadline(), order.getCost());
        }
    }


    public void addRecipe(Recipe recipe) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-pane.fxml"));
        HBox hBox = loader.load();
        RecipeController controller = loader.getController();
        controller.setOrderWindowController(this);
        hBox.prefWidthProperty().bind(recipesVbox.widthProperty());
        hBox.prefHeightProperty().bind(recipesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        label.setText(recipe.getName());
        recipesVbox.getChildren().add(hBox);
    }

    @FXML
    void addNewRecipe() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-choice-window.fxml"));
        Parent recipeChoiceWindow = loader.load();
        RecipeChoiceWindowController controller = loader.getController();
        controller.setOrderWindowController(this);
        controller.showRecipeChoiceWindow(recipeChoiceWindow);

    }

}
