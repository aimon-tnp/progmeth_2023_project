package entity;

import interfaces.Drawable;
import interfaces.Updatable;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import panel.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import logic.HpReducer;
import logic.KeyHandler;

import java.util.*;

public class Player extends Entity implements Updatable, Drawable {
    private final GamePanel GAMEPANEL;
    private final KeyHandler KEYHANDLER;
    private final int ID;
    private boolean isCollidedWithWall;
    private Image previousSpriteImage = null;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private final long FIRING_COOLDOWN = 500_000_000L;
    private long lastFiringTime = 0;
    private String lastNonStopDirection = "up";
    private final int MAX_HP = 100;
    private int hp;
    private Map<String, Long> state = new HashMap<>();
    private long lastHitTime = 0;
    private final long REDUCE_HP_COOLDOWN = 250_000_000L;
    private final int DEFAULT_SPEED = 3;
    private MediaPlayer mediaPlayer= new MediaPlayer(new Media(ClassLoader.getSystemResource("audio/hurt.mp3").toString()));
    private Image up1, up2, left1, left2, down1, down2, right1, right2;

    public Player(GamePanel GAMEPANEL, KeyHandler KEYHANDLER, int ID) {
        this.GAMEPANEL = GAMEPANEL;
        this.KEYHANDLER = KEYHANDLER;
        this.ID = ID;
        this.setHp(MAX_HP);
        this.collisionField = new Rectangle(8, 8, 48, 48); // x, y, width, height
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        if (ID == 1) {
            setX(200);
            setY(150);
        } else {
            setX(700);
            setY(150);
        }
        setSpeed(DEFAULT_SPEED);
        setDirection("");
    }

    public void getPlayerImage() {
        setUp1(new Image(ClassLoader.getSystemResource("tank/up1.png").toString()));
        setUp2(new Image(ClassLoader.getSystemResource("tank/up2.png").toString()));
        setLeft1(new Image(ClassLoader.getSystemResource("tank/left1.png").toString()));
        setLeft2(new Image(ClassLoader.getSystemResource("tank/left2.png").toString()));
        setDown1(new Image(ClassLoader.getSystemResource("tank/down1.png").toString()));
        setDown2(new Image(ClassLoader.getSystemResource("tank/down2.png").toString()));
        setRight1(new Image(ClassLoader.getSystemResource("tank/right1.png").toString()));
        setRight2(new Image(ClassLoader.getSystemResource("tank/right2.png").toString()));
    }

    public void update() {
        if (KEYHANDLER.isUpPressed()) {
            setDirection("up");
        } else if (KEYHANDLER.isLeftPressed()) {
            setDirection("left");
        } else if (KEYHANDLER.isDownPressed()) {
            setDirection("down");
        } else if (KEYHANDLER.isRightPressed()) {
            setDirection("right");
        } else {
            if (previousSpriteImage == null) {
                setDirection("");
            } else {
                setDirection("stop");
            }
        }
        if (!Objects.equals(getDirection(), "") && !Objects.equals(getDirection(), "stop")) {
            lastNonStopDirection = getDirection();
        }
        move();
        adjustSprite();
        if (KEYHANDLER.isFired() && shootable()) {
            fire();
        }
        manageBullets();
        manageBuffs();
    }
    public void move() {
        isCollided = false;
        isCollidedWithWall = false;
        GAMEPANEL.collisionChecker.checkTile(this);
        if (getID() == 1) {
            GAMEPANEL.collisionChecker.checkPlayer(this, GAMEPANEL.player2);
        } else {
            GAMEPANEL.collisionChecker.checkPlayer(this, GAMEPANEL.player1);
        }

        // check collided?
        if (!isCollided && !isCollidedWithWall) {
            switch (getDirection()) {
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
        }
    }
    public void adjustSprite() {
        spriteCounter++;
        if (spriteCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    private void manageBuffs() {
        Iterator<Map.Entry<String, Long>> iterator = state.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String stateName = entry.getKey();
            long expirationTime = entry.getValue();
            long currentTime = System.nanoTime();
            if (expirationTime <= currentTime && !Objects.equals(stateName, "NORMAL")) {
                iterator.remove();
                if (Objects.equals(stateName, "SPEED_BOOST")) {
                    setSpeed(DEFAULT_SPEED);
                }
            }
        }
    }

    public void manageBullets() {
        ArrayList<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (getID() == 1) {
                GAMEPANEL.collisionChecker.checkPlayer(bullet, GAMEPANEL.player2);
            } else {
                GAMEPANEL.collisionChecker.checkPlayer(bullet, GAMEPANEL.player1);
            }
            if (bullet.isCollided) {
                bullet.setWorking(false);
                if (getID() == 1) {
                    HpReducer.reducePlayerHp(GAMEPANEL.player2, bullet);
                    mediaPlayer.stop();
                    mediaPlayer.play();

                } else {
                    HpReducer.reducePlayerHp(GAMEPANEL.player1, bullet);
                    mediaPlayer.stop();
                    mediaPlayer.play();
                }
            }
            if (bullet.isCollidedWithWall()) {
                bullet.setWorking(false);
            }
            if (!bullet.isWorking()) {
                toRemove.add(bullet);
            }
        }

        bullets.removeAll(toRemove);

        if (!GAMEPANEL.getGameRunning()) {
            bullets.clear();
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        drawPlayer(graphicsContext);
        for (Bullet bullet : bullets) {
            bullet.draw(graphicsContext);
        }
        drawBarrier(graphicsContext);
        drawHealthBar(graphicsContext);
        drawStateDisplay(graphicsContext);
    }

    private void drawPlayer(GraphicsContext graphicsContext) {
        Image image;
        if (Objects.equals(this.getDirection(), "")) {
            image = getUp1();
        } else {
            image = previousSpriteImage;
        }
        if (Objects.equals(this.getDirection(), "up") && spriteNum == 1){
            image = up1;
        } else if (Objects.equals(this.getDirection(), "up") && spriteNum == 2){
            image = up2;
        } else if (Objects.equals(this.getDirection(), "left") && spriteNum == 1){
            image = left1;
        } else if (Objects.equals(this.getDirection(), "left") && spriteNum == 2){
            image = left2;
        } else if (Objects.equals(this.getDirection(), "down") && spriteNum == 1){
            image = down1;
        } else if (Objects.equals(this.getDirection(), "down") && spriteNum == 2){
            image = down2;
        } else if (Objects.equals(this.getDirection(), "right") && spriteNum == 1){
            image = right1;
        } else if (Objects.equals(this.getDirection(), "right") && spriteNum == 2){
            image = right2;
        }
        previousSpriteImage = image;
        graphicsContext.drawImage(image, getX(), getY(), GAMEPANEL.TILE_SIZE,
                GAMEPANEL.TILE_SIZE);
    }

    private void drawBarrier(GraphicsContext graphicsContext) {
        if (getState().containsKey("INVINCIBLE")) {
            graphicsContext.drawImage(new Image(ClassLoader.getSystemResource("tank/barrier_" + getLastNonStopDirection() + ".png").toString()),
                    getX(), getY(), GAMEPANEL.TILE_SIZE, GAMEPANEL.TILE_SIZE);
        }
    }

    private void drawStateDisplay(GraphicsContext graphicsContext) {
        if (getState() != null) {
            int startingX;
            if (getID() == 1) {
                startingX = GAMEPANEL.TILE_SIZE * 2;
            } else {
                startingX = GAMEPANEL.SCREEN_WIDTH - GAMEPANEL.TILE_SIZE * 2 - GAMEPANEL.TILE_SIZE / 2;
            }
            for (String state : getState().keySet()) {
                graphicsContext.drawImage(
                        new Image(ClassLoader.getSystemResource("object/" + state + ".png").toString()),
                        startingX, GAMEPANEL.SCREEN_HEIGHT - GAMEPANEL.TILE_SIZE / 4 * 3,
                        GAMEPANEL.TILE_SIZE / 2, GAMEPANEL.TILE_SIZE / 2);
                if (getID() == 1) {
                    startingX += 20;
                } else {
                    startingX -= 20;
                }
            }
        }
    }

    private void drawHealthBar(GraphicsContext graphicsContext) {
        int healthBarWidth = 100;
        int healthBarHeight = 10;
        int healthBarY = GAMEPANEL.SCREEN_HEIGHT - GAMEPANEL.TILE_SIZE / 2;
        int healthBarX;
        if (getID() == 1) {
            healthBarX = 10;
        } else {
            healthBarX = GAMEPANEL.SCREEN_WIDTH - GAMEPANEL.TILE_SIZE * 2 + 10;
        }
        graphicsContext.setFill(Color.GREY);
        graphicsContext.fillRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

        double fillWidth = (double) (getHp()) / (double) (getMAX_HP()) * (double) healthBarWidth;
        if (fillWidth > 0) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.fillRect(healthBarX, healthBarY, fillWidth, healthBarHeight);
        }
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText(String.format("HP: %d/%d", getHp(), getMAX_HP()), healthBarX + 5, healthBarY - 5);
    }

    public int getID() {
        return ID;
    }

    private void fire() {
        if (GAMEPANEL.getGameRunning()) {
            bullets.add(new Bullet(GAMEPANEL, this));
            lastFiringTime = System.nanoTime();
           // mediaPlayer.stop();
           //mediaPlayer.play();
        }

    }

    public String getLastNonStopDirection() {
        return lastNonStopDirection;
    }

    private boolean shootable() {
        return (System.nanoTime() - lastFiringTime) >= FIRING_COOLDOWN;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp > MAX_HP) {
            this.hp = MAX_HP;
        } else this.hp = Math.max(hp, 0);
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public long getLastHitTime() {
        return lastHitTime;
    }

    public void setLastHitTime(long lastHitTime) {
        this.lastHitTime = lastHitTime;
    }

    public long getREDUCE_HP_COOLDOWN() {
        return REDUCE_HP_COOLDOWN;
    }

    public Map<String, Long> getState() {
        return state;
    }

    public void setState(Map<String, Long> state) {
        this.state = state;
    }

    public Image getUp1() {
        return up1;
    }

    public void setUp1(Image up1) {
        this.up1 = up1;
    }

    public Image getUp2() {
        return up2;
    }

    public void setUp2(Image up2) {
        this.up2 = up2;
    }

    public Image getLeft1() {
        return left1;
    }

    public void setLeft1(Image left1) {
        this.left1 = left1;
    }

    public Image getLeft2() {
        return left2;
    }

    public void setLeft2(Image left2) {
        this.left2 = left2;
    }

    public Image getDown1() {
        return down1;
    }

    public void setDown1(Image down1) {
        this.down1 = down1;
    }

    public Image getDown2() {
        return down2;
    }

    public void setDown2(Image down2) {
        this.down2 = down2;
    }

    public Image getRight1() {
        return right1;
    }

    public void setRight1(Image right1) {
        this.right1 = right1;
    }

    public Image getRight2() {
        return right2;
    }

    public void setRight2(Image right2) {
        this.right2 = right2;
    }

    public int getDEFAULT_SPEED() {
        return DEFAULT_SPEED;
    }

    public void setCollidedWithWall(boolean collidedWithWall) {
        isCollidedWithWall = collidedWithWall;
    }
}
