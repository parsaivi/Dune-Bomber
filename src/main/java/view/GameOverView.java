package view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class GameOverView {
    public Label lastWave;
    public int lastWaveInt;
    public Label score;
    public int scoreInt;
    public Label accuracy;
    public int accuracyInt;
    public Label WOrL;
    public Button backButton;
    private Parent gameOverRoot;
    private GameLauncher gameLauncher;
    public void setGameOverRoot(Parent gameOverRoot) {
        this.gameOverRoot = gameOverRoot;
    }

    public void setGameLauncher(GameLauncher controller) {
        this.gameLauncher = controller;
    }

    public void visibleGameOverRoot() {
        gameOverRoot.setVisible(true);
    }

    public void setWin() {
        WOrL.setText("Win!");
        lastWave.setText(String.valueOf(lastWaveInt));
        score.setText(String.valueOf(scoreInt));
        accuracy.setText("%" + accuracyInt);
    }

    public void setData(int wave, int kills, int accuracy) {
        lastWaveInt = wave;
        scoreInt = kills;
        accuracyInt = accuracy;
    }

    public void setLose() {
        WOrL.setText("Lose!");
        lastWave.setText(String.valueOf(lastWaveInt));
        score.setText(String.valueOf(scoreInt));
        accuracy.setText("%" + accuracyInt);
    }

    public void backClicked(MouseEvent event) {
        gameLauncher.backToMainMenu();
    }
}
