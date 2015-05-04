package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.GameState;
import za.co.entelect.challenge.dto.enums.ShipCommand;

/**
 * Created by cayle on 5/4/15.
 */
public interface IRobot {
    String getName();
    void setGameState(GameState state);
    ShipCommand calculateMove();
    void log(String message);
}
