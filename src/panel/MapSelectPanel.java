package panel;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.SceneChanger;

public class MapSelectPanel extends HBox {
    public MapSelectPanel() {

        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        this.setAlignment(Pos.TOP_RIGHT);
        this.setSpacing(60);
        this.setPadding(new Insets(0, 50, 0, 0));

        // FirstMapButton
        ImageView map1Btn = baseImageView("1",1);
        zoomUpgrade(map1Btn);

        ///////
        ImageView map2Btn = baseImageView("2",2);
        zoomUpgrade(map2Btn);

        ///////
        ImageView map3Btn = baseImageView("3",3);
        zoomUpgrade(map3Btn);
        this.getChildren().addAll(map1Btn, map2Btn, map3Btn, SceneChanger.getExitButton());
    }

        public ImageView baseImageView(String num,int mapcode){
            ImageView mapBtn = new ImageView(new Image(ClassLoader.getSystemResource("mapselect/Map"+num+".png").toString()));
            mapBtn.setFitHeight(200);
            mapBtn.setFitWidth(200);
            mapBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    new AudioClip(ClassLoader.getSystemResource("audio/buttonclicked.mp3").toString()).play();
                    SceneChanger.gotoGamePanel(mapcode);
                }
            });

            return mapBtn;

        }
        public void zoomUpgrade(ImageView imageView){
            ScaleTransition scaleInImageView = new ScaleTransition(Duration.millis(100), imageView);
            scaleInImageView.setToX(1.1);
            scaleInImageView.setToY(1.1);
            ScaleTransition scaleOutImageView = new ScaleTransition(Duration.millis(200), imageView);
            scaleOutImageView.setToX(1);
            scaleOutImageView.setToY(1);
            imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    scaleInImageView.play();
                }
            });
            imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    scaleOutImageView.play();
                }
            });
    }
}
