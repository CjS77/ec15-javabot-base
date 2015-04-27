package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.Settings;
import za.co.entelect.challenge.dto.enums.ShipCommand;
import za.co.entelect.challenge.utils.BotHelper;

import java.util.Random;

public class RandomBot extends BaseBot {

    public RandomBot(Settings settings) {
        super(settings);
    }

    private String getRandomMove() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int move = Math.abs(random.nextInt() % ShipCommand.values().length);
        String translatedMove = BotHelper.translateMove(move);
        return translatedMove;
    }

    @Override
    protected String getMove() {
        return getRandomMove();
    }
}
