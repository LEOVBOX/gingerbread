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

public class ResourceController {
    @FXML
    Label label;

    @FXML
    HBox hBox;

    @FXML
    Button button;

    @FXML
    Label countLabel;

    @FXML
    Label unitsLabel;

    private ResourceTabController resourceTabController;
    private RecipeWindowController recipeWindowController;

    public void setResourceTabController(ResourceTabController controller) {
        resourceTabController = controller;
    }

    public void setRecipeWindowController(RecipeWindowController controller) {
        recipeWindowController = controller;
    }



    @FXML
    void initialize() {
        countLabel.prefWidthProperty().bind(hBox.prefWidthProperty().multiply(0.2));
        unitsLabel.prefWidthProperty().bind(hBox.prefWidthProperty().multiply(0.2));
        label.prefWidthProperty().bind(hBox.widthProperty().multiply(0.6));
        label.prefHeightProperty().bind(hBox.heightProperty());
        button.prefWidthProperty().bind(hBox.prefWidthProperty());
        String css = getClass().getResource("resource_Pane.css").toExternalForm();
        hBox.getStylesheets().add(css);

    }

    AtomicBoolean confirmDelete() throws IOException {
        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setTitle("Подтверждение удаления resourse");

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
    void removeResource() throws IOException, SQLException {
        if (confirmDelete().get()) {
            Resource delResource = null;
            if (resourceTabController != null) {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("resources-tab.fxml"));
//                loader.load();
                delResource = Gingerbread.getResourceByName(this.label.getText(), "resources");
                delResource.deleteResource("resources");
            }
            else if(recipeWindowController != null) {
                String tableName = recipeWindowController.getRecipeName() + "_RecipeResources";
                delResource = Gingerbread.getResourceByName(
                        this.label.getText(), tableName);
                delResource.deleteResource(tableName);
            }


            Pane pane = (Pane) hBox.getParent();
            pane.getChildren().remove(hBox);
        }
    }

    @FXML
    void changeResource() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-window.fxml"));
        Parent resChangeWindow = loader.load();
        ResourceWindowController controller = loader.getController();
        controller.setResourceController(this);
        String tableName = null;
        if (recipeWindowController == null) {
            tableName = "resources";
        }
        else {
            tableName = recipeWindowController.getRecipeName() + "_RecipeResources";
        }
        controller.showResourceWindow(resChangeWindow, label.getText(), tableName);
    }

    void saveChanges(String name, String count, String units) {
        label.setText(name);
        countLabel.setText(count);
        unitsLabel.setText(units);
    }

}
