package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResourceController {
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
        removeButton.prefWidthProperty().bind(hBox.prefWidthProperty().multiply(0.05));
        String css = getClass().getResource("resourece_Pane.css").toExternalForm();
        hBox.getStylesheets().add(css);

    }

    AtomicBoolean confirmDelete() throws IOException {

        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setTitle("Подтверждение удаления resourse");
        Pane dialogPane = confirmDialog.getDialogPane();
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
    void removeResource() throws IOException {
        if (confirmDelete().get()) {
            Pane pane = (Pane) hBox.getParent();
            pane.getChildren().remove(hBox);
        }
    }
}
