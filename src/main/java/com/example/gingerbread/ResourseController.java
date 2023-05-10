package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ResourseController {
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
    }

    @FXML
    void removeResourse() {
        Pane pane = (Pane) hBox.getParent();
        pane.getChildren().remove(hBox);
    }
}
