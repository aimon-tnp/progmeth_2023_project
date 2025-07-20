package panel;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class WinnerPanel {
    public void draw(GraphicsContext graphicsContext, int id) {
        graphicsContext.setFill(Color.DARKBLUE);
        Font.loadFont(ClassLoader.getSystemResource("font/PressStart2P.ttf").toString(), 14);
        Font font = Font.font("Press Start 2P", 23);
        graphicsContext.setFont(font);
        if (id == 1) {
            graphicsContext.fillText("Player 1 Wins ", 367, 330);
        } else if (id == 2) {
            graphicsContext.fillText("Player 2 Wins ", 367, 330);
        }
        graphicsContext.fillText("Press anywhere to go to main menu",130,370);
    }
}
