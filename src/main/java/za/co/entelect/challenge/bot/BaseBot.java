package za.co.entelect.challenge.bot;

import za.co.entelect.challenge.dto.GameState;
import za.co.entelect.challenge.dto.Player;
import za.co.entelect.challenge.dto.Settings;
import za.co.entelect.challenge.dto.enums.ShipCommand;
import za.co.entelect.challenge.dto.reader.BasicGameStateReader;
import za.co.entelect.challenge.dto.reader.GameStateReader;
import za.co.entelect.challenge.dto.reader.GsonGameStateReader;
import za.co.entelect.challenge.dto.reader.JacksonGameStateReader;
import za.co.entelect.challenge.utils.BotHelper;
import za.co.entelect.challenge.utils.FileHelper;
import za.co.entelect.challenge.utils.LogHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseBot {

    private Settings settings = null;
    private final IRobot mRobot;

    public BaseBot(Settings settings) {
        this.settings = settings;
        mRobot = new RandomBot();
    }

    public BaseBot(Settings settings, IRobot botInstance) {
        this.settings = settings;
        mRobot = botInstance;
    }

    private GameState gameState;

    public void execute() {

        //Choose how you want to access the JSON
        GameStateReader reader = 
                //new BasicGameStateReader();
                //new JacksonGameStateReader();
                new GsonGameStateReader();

        gameState = loadGameState(reader);
        
        logMatchState();

        StringBuilder map = loadMap();
        LogHelper.logPartsOnDifferentLines("Map", map);

        String move = getMove();
        saveMove(move);
    }

    private GameState loadGameState(GameStateReader reader) {
        GameState gameState = null;

        File jsonFile = FileHelper.getFile(settings.getDefaultOutputFolder(), settings.getStateFile());

        try {
            gameState = reader.read(jsonFile);
        } catch (Exception ioe) {
            LogHelper.log("Error reading state file: " + settings.getStateFile());
            ioe.printStackTrace();
            return null;
        }
        return gameState;
    }

    private void logMatchState() {
        LogHelper.log(LogHelper.PREFIX + "Game state:");
        LogHelper.log("\tRound: " + gameState.getRoundNumber());

        for (Player player : getPlayers()) {
            logPlayerState(player);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Player> getPlayers() {
        return gameState.getPlayers();
    }

    private void logPlayerState(Player player) {
        LogHelper.log("\tPlayer " + player.getPlayerNumberReal() + " Kills: " + player.getKills());
        LogHelper.log("\tPlayer " + player.getPlayerNumberReal() + " Lives: " + player.getLives());
        LogHelper.log("\tPlayer " + player.getPlayerNumberReal() + " Missiles: " + player.getMissiles().size() + "/" + player.getMissileLimit());
    }

    private StringBuilder loadMap() {
        try {
            return FileHelper.readFile(settings.getDefaultOutputFolder(), settings.getMapFile());
        } catch(IOException ioe) {
            LogHelper.log("Unable to read map file: " + settings.getMapFile());
            ioe.printStackTrace();
            return new StringBuilder();
        }
    }

    protected String getMove() {
        mRobot.setGameState(getGameState());
        ShipCommand move = mRobot.calculateMove();
        String msg = mRobot.getMessage();
        if (msg != null && msg.length()>0) LogHelper.log(String.format("[%s]\n%s\n", mRobot.getName(), msg));
        return move.toString();
    }

    private void saveMove(String move) {
        try {
            FileHelper.writeFile(settings.getDefaultOutputFolder(), settings.getOutputFile(), move);
            LogHelper.log("Move", move);
        } catch (IOException ioe) {
            LogHelper.log("Unable to write move file: " + settings.getOutputFile());
            ioe.printStackTrace();
        }
    }
}
