package com.example.gingerbread;

import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChoiceRecipeController extends RecipeController{
    private RecipeChoiceWindowController recipeChoiceWindowController;

    private OrderWindowController orderWindowController;

    public void setOrderWindowController(OrderWindowController controller) {
        orderWindowController = controller;
    }

    public OrderWindowController getOrderWindowController() {
        return orderWindowController;
    }

    public void setRecipeChoiceWindowController(RecipeChoiceWindowController controller) {
        this.recipeChoiceWindowController = controller;
    }

    @FXML
    void onButtonClick() throws SQLException, IOException {

//        if (recipeChoiceWindowController != null) {
//            // Увеличиваем количество рецептов \ добавляем рецепт в заказ
//        }
//        else {
//            this.removeRecipe();
//        }
    }

    void addOrdersRecipe(String recipeName) throws SQLException {

    }

    void deleteOrdersRecipe(String recipeName) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:/Users/leonid/IdeaProjects/gingerbread/database.sqlite");
            String tableName = getOrderWindowController().getOrderName() + "_OrdersRecipes";
            String sql = "DELETE FROM " + tableName + " WHERE recipeName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipeName);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("DROP TABLE IF EXISTS " + tableName);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    void removeRecipe() throws IOException {
        if (confirmDelete().get()) {
            String recipeName = this.label.getText();

        }
    }

}
