package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.GameState;
import za.co.entelect.challenge.dto.enums.ShipCommand;
import za.co.entelect.challenge.utils.LogHelper;

/**
 * Created by cayle on 5/4/15.
 */
public class CycleBot implements IRobot {
    int counter = 0;
    ShipCommand[] moves = {ShipCommand.Shoot, ShipCommand.MoveLeft, ShipCommand.MoveLeft, ShipCommand.MoveLeft,
                           ShipCommand.Shoot, ShipCommand.MoveRight, ShipCommand.MoveRight, ShipCommand.MoveRight};
    @Override
    public void setGameState(GameState state) {}

    @Override
    public ShipCommand calculateMove() {
        return moves[counter++ % moves.length]
    }

    @Override
    public void log(String message) {
        LogHelper
    }
}
