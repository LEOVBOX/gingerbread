package com.example.gingerbread;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

public class OrdersTabController extends TabController{
    private ArrayList<Order> orders;

    @FXML
    void initialize() throws IOException {
        bindElementsScale();
        orders = Gingerbread.loadOrders();
        System.out.println("Init orders tab");
        for (Order order: orders) {
            loadResource(order);
            order.print();
        }
    }

    @FXML
    public void loadResource(Order order) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-pane.fxml"));
        HBox hBox = loader.load();
        OrderController controller = loader.getController();
        controller.setOrdersTabController(this);
        hBox.prefWidthProperty().bind(contentVbox.widthProperty());
        hBox.prefHeightProperty().bind(contentVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        Label deadlineLabel = (Label) hBox.lookup("#deadlineLabel");
        Label costLabel = (Label) hBox.lookup("#costLabel") ;
        costLabel.setText(Double.toString(order.getCost()));
        deadlineLabel.setText(order.getDeadline());
        label.setText(order.getName());
        contentVbox.getChildren().add(hBox);
    }



    @FXML
    void addNewOrder() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-window.fxml"));
        Parent recipeWindow = loader.load();
        OrderWindowController controller = loader.getController();
        controller.setTabController(this);
        controller.showOrderWindow(recipeWindow, null);
    }


}