package logic;

import entity.Player;
import entity.PowerUp;

import java.util.Map;
import java.util.Objects;

public class PlayerStateModifier {
    private static final long SPEED_BOOST_DURATION = 8_000_000_000L;
    private static final long DAMAGE_BOOST_DURATION = 10_000_000_000L;
    private static final long INVINCIBILITY_DURATION = 5_000_000_000L;

    public static void modifyState(PowerUp powerUp, Player player) {
        if (Objects.equals(powerUp.getType(), "HEAL") && SceneChanger.getGamePanel().getGameRunning()) {
            player.setHp(player.getHp() + 50);
        } else if (Objects.equals(powerUp.getType(), "SPEED_BOOST")) {
            player.setSpeed(player.getDEFAULT_SPEED() + 1);
            Map<String, Long> currentState = player.getState();
            currentState.put("SPEED_BOOST", System.nanoTime() + SPEED_BOOST_DURATION);
            player.setState(currentState);
        } else if (Objects.equals(powerUp.getType(), "DAMAGE_BOOST")) {
            Map<String, Long> currentState = player.getState();
            currentState.put("DAMAGE_BOOST", System.nanoTime() + DAMAGE_BOOST_DURATION);
            player.setState(currentState);
        } else {
            Map<String, Long> currentState = player.getState();
            currentState.put("INVINCIBLE", System.nanoTime() + INVINCIBILITY_DURATION);
            player.setState(currentState);
        }
    }
}
