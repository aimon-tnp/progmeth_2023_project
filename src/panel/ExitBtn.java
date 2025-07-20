package panel;

import interfaces.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ExitBtn extends Button implements Drawable {

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.DARKGRAY);
        graphicsContext.fillRoundRect(10, 10, 32, 32, 10, 5);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setFont(new Font(30));
        graphicsContext.fillText("X", 17, 36);
    }
}
