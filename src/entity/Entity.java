package entity;

import javafx.scene.shape.Rectangle;

public class Entity {
    private int x, y;
    private int speed;

    private String direction;

    // for dynamic animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    protected Rectangle collisionField;
    protected boolean isCollided = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Rectangle getCollisionField() {
        return collisionField;
    }

    public void setCollisionField(Rectangle collisionField) {
        this.collisionField = collisionField;
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided(boolean collided) {
        isCollided = collided;
    }
}
