package tile;

import interfaces.Drawable;
import panel.GamePanel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager implements Drawable {
    private final GamePanel GAMEPANEL;
    private Tile[] tiles;
    private int[][] mapTileCode;
    private static String mapCode;

    public TileManager(GamePanel GAMEPANEL) {
        this.GAMEPANEL = GAMEPANEL;
        tiles = new Tile[100];
        mapTileCode = new int[GAMEPANEL.MAX_SCREEN_COLUMN][GAMEPANEL.MAX_SCREEN_ROW];
        getTileImage();
        loadMap();

    }

    public static void setMapCode(int mapInput) {
        if (mapInput == 1) {
            mapCode = "/maps/map1.txt";
        } else if (mapInput == 2) {
            mapCode = "/maps/map2.txt";
        } else if (mapInput == 3) {
            mapCode = "/maps/map3.txt";
        } else {
            mapCode = "/maps/map1.txt";
        }

    }

    public void getTileImage() {
        tiles[0] = new Tile();
        tiles[0].image = new Image(ClassLoader.getSystemResource("texture/grass.png").toString());

        tiles[1] = new Tile();
        tiles[1].image = new Image(ClassLoader.getSystemResource("texture/brick.png").toString());
        tiles[1].setHasCollisionField(true);

        tiles[2] = new Tile();
        tiles[2].image = new Image(ClassLoader.getSystemResource("texture/water.png").toString());
        tiles[2].setHasCollisionField(true);

    }

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(mapCode);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;
            while (col < GAMEPANEL.MAX_SCREEN_COLUMN && row < GAMEPANEL.MAX_SCREEN_ROW) {
                String line = bufferedReader.readLine();
                while (col < GAMEPANEL.MAX_SCREEN_COLUMN) {
                    String[] characters = line.split(" ");
                    int numbers = Integer.parseInt(characters[col]);
                    mapTileCode[col][row] = numbers;
                    col++;
                }
                if (col == GAMEPANEL.MAX_SCREEN_COLUMN) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < GAMEPANEL.MAX_SCREEN_COLUMN && row < GAMEPANEL.MAX_SCREEN_ROW) {
            int code = mapTileCode[col][row];
            graphicsContext.drawImage(tiles[code].image, x, y, GAMEPANEL.TILE_SIZE,
                    GAMEPANEL.TILE_SIZE);
            col++;
            x += GAMEPANEL.TILE_SIZE;
            if (col == GAMEPANEL.MAX_SCREEN_COLUMN) {
                col = 0;
                x = 0;
                row++;
                y += GAMEPANEL.TILE_SIZE;
            }
        }
    }

    public int[][] getMapTileCode() {
        return mapTileCode;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
