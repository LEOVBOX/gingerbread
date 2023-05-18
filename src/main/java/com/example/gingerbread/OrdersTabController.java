package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

public class OrdersTabController {

    @FXML
    private Label recipesLabel;

    @FXML
    private Button addButton;

    @FXML
    private SplitPane mainPane;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane labelBar;

    @FXML
    private Pane sideBar;

    @FXML
    private Pane rightVertPane;


    @FXML
    private Pane rightPane;

    @FXML
    public Pane ordersVbox;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label resourcesTab;

    @FXML
    private Label recipesTab;

    @FXML
    private Label ordersTab;

    private Scene scene;

    private Application application;

    private ArrayList<Order> orders;


    @FXML
    void initialize() throws IOException {
        scene = new Scene(mainPane, 700, 700);
        SplitPane.setResizableWithParent(rightPane, true);
        rightPane.prefWidthProperty().bind(mainPane.widthProperty());
        rightPane.prefHeightProperty().bind(mainPane.heightProperty());
        SplitPane.setResizableWithParent(leftPane, true);


        rightVertPane.prefHeightProperty().bind(rightPane.heightProperty());
        rightVertPane.prefWidthProperty().bind(rightPane.widthProperty());

        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());
        scrollPane.prefHeightProperty().bind(rightPane.heightProperty());

        ordersVbox.prefWidthProperty().bind(scrollPane.widthProperty());
        ordersVbox.prefHeightProperty().bind(scrollPane.heightProperty());


        labelBar.prefWidthProperty().bind(rightPane.widthProperty());

        sideBar.prefWidthProperty().bind(leftPane.widthProperty());
        sideBar.prefHeightProperty().bind(leftPane.heightProperty());

        resourcesTab.prefWidthProperty().bind(sideBar.widthProperty());
        recipesTab.prefWidthProperty().bind(sideBar.widthProperty());
        ordersTab.prefWidthProperty().bind(sideBar.widthProperty());

        DoubleBinding resourseLabelBind = labelBar.widthProperty().multiply(0.8);
        recipesLabel.prefWidthProperty().bind(resourseLabelBind);

        DoubleBinding addButtonBind = labelBar.widthProperty().multiply(0.2);
        addButton.prefWidthProperty().bind(addButtonBind);

        mainPane.prefWidthProperty().bind(scene.widthProperty());

        orders = Gingerbread.loadOrders();
        System.out.println("Init recipes tab");
        for (Order order: orders) {
            addOrder(order);
            order.print();
        }

    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @FXML
    void showResourcesTab() throws IOException {
        application.showResourcesTab();
    }

    @FXML
    void showRecipesTab() throws IOException {
        application.showRecepiesTab();
    }


    @FXML
    public void addOrder(Order order) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("order-pane.fxml"));
        HBox hBox = loader.load();
        OrderController controller = loader.getController();
        controller.setOrdersTabController(this);
        hBox.prefWidthProperty().bind(ordersVbox.widthProperty());
        hBox.prefHeightProperty().bind(ordersVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        Label deadlineLabel = (Label) hBox.lookup("#deadlineLabel");
        Label costLabel = (Label) hBox.lookup("#costLabel") ;
        costLabel.setText(Double.toString(order.getCost()));
        deadlineLabel.setText(order.getDeadline());
        label.setText(order.getName());
        ordersVbox.getChildren().add(hBox);
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