package model.gameModels.enemies;

import model.gameModels.GameField;
import model.enums.GameDifficulty;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {
    public GameDifficulty difficulty;
    public double WIDTH = 100;
    public double HEIGHT = 100;
    public double speed = 1;
    public double maxHP = 100;
    public double HP = 100;
    public double damage = 10;
    public double angle;

    public final GameField gameField;

    public Enemy(GameField gameField, double width, double height) {
        super(width, height);
        this.gameField = gameField;
    }

    public Enemy(GameField gameField) {
        super(100, 100);
        this.gameField = gameField;
    }

    public Enemy(GameField gameField, double width, double height, GameDifficulty difficulty) {
        super(width, height);
        this.gameField = gameField;
        this.difficulty = difficulty;
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }
}
