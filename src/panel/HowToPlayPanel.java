package panel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import logic.SceneChanger;

public class HowToPlayPanel extends HBox {

    public HowToPlayPanel() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(-50,10,0,0));
        ImageView imageView = new ImageView(new Image(ClassLoader.getSystemResource("object/how_to_play.png").toString()));
        this.getChildren().addAll(imageView, SceneChanger.getExitButton());
    }
}
