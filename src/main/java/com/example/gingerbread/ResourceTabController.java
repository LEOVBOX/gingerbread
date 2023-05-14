package com.example.gingerbread;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ResourceTabController {

    @FXML
    private Label resourseLabel;

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
    public Pane resoursesVbox;

    @FXML
    private ScrollPane scrollPane;

    private Scene scene;




    public ArrayList<Resource> resources;

    @FXML
    void initialize() throws IOException {
        scene = new Scene(mainPane, 500, 500);
        SplitPane.setResizableWithParent(rightPane, true);
        rightPane.prefWidthProperty().bind(mainPane.widthProperty());
        rightPane.prefHeightProperty().bind(mainPane.heightProperty());
        SplitPane.setResizableWithParent(leftPane, true);


        rightVertPane.prefHeightProperty().bind(rightPane.heightProperty());
        rightVertPane.prefWidthProperty().bind(rightPane.widthProperty());

        scrollPane.prefWidthProperty().bind(rightPane.widthProperty());
        scrollPane.prefHeightProperty().bind(rightPane.heightProperty());

        resoursesVbox.prefWidthProperty().bind(scrollPane.widthProperty());
        resoursesVbox.prefHeightProperty().bind(scrollPane.heightProperty());


        labelBar.prefWidthProperty().bind(rightPane.widthProperty());

        sideBar.prefWidthProperty().bind(leftPane.widthProperty());
        sideBar.prefHeightProperty().bind(leftPane.heightProperty());

        DoubleBinding resourseLabelBind = labelBar.widthProperty().multiply(0.8);
        resourseLabel.prefWidthProperty().bind(resourseLabelBind);

        DoubleBinding addButtonBind = labelBar.widthProperty().multiply(0.2);
        addButton.prefWidthProperty().bind(addButtonBind);

        mainPane.prefWidthProperty().bind(scene.widthProperty());

        resources = Gingerbread.loadResourses();
        System.out.println("Init resource tab");
        for (Resource res: resources) {
            addResourse(res);
            res.print();
        }



    }

    public void addResourse(Resource resource) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResoursePane.fxml"));
        HBox hBox = loader.load();
        hBox.prefWidthProperty().bind(resoursesVbox.widthProperty());
        hBox.prefHeightProperty().bind(resoursesVbox.heightProperty().multiply(0.1));
        Label label = (Label) hBox.lookup("#label");
        label.setText(resource.name);
        resoursesVbox.getChildren().add(hBox);
    }

    void showResouceWindow(Parent resChangeWindow) throws IOException{
        Scene resourceScene = resChangeWindow.getScene();
        Stage stage = new Stage();
        String css = getClass().getResource("resource_window.css").toExternalForm();
        resourceScene.getStylesheets().add(css);
        stage.setScene(resourceScene);
        stage.setTitle("Resource");
        stage.setResizable(false);
        stage.show();

    }
    @FXML
    void addNewResource() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resource-window.fxml"));
        Resource newRes = new Resource();
        Parent resChangeWindow = loader.load();
        ResourceWindowController controller = loader.getController();
        controller.setMainController(this);
        showResouceWindow(resChangeWindow);
    }
}