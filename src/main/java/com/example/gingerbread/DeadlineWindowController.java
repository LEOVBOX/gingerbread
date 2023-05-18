package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DeadlineWindowController {
    @FXML
    AnchorPane mainPane;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    TextField deadlineTextField;

    private Scene scene;

    private Stage stage;

    private OrderController orderController;

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @FXML
    void initialize() {
        scene = new Scene(mainPane, 400, 200);
    }

    @FXML
    void saveChanges() {
        if (orderController != null) {
            orderController.deadlineLabel.setText(deadlineTextField.getText());
            Order order = Gingerbread.getOrderByName(orderController.label.getText());
            order.setDeadline(deadlineTextField.getText());
            order.save();
        }
        stage.close();
    }

    void showDeadlineWindow(Parent deadlineWindow, String deadline) {
        scene = deadlineWindow.getScene();
        stage = new Stage();
        String css = getClass().getResource("resource_window.css").toExternalForm();
        deadlineTextField.setText(deadline);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("deadline");
        stage.setResizable(false);
        stage.show();
    }



    @FXML
    void cancel() {
        stage.close();
    }
}
