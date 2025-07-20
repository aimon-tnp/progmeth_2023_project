package tile;

import javafx.scene.image.Image;

public class Tile {
    public Image image;
    private boolean hasCollisionField = false;

    public boolean HasCollisionField() {
        return hasCollisionField;
    }

    public void setHasCollisionField(boolean hasCollisionField) {
        this.hasCollisionField = hasCollisionField;
    }
}
