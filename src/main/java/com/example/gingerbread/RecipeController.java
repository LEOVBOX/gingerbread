package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeController {
    @FXML
    Label label;

    @FXML
    HBox hBox;

    @FXML
    Button removeButton;



    @FXML
    void initialize() {
        label.prefWidthProperty().bind(hBox.widthProperty());
        label.prefHeightProperty().bind(hBox.heightProperty());
        removeButton.prefWidthProperty().bind(hBox.prefWidthProperty());
        String css = getClass().getResource("resourece_Pane.css").toExternalForm();
        hBox.getStylesheets().add(css);

    }

    AtomicBoolean confirmDelete() throws IOException {
        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setTitle("Подтверждение удаления recipe");

        // добавление кнопок
        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        confirmDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // установка контента диалогового окна
        StackPane content = new StackPane();
        content.getChildren().add(new Text("Удалить ресурс?"));
        confirmDialog.getDialogPane().setContent(content);

        AtomicBoolean buttonRes = new AtomicBoolean(false);
        // показ диалогового окна и ожидание закрытия
        confirmDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == okButtonType) {
                buttonRes.set(true);
            }
        });
        return buttonRes;
    }

    @FXML
    void removeRecipe() throws IOException, SQLException {
        if (confirmDelete().get()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("recipes-tab.fxml"));
            loader.load();
            RecipesTabController controller = loader.getController();
            Recipe delRecipe = Gingerbread.getRecipeByName(this.label.getText());
            delRecipe.deleteRecipe();
            Pane pane = (Pane) hBox.getParent();
            pane.getChildren().remove(hBox);
        }
    }

    @FXML
    void openRecipeWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe-window.fxml"));
        Parent resChangeWindow = loader.load();
        RecipeWindowController controller = loader.getController();
        controller.setResourceController(this);
        controller.showRecipeWindow(resChangeWindow, label.getText());
    }


    void saveChanges(String newLabel) {
        label.setText(newLabel);
    }

}
