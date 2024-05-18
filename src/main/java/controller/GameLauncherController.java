package controller;

import model.App;
import model.Game;
import model.enums.GameDifficulty;
import model.gameModels.GameField;
import model.saved.SavedGame;
import view.GameLauncher;
import com.google.gson.Gson;
import javafx.scene.layout.Pane;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameLauncherController {
    public static Pane root;

    private GameDifficulty dificulty = GameDifficulty.EASY;
    private boolean musicMuted = false;
    private boolean soundMuted = false;
    private boolean BWColorize = false;
    private boolean usingArrowKeys = false;
    public static boolean continueGame = false;

    public static void saveGame(SavedGame savedGame) {
        Gson gson = new Gson();
        String json = gson.toJson(savedGame);

        try (FileWriter writer = new FileWriter("src/main/DB/LastGame/" + App.getLoggedInUser().getUserName() + ".json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameField loadGameField(SavedGame savedGame) {
        //create a new game field:
        return new GameField(savedGame.savedWave.savedGameField);
    }

    public static Game launchGame(GameLauncher gameLauncher) {

        if (!continueGame) {
            // No saved game exists, create a new game
            // Replace the following line with your game creation code
            Game game = new Game(root);
            return game;
        } else {
            // A saved game exists, load it
            // Replace the following lines with your game loading code
            Game game = new Game(root);
            game.gameLauncher = gameLauncher;
            gameLauncher.game = game;
            SavedGame savedGame = loadGame();
            game.loadFromSavedGame(savedGame);
            return game;
        }
        // Continue with the rest of your game launching code
    }

    public static SavedGame loadGame() {
        Gson gson = new Gson();
        SavedGame savedGame = null;
        try (FileReader reader = new FileReader("src/main/DB/LastGame/" + App.getLoggedInUser().getUserName() + ".json")) {
            savedGame = gson.fromJson(reader, SavedGame.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedGame;
    }

    public void setDificulty(GameDifficulty dificulty) {
        this.dificulty = dificulty;
    }

    public void setMusicMuted(boolean musicMuted) {
        this.musicMuted = musicMuted;
    }

    public void setSoundMuted(boolean soundMuted) {
        this.soundMuted = soundMuted;
    }

    public void setBWColorize(boolean BWColorize) {
        this.BWColorize = BWColorize;
    }

    public void setUsingArrowKeys(boolean usingArrowKeys) {
        this.usingArrowKeys = usingArrowKeys;
    }

    public GameDifficulty getDificulty() {
        return dificulty;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public boolean isSoundMuted() {
        return soundMuted;
    }

    public boolean isBWColorize() {
        return BWColorize;
    }

    public boolean isUsingArrowKeys() {
        return usingArrowKeys;
    }


}
