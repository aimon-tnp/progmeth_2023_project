package logic;

import panel.GamePanel;
import entity.PowerUp;

import java.util.Random;

public class PowerUpSpawner {
    private final GamePanel GAMEPANEL;
    private final Random RANDOM = new Random();

    public PowerUpSpawner(GamePanel GAMEPANEL) {
        this.GAMEPANEL = GAMEPANEL;
    }

    public void spawn() {
        int[] coordinate = getRandomCoordinate();

        PowerUp powerUp = new PowerUp(GAMEPANEL, coordinate[0],
                coordinate[1], getRandomType());
        GAMEPANEL.powerUps.add(powerUp);
    }

    public int[] getRandomCoordinate() {
        int col, row, tileNum;

        do {
            col = RANDOM.nextInt(GAMEPANEL.MAX_SCREEN_COLUMN);
            row = RANDOM.nextInt(GAMEPANEL.MAX_SCREEN_ROW);
            tileNum = GAMEPANEL.tileManager.getMapTileCode()[col][row];
        } while (tileNum != 0);

        return new int[]{col * GAMEPANEL.TILE_SIZE + (GAMEPANEL.TILE_SIZE / 4),
                row * GAMEPANEL.TILE_SIZE + (GAMEPANEL.TILE_SIZE / 4)};
    }

    public String getRandomType() {
        int value = RANDOM.nextInt(100);
        if (value < 30) {//30%
            return "SPEED_BOOST";
        } else if (value < 60) {//30%
            return "DAMAGE_BOOST";//20%
        } else if (value < 80) {
            return "HEAL";
        } else {//20%
            return "INVINCIBLE";
        }
    }
}