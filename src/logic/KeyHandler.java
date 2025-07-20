package logic;

import javafx.scene.input.KeyCode;

public class KeyHandler {

    private boolean isUpPressed, isLeftPressed, isDownPressed, isRightPressed, isFired;

    public void keyPressed(KeyCode e, int playerId) {
        if (playerId == 1) {
            if (e == KeyCode.W) {
                isUpPressed = true;
            }
            if (e == KeyCode.A) {
                isLeftPressed = true;
            }
            if (e == KeyCode.S) {
                isDownPressed = true;
            }
            if (e == KeyCode.D) {
                isRightPressed = true;
            }

            if (e == KeyCode.SPACE) {
                isFired = true;
            }

        } else {
            if (e == KeyCode.UP) {
                isUpPressed = true;
            }
            if (e == KeyCode.LEFT) {
                isLeftPressed = true;
            }
            if (e == KeyCode.DOWN) {
                isDownPressed = true;
            }
            if (e == KeyCode.RIGHT) {
                isRightPressed = true;
            }

            if (e == KeyCode.L) {
                isFired = true;
            }
        }
    }

    public void keyReleased(KeyCode e, int playerID) {
        if (playerID == 1) {
            if (e == KeyCode.W) {
                isUpPressed = false;
            }
            if (e == KeyCode.A) {
                isLeftPressed = false;
            }
            if (e == KeyCode.S) {
                isDownPressed = false;
            }
            if (e == KeyCode.D) {
                isRightPressed = false;
            }

            if (e == KeyCode.SPACE) {
                isFired = false;
            }

        } else {
            if (e == KeyCode.UP) {
                isUpPressed = false;
            }
            if (e == KeyCode.LEFT) {
                isLeftPressed = false;
            }
            if (e == KeyCode.DOWN) {
                isDownPressed = false;
            }
            if (e == KeyCode.RIGHT) {
                isRightPressed = false;
            }

            if (e == KeyCode.L) {
                isFired = false;
            }
        }
    }

    public boolean isUpPressed() {
        return isUpPressed;
    }

    public boolean isLeftPressed() {
        return isLeftPressed;
    }

    public boolean isDownPressed() {
        return isDownPressed;
    }

    public boolean isRightPressed() {
        return isRightPressed;
    }

    public boolean isFired() {
        return isFired;
    }
}
