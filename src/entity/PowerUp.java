package entity;

import interfaces.Drawable;
import interfaces.Updatable;
import panel.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.PowerUpSpawner;

import java.util.Objects;

public class PowerUp extends Entity implements Updatable, Drawable {
    private boolean isCollidedWithP2;
    private boolean isCollidedWithP1;
    private Image image;
    private String type;
    private boolean isWorking;
    private static final long DURATION = 10_000_000_000L;
    private final long CREATION_TIME;

    public PowerUp(GamePanel gamePanel, int x, int y, String type) {
        PowerUpSpawner powerUpSpawner = new PowerUpSpawner(gamePanel);
        this.collisionField = new Rectangle(3, 3, 26, 26); // tweaked
        this.setX(x);
        this.setY(y);
        this.CREATION_TIME = System.nanoTime();
        this.isWorking = true;
        this.type = type;
        this.isCollidedWithP1 = false;
        this.isCollidedWithP2 = false;

        getPowerUpImage();
    }

    public void getPowerUpImage() { // depending on type
        if (Objects.equals(type, "HEAL")) {
            image = new Image(ClassLoader.getSystemResource("object/HEAL.png").toString());
        } else if (Objects.equals(type, "SPEED_BOOST")) {
            image = new Image(ClassLoader.getSystemResource("object/SPEED_BOOST.png").toString());
        } else if (Objects.equals(type, "DAMAGE_BOOST")) {
            image = new Image(ClassLoader.getSystemResource("object/DAMAGE_BOOST.png").toString());
        } else {
            image = new Image(ClassLoader.getSystemResource("object/INVINCIBLE.png").toString());
        }
    }

    public void update() {
        if (System.nanoTime() - CREATION_TIME > DURATION) {
            isWorking = false;
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.drawImage(image, getX(), getY());
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isCollidedWithP2() {
        return isCollidedWithP2;
    }

    public void setCollidedWithP2(boolean collidedWithP2) {
        isCollidedWithP2 = collidedWithP2;
    }

    public boolean isCollidedWithP1() {
        return isCollidedWithP1;
    }

    public void setCollidedWithP1(boolean collidedWithP1) {
        isCollidedWithP1 = collidedWithP1;
    }
}
