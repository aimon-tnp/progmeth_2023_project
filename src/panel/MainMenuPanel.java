package panel;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.SceneChanger;

import java.io.File;


public class MainMenuPanel extends VBox {

    private MediaPlayer mediaPlayer;
    public MainMenuPanel() {
        this.mediaPlayer = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/background_music.mp3").toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.5);

        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        this.setFocusTraversable(true);
        this.setSpacing(70);

        initializedButton();
    }
    public Button baseButton(String text){
        Button btn = new Button(text);
        btn.setBackground(new Background(new BackgroundFill(Color.rgb(190, 190, 190), new CornerRadii(10), null)));
        btn.setPrefHeight(50);
        btn.setPrefWidth(200);
        zoomUpgrade(btn);
        return btn;

    }
    public void initializedButton(){
        AudioClip audioClip=new AudioClip(ClassLoader.getSystemResource("audio/pop.mp3").toString());
        Button startBtn = baseButton("Start Game");
        startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                audioClip.play();
                mediaPlayer.stop();
                SceneChanger.gotoMapSelectPanel();
            }
        });

        ///
        Button howToPlayBtn = baseButton("How to play");
        howToPlayBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                audioClip.play();
                mediaPlayer.stop();
                SceneChanger.gotoHowToPlayPanel();
            }
        });

        ///
        Button quitBtn = baseButton("Quit");
        quitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        this.getChildren().addAll(startBtn, howToPlayBtn, quitBtn);
    }
    public void zoomUpgrade(Button button){
        ScaleTransition scaleInButton = new ScaleTransition(Duration.millis(100), button);
        scaleInButton.setToX(1.1);
        scaleInButton.setToY(1.1);
        ScaleTransition scaleOutButton = new ScaleTransition(Duration.millis(200), button);
        scaleOutButton.setToX(1);
        scaleOutButton.setToY(1);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scaleInButton.play();
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scaleOutButton.play();
            }
        });
}}
