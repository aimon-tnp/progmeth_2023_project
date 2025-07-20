package application;

import panel.RootPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.SceneChanger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Tanky Shooter");

        Scene thisScene = new Scene(RootPane.getRootPane());
        primaryStage.setScene(thisScene);

        SceneChanger.setRootPane(RootPane.getRootPane());
        SceneChanger.setPrimaryStage(primaryStage);
        SceneChanger.setPrimaryScene(thisScene);
        SceneChanger.gotoMainMenu();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
