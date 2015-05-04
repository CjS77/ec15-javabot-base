package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.GameState;
import za.co.entelect.challenge.dto.Settings;
import za.co.entelect.challenge.dto.enums.ShipCommand;
import za.co.entelect.challenge.utils.BotHelper;

import java.util.Random;

public class RandomBot implements IRobot {
    private static final String NAME = "RandomBot";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setGameState(GameState state) {}

    @Override
    public ShipCommand calculateMove() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int move = Math.abs(random.nextInt() % ShipCommand.values().length);
        return ShipCommand.values()[move];
    }

    @Override
    public void log(String message) {

    }
}
