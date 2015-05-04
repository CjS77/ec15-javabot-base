package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.GameState;
import za.co.entelect.challenge.dto.enums.ShipCommand;
import za.co.entelect.challenge.utils.LogHelper;

/**
 * Bot just cycles through set sequence
 */
public class CycleBot implements IRobot {
    private static final String NAME = "CycleBot";
    private GameState state = null;
    ShipCommand[] moves = {ShipCommand.Shoot, ShipCommand.MoveLeft, ShipCommand.MoveLeft, ShipCommand.MoveLeft,
                           ShipCommand.Shoot, ShipCommand.MoveRight, ShipCommand.MoveRight, ShipCommand.MoveRight};

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setGameState(GameState state) {
        this.state = state;
    }

    @Override
    public ShipCommand calculateMove() {
        if (this.state == null) {
            log("Received null state");
        }
        int round = state.getRoundNumber();
        return moves[round % moves.length];
    }

    @Override
    public void log(String message) {
        LogHelper.log("[Cyclebot]", message);
    }
}
