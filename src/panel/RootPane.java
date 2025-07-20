package panel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RootPane extends VBox {
    private static RootPane instance;
    private final int ORIGINAL_TILE_SIZE = 32;
    private final int SCALE = 2;

    private final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private final int MAX_SCREEN_COLUMN = 16;
    private final int MAX_SCREEN_ROW = 12;
    private final int SCREEN_WIDTH = MAX_SCREEN_COLUMN * TILE_SIZE;
    private final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE;

    public RootPane() {
        this.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(new Background(new BackgroundImage(new Image(ClassLoader.getSystemResource("mapselect/Background.png").toString()),null,null,null,null)));
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(90);
        this.setPadding(new Insets(110, 0, 32, 0));
        //
        Text t = new Text("Tanky Shooter");
        t.setFill(Color.LIGHTGRAY);
        Font font = Font.font("Verdana", FontWeight.BOLD, 32);
        t.setFont(font);
        //
        this.getChildren().add(t);
    }


    public static RootPane getRootPane() {
        if (instance == null)
            instance = new RootPane();
        return instance;
    }
}
