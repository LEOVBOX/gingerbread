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

public class OrderController {
    @FXML
    Label label;

    @FXML
    HBox hBox;

    @FXML
    Button removeButton;

    @FXML
    Label deadlineLabel;

    @FXML
    Label costLabel;

    private OrdersTabController ordersTabController;

    public void setOrdersTabController(OrdersTabController controller) {ordersTabController = controller;}



    @FXML
    void initialize() {
        label.prefWidthProperty().bind(hBox.widthProperty().multiply(0.4));
        label.prefHeightProperty().bind(hBox.heightProperty());
        deadlineLabel.prefWidthProperty().bind(hBox.widthProperty().multiply(0.3));
        costLabel.prefWidthProperty().bind(hBox.widthProperty().multiply(0.3));
        removeButton.prefWidthProperty().bind(hBox.prefWidthProperty());
        String css = getClass().getResource("resourece_Pane.css").toExternalForm();
        hBox.getStylesheets().add(css);

    }

    AtomicBoolean confirmDelete() throws IOException {
        Dialog<ButtonType> confirmDialog = new Dialog<>();
        confirmDialog.setTitle("Подтверждение удаления заказа");

        // добавление кнопок
        ButtonType okButtonType = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        confirmDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // установка контента диалогового окна
        StackPane content = new StackPane();
        content.getChildren().add(new Text("Удалить заказ?"));
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
    void removeOrder() throws IOException, SQLException {
        if (confirmDelete().get()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("orders-tab.fxml"));
            loader.load();
            OrdersTabController controller = loader.getController();
            Order delOrder = Gingerbread.getOrderByName(this.label.getText());
            delOrder.deleteOrder();
            Pane pane = (Pane) hBox.getParent();
            pane.getChildren().remove(hBox);
        }
    }

    @FXML
    void openOrderWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-window.fxml"));
        Parent orderChangeWindow = loader.load();
        OrderWindowController controller = loader.getController();
        controller.setOrderController(this);
        controller.showOrderWindow(orderChangeWindow, label.getText());
    }


    void saveChanges(String newLabel, String deadline, double cost) {
        label.setText(newLabel);
        deadlineLabel.setText(deadline);
        costLabel.setText(Double.toString(cost));
    }

    @FXML
    void openDeadlineWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deadline-window.fxml"));
        Parent deadlineWindow = loader.load();
        DeadlineWindowController controller = loader.getController();
        controller.setOrderController(this);
        controller.showDeadlineWindow(deadlineWindow, deadlineLabel.getText());
    }

}
