package logic;

import javafx.scene.media.AudioClip;
import panel.GamePanel;
import entity.Bullet;
import entity.Entity;
import entity.Player;
import entity.PowerUp;

public class CollisionChecker {
    private final GamePanel GAMEPANEL;
    int tileNum1, tileNum2;
    public CollisionChecker(GamePanel GAMEPANEL) {
        this.GAMEPANEL = GAMEPANEL;
    }
    public int[] coordinate(Entity entity){
        int entityLeftX = (int) (entity.getX() + entity.getCollisionField().getX());
        int entityRightX = (int) (entity.getX() + entity.getCollisionField().getX() +
                entity.getCollisionField().getWidth());
        int entityTopY = (int) (entity.getY() + entity.getCollisionField().getY());
        int entityBottomY = (int) (entity.getY() + entity.getCollisionField().getY() +
                entity.getCollisionField().getHeight());
        return new int[]{entityLeftX, entityRightX, entityTopY, entityBottomY};
    }
    public void checkTile(Entity entity) {
        int[] position = coordinate(entity);

        int entityLeftCol = position[0] / GAMEPANEL.TILE_SIZE;
        int entityRightCol = position[1] / GAMEPANEL.TILE_SIZE;
        int entityTopRow = position[2] / GAMEPANEL.TILE_SIZE;
        int entityBottomRow = position[3] / GAMEPANEL.TILE_SIZE;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (position[2] - entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                tileNum1 = GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityTopRow];
                tileNum2 = GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityTopRow];
                if (GAMEPANEL.tileManager.getTiles()[tileNum1].HasCollisionField() ||
                        GAMEPANEL.tileManager.getTiles()[tileNum2].HasCollisionField()) {
                    ((Player) entity).setCollidedWithWall(true);
                }
                break;

            case "left":
                entityLeftCol = (position[0] - entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                tileNum1 = GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityTopRow];
                tileNum2 = GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityBottomRow];
                if (GAMEPANEL.tileManager.getTiles()[tileNum1].HasCollisionField() ||
                        GAMEPANEL.tileManager.getTiles()[tileNum2].HasCollisionField()) {
                    ((Player) entity).setCollidedWithWall(true);
                }
                break;

            case "down":
                entityBottomRow = (position[3] + entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                tileNum1 = GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityBottomRow];
                tileNum2 = GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityBottomRow];
                if (GAMEPANEL.tileManager.getTiles()[tileNum1].HasCollisionField() ||
                        GAMEPANEL.tileManager.getTiles()[tileNum2].HasCollisionField()) {
                    ((Player) entity).setCollidedWithWall(true);
                }
                break;

            case "right":
                entityRightCol = (position[1] + entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                tileNum1 = GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityTopRow];
                tileNum2 = GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityBottomRow];
                if (GAMEPANEL.tileManager.getTiles()[tileNum1].HasCollisionField() ||
                        GAMEPANEL.tileManager.getTiles()[tileNum2].HasCollisionField()) {
                    ((Player) entity).setCollidedWithWall(true);
                }
                break;
        }
    }

    public void checkPlayer(Entity e1, Entity e2) {
        int[] positionE1 = coordinate(e1);
        int[] positionE2 = coordinate(e2);

        switch (e1.getDirection()) {
            case "up":
                positionE1[2] -= e1.getSpeed();
                if (positionE1[2] <= positionE2[3] && !(positionE1[3] < positionE2[2])) {
                        e1.setCollided((positionE2[0] <= positionE1[1] && positionE1[1] <= positionE2[1]) || (positionE2[0] <= positionE1[0] && positionE1[0] <= positionE2[1]));
                }
                break;
            case "left":
                positionE1[0] -= e1.getSpeed();
                if (positionE1[0] <= positionE2[1] && !(positionE1[1] < positionE2[0])) {
                        e1.setCollided((positionE2[2] <= positionE1[2] && positionE1[2] <= positionE2[3]) || (positionE2[2] <= positionE1[3] && positionE1[3] <= positionE2[3]));
                }
                break;
            case "down":
                positionE1[3] += e1.getSpeed();
                if (positionE1[3] >= positionE2[2] && !(positionE1[2] > positionE2[3])) {
                        e1.setCollided((positionE2[0] <= positionE1[1] && positionE1[1] <= positionE2[1]) || (positionE2[0] <= positionE1[0] && positionE1[0] <= positionE2[1]));
                }
                break;
            case "right":
                positionE1[1] += e1.getSpeed();
                if (positionE1[1] >= positionE2[0] && !(positionE1[0] > positionE2[1])) {
                        e1.setCollided((positionE2[2] <= positionE1[2] && positionE1[2] <= positionE2[3]) || (positionE2[2] <= positionE1[3] && positionE1[3] <= positionE2[3]));
                }
                break;
        }
    }
    public void checkPowerUp(PowerUp powerUp, Entity entity) {

        int[] positionE1 = coordinate(powerUp);
        int[] positionE2 = coordinate(entity);

        if (positionE1[1] >= positionE2[0] && positionE1[0] <= positionE2[1] &&
                positionE1[3] >= positionE2[2] && positionE1[2] <= positionE2[3]) {
            powerUp.setCollided(true);
            if (((Player) entity).getID() == 1) {
                powerUp.setCollidedWithP1(true);
            } else {
                powerUp.setCollidedWithP2(true);
            }
            new AudioClip(ClassLoader.getSystemResource("audio/drinking-sound-effect.mp3").toString()).play();
        }
    }

    public void checkBulletWithTile(Entity entity){

        int[] position= coordinate(entity);
        int entityLeftCol = position[0] / GAMEPANEL.TILE_SIZE;
        int entityRightCol = position[1] / GAMEPANEL.TILE_SIZE;
        int entityTopRow = position[2] / GAMEPANEL.TILE_SIZE;
        int entityBottomRow = position[3] / GAMEPANEL.TILE_SIZE;

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (position[2] - entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                if (GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityTopRow] == 1 ||
                        GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityTopRow] == 1){
                    ((Bullet) entity).setCollidedWithWall(true);
                }
                break;

            case "left":
                entityLeftCol = (position[0] - entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                if (GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityTopRow] == 1 ||
                        GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityBottomRow] == 1){
                    ((Bullet) entity).setCollidedWithWall(true);
                }
                break;

            case "down":
                entityBottomRow = (position[3] + entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                if (GAMEPANEL.tileManager.getMapTileCode()[entityLeftCol][entityBottomRow] == 1 ||
                        GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityBottomRow] == 1){
                    ((Bullet) entity).setCollidedWithWall(true);
                }
                break;

            case "right":
                entityRightCol = (position[1] + entity.getSpeed()) / GAMEPANEL.TILE_SIZE;
                if (GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityTopRow] == 1 ||
                        GAMEPANEL.tileManager.getMapTileCode()[entityRightCol][entityBottomRow] == 1){
                    ((Bullet) entity).setCollidedWithWall(true);
                }
                break;
        }
    }
}
