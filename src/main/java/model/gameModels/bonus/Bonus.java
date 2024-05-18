package model.gameModels.bonus;

import model.Game;
import model.enums.TargetNumbers;
import javafx.scene.shape.Rectangle;

public class Bonus extends Rectangle {
    public double xSpeed;
    public double ySpeed;
    public Game game;

    public Bonus (Game game, double x, double y) {
        super(x, y, TargetNumbers.BONUS_WIDTH.getNumber(game.player.getDifficulty()), TargetNumbers.BONUS_HEIGHT.getNumber(game.player.getDifficulty()));
        double speed = TargetNumbers.BONUS_SPEED.getNumber(game.player.getDifficulty());
        this.xSpeed = 0;
        this.ySpeed = speed;
        this.game = game;
    }

    public void addBonus() {

    }
}
