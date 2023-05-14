package com.example.gingerbread;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        showResourcesTab();
    }

    public void showResourcesTab() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("resources-tab.fxml"));
        Parent root = loader.load();
        ResourceTabController controller = loader.getController();
        controller.setApplication(this);
        Scene scene = root.getScene();
        String css = getClass().getResource("resources-stage.css").toExternalForm();
        root.getStylesheets().add(css);
        mainStage.setTitle("Gingerbread");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public void showRecepiesTab() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("recipes-tab.fxml"));
        Parent root = fxmlLoader.load();
        RecipesTabController controller = fxmlLoader.getController();
        controller.setApplication(this);
        Scene scene = root.getScene();
        String css = getClass().getResource("resources-stage.css").toExternalForm();
        root.getStylesheets().add(css);
        mainStage.setTitle("Gingerbread");
        mainStage.setScene(scene);
        mainStage.show();
    }



    public static void main(String[] args) throws SQLException, IOException {
        launch();
    }
}