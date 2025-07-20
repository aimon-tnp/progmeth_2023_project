package logic;

public class ClickHandler {


    public void clickClicked(double x, double y) {
        if (!SceneChanger.getGamePanel().getGameRunning()) {
            SceneChanger.gotoMainMenu();
        }
        if (37 >= x && x >= 5 && 37 >= y && y >= 5) {
            SceneChanger.gotoMainMenu();
        }
    }}



