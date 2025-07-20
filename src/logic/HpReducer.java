package logic;

import entity.Bullet;
import entity.Player;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class HpReducer {
    public static void reducePlayerHp(Player player, Bullet bullet) {
        if (player.getID() != bullet.getOWNER_ID()) {
            if (!player.getState().containsKey("INVINCIBLE") && SceneChanger.getGamePanel().getGameRunning()){
                if ((System.nanoTime() - player.getLastHitTime()) >= player.getREDUCE_HP_COOLDOWN()) {
                    if (Objects.equals(bullet.getState(), "NORMAL")) {
                        player.setHp(player.getHp() - bullet.getNORMAL_BULLET_DAMAGE());
                        player.setLastHitTime(System.nanoTime());
                    } else if (Objects.equals(bullet.getState(), "ENHANCED")) {
                        player.setHp(player.getHp() - bullet.getENHANCED_BULLET_DAMAGE());
                    }
                    if (SceneChanger.getGamePanel().player1.getHp() == 0) {
                        SceneChanger.getGamePanel().setGameRunning(false);
                        SceneChanger.getGamePanel().setWinner(2);
                    } else if (SceneChanger.getGamePanel().player2.getHp() == 0) {
                        SceneChanger.getGamePanel().setGameRunning(false);
                        SceneChanger.getGamePanel().setWinner(1);
                    }
                }
            }
        }
    }
}
