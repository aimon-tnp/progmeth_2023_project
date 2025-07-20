package logic;

import panel.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tile.TileManager;


public class SceneChanger {
    private static SceneChanger instance;
    private static Stage primaryStage;
    private static Scene primaryScene;
    private static RootPane rootPane;
    private static GamePanel gamePanel;

    static public void setRootPane(RootPane rootPane) {
        SceneChanger.rootPane = rootPane;
    }

    static public void setPrimaryScene(Scene primaryScene) {
        SceneChanger.primaryScene = primaryScene;
    }

    static public void gotoGamePanel(int mapCode) {
        clear();
        TileManager.setMapCode(mapCode);
        GamePanel gamePanel = new GamePanel();
        SceneChanger.gamePanel = gamePanel;
        primaryStage.setScene(new Scene(gamePanel));
        SceneChanger.gamePanel.requestFocus();
    }

    static public void setPrimaryStage(Stage primaryStage) {
        SceneChanger.primaryStage = primaryStage;
    }

    static public void gotoHowToPlayPanel() {
        clear();
        rootPane.getChildren().add(new HowToPlayPanel());
    }

    static public void gotoMapSelectPanel() {
        clear();
        rootPane.getChildren().addAll(new MapSelectPanel());
    }

    static public void gotoMainMenu() {
        clear();
        primaryStage.setScene(primaryScene);
        rootPane.getChildren().add(new MainMenuPanel());
        gamePanel = null;

    }

    public static void clear() {
        while (rootPane.getChildren().size() > 1) {
            rootPane.getChildren().remove(1);

        }
    }


    static public SceneChanger getInstance() {
        if (instance == null) {
            instance = new SceneChanger();
        }
        return instance;
    }


    public static Button getExitButton() {
        Button exitBtn = new Button("X");
        exitBtn.setPrefSize(32, 32);
        exitBtn.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), null)));

        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gotoMainMenu();
            }
        });
        return exitBtn;

    }

    public static GamePanel getGamePanel() {
        return gamePanel;
    }
}
