package com.example.gingerbread;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Application extends javafx.application.Application {

    Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        showResourcesTab();
    }

    public void showResourcesTab() throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("resources-tab.fxml"));
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

    public void showTab(String fxmlPath, String cssPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        TabController controller = fxmlLoader.getController();
        controller.setApplication(this);
        Scene scene = root.getScene();
        String css = getClass().getResource(cssPath).toExternalForm();
        root.getStylesheets().add(css);
        mainStage.setTitle("Gingerbread");
        mainStage.setScene(scene);
        mainStage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}