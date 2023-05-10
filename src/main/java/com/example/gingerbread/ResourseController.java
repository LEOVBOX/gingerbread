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
    Pane resoursePane;

    @FXML
    Button removeButton;

    @FXML
    void initialize() {
        hBox.prefWidthProperty().bind(resoursePane.widthProperty());
        hBox.prefHeightProperty().bind(resoursePane.heightProperty());
        label.prefWidthProperty().bind(hBox.widthProperty().multiply(0.8));
        label.prefHeightProperty().bind(hBox.heightProperty());
        removeButton.prefWidthProperty().bind(hBox.prefWidthProperty().multiply(0.2));
    }
}
