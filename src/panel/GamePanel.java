package panel;

import entity.Player;
import entity.PowerUp;
import interfaces.Drawable;
import interfaces.Updatable;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import logic.*;
import tile.TileManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GamePanel extends Pane {

    // screen settings
    public final int ORIGINAL_TILE_SIZE = 32;
    private final int SCALE = 2;
    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public final int MAX_SCREEN_COLUMN = 16;// 1024
    public final int MAX_SCREEN_ROW = 12;// 768
    public final int SCREEN_WIDTH = MAX_SCREEN_COLUMN * TILE_SIZE;
    public final int SCREEN_HEIGHT = MAX_SCREEN_ROW * TILE_SIZE;
    private final int FPS = 120;
    private final long SPAWNING_COOLDOWN = 5_000_000_000L;
    private long lastSpawnTime = 0;
    private int spawnCounter = 0;
    private boolean isGameRunning = true;
    private int winner = 0;

    // components
    private KeyHandler keyHandler1, keyHandler2;
    public Player player1, player2;
    public TileManager tileManager;
    public CollisionChecker collisionChecker;
    public ExitBtn exitBtn;
    public  ArrayList<PowerUp> powerUps = new ArrayList<>();
    public PowerUpSpawner powerUpSpawner;
    private ClickHandler clickHandler;
    private WinnerPanel winnerPanel;
    private List<Updatable> updatableEntities;
    private List<Drawable> drawableEntities;

    public GamePanel() {

        this.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK,
                null, null)));
        this.setFocusTraversable(true);

        initializedAllFields();

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdateTime = 0;

            @Override
            public void handle(long now) {
                long elapsedTime = now - lastUpdateTime;
                if (elapsedTime >= 1_000_000_000L / FPS) {
                    update();
                    redraw();
                    lastUpdateTime = now;
                }
            }
        };
        gameLoop.start();
    }

    public void update() {
        if ((System.nanoTime() - lastSpawnTime) >= SPAWNING_COOLDOWN) {
            if (spawnCounter < 2) {
                spawnCounter++;
            } else {
                powerUpSpawner.spawn();
            }
            lastSpawnTime = System.nanoTime();
        }

        for (Updatable updatable : updatableEntities) {
            updatable.update();
        }


            Iterator<PowerUp> iterator = powerUps.iterator();
            while (iterator.hasNext()) {
                PowerUp powerUp = iterator.next();
                powerUp.update();
                collisionChecker.checkPowerUp(powerUp, player1);
                collisionChecker.checkPowerUp(powerUp, player2);

                if (powerUp.isCollided()) {
                    if (powerUp.isCollidedWithP1()) {
                        PlayerStateModifier.modifyState(powerUp, player1);
                    } else if (powerUp.isCollidedWithP2()) {
                        PlayerStateModifier.modifyState(powerUp, player2);
                    }
                    powerUp.setWorking(false);
                }
                if (!powerUp.isWorking()) {
                    iterator.remove();
                }

        }
    }

    public void redraw() {

        Platform.runLater(() -> {
            Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setImageSmoothing(true);
            graphicsContext.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            for (Drawable drawable : drawableEntities) {
                drawable.draw(graphicsContext);
            }

            getChildren().setAll(canvas);
            for (PowerUp powerUp : powerUps) {
                powerUp.draw(graphicsContext);
            }
            if (!isGameRunning) {
                winnerPanel.draw(graphicsContext, winner);
            }
        });

    }
    public void initializedPlayer() {
        keyHandler1 = new KeyHandler();
        keyHandler2 = new KeyHandler();
        clickHandler = new ClickHandler();
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyHandler1.keyPressed(keyEvent.getCode(), player1.getID());
                keyHandler2.keyPressed(keyEvent.getCode(), player2.getID());
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                keyHandler1.keyReleased(keyEvent.getCode(), player1.getID());
                keyHandler2.keyReleased(keyEvent.getCode(), player2.getID());
            }
        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                clickHandler.clickClicked(mouseEvent.getX(), mouseEvent.getY());
            }
        });
        player1 = new Player(this, keyHandler1, 1);
        player2 = new Player(this, keyHandler2, 2);
    }

    public void initializedAllFields() {
        initializedPlayer();
        tileManager = new TileManager(this);
        collisionChecker = new CollisionChecker(this);
        powerUpSpawner = new PowerUpSpawner(this);
        exitBtn = new ExitBtn();
        winnerPanel = new WinnerPanel();

        updatableEntities = new ArrayList<>();
        updatableEntities.add(player1);
        updatableEntities.add(player2);

        drawableEntities = new ArrayList<>();
        drawableEntities.add(tileManager);
        drawableEntities.add(player1);
        drawableEntities.add(player2);
        drawableEntities.add(exitBtn);
    }
    public void setGameRunning(boolean gameRunning) {
        this.isGameRunning = gameRunning;
    }

    public boolean getGameRunning() {
        return isGameRunning;
    }

    public void setWinner(int id) {
        this.winner = id;
    }


}