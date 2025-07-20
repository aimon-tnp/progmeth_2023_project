package entity;

import interfaces.Drawable;
import interfaces.Updatable;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import panel.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Bullet extends Entity implements Drawable, Updatable {
    private final GamePanel GAMEPANEL;
    private final long DURATION = 3_000_000_000L;
    private final int OWNER_ID;
    private Image image;
    private boolean isWorking;
    private final long CREATION_TIME;
    private final int NORMAL_BULLET_DAMAGE = 10;
    private final int ENHANCED_BULLET_DAMAGE = 15;
    private String state;
    private boolean isCollidedWithWall;
    private MediaPlayer mediaPlayer = new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/pew.mp3").toString()));
    public Bullet(GamePanel GAMEPANEL, Player player) {


        this.GAMEPANEL = GAMEPANEL;
        this.OWNER_ID = player.getID();
        this.collisionField = new Rectangle(9, 9, 14, 14);
        this.setDirection(player.getLastNonStopDirection());
        this.setX(getStartingX(player));
        this.setY(getStartingY(player));
        this.isWorking = true;
        this.isCollidedWithWall = false;
        this.CREATION_TIME = System.nanoTime();

        // set state, speed depending on player state
        this.setState(player);
        this.setSpeed(player);

        getBulletImage();
        playSound();
    }

    public void getBulletImage() {
        String path = "object/" + this.getState().toLowerCase() + "_bullet_" + this.getDirection().toLowerCase() + ".png";
        image = new Image(ClassLoader.getSystemResource(path).toString());
    }
    public void playSound(){
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    public void update() {
        switch (this.getDirection()) {
            case "up":
                setY(getY() - getSpeed());
                break;
            case "left":
                setX(getX() - getSpeed());
                break;
            case "down":
                setY(getY() + getSpeed());
                break;
            case "right":
                setX(getX() + getSpeed());
                break;
        }
        if (System.nanoTime() - CREATION_TIME > DURATION) {
            isWorking = false;
        }
        GAMEPANEL.collisionChecker.checkBulletWithTile(this);
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

    public int getOWNER_ID() {
        return OWNER_ID;
    }

    public void setState(Player player) {
        if (player.getState().containsKey("DAMAGE_BOOST")) {
            this.state = "ENHANCED";
        } else {
            this.state = "NORMAL";
        }
    }

    public String getState() {
        return state;
    }

    public int getNORMAL_BULLET_DAMAGE() {
        return NORMAL_BULLET_DAMAGE;
    }

    public int getENHANCED_BULLET_DAMAGE() {
        return ENHANCED_BULLET_DAMAGE;
    }

    public boolean isCollidedWithWall() {
        return isCollidedWithWall;
    }

    public void setCollidedWithWall(boolean collidedWithWall) {
        isCollidedWithWall = collidedWithWall;
    }

    public void setSpeed(Player player) {
        if (player.getState().containsKey("DAMAGE_BOOST")) {
            this.setSpeed(4);
        } else {
            this.setSpeed(3);
        }
    }

    public int getStartingX(Player player){
        int x;
        if (Objects.equals(this.getDirection(), "left")){
            x = player.getX();
        } else if (Objects.equals(this.getDirection(), "right")) {
            x = player.getX() + GAMEPANEL.ORIGINAL_TILE_SIZE;
        } else {
            x = player.getX() + GAMEPANEL.ORIGINAL_TILE_SIZE / 2;
        }
        return x;
    }
    public int getStartingY(Player player){
        int y;
        if (Objects.equals(this.getDirection(), "up")){
            y = player.getY();
        } else if (Objects.equals(this.getDirection(), "down")) {
            y = player.getY() + GAMEPANEL.ORIGINAL_TILE_SIZE;
        } else {
            y = player.getY() + GAMEPANEL.ORIGINAL_TILE_SIZE / 2;
        }
        return y;
    }
}
