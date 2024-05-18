package model.gameModels.enemies;

import model.gameModels.GameField;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Mig extends Enemy {
    public Mig(GameField gameField, GameDifficulty difficulty, double x, boolean rotate, double y3) {
        super(gameField, TargetNumbers.MIG_WIDTH.getNumber(difficulty), TargetNumbers.MIG_HEIGHT.getNumber(difficulty), difficulty);
        super.setX(x);
        super.setY(gameField.getHeight() - y3 - TargetNumbers.MIG_HEIGHT.getNumber(difficulty) + 10);
        super.setRotate(angle);

        migSetFill();

        this.angle = 0;
        this.maxHP = TargetNumbers.MIG_HP.getNumber(difficulty);
        this.damage = TargetNumbers.MIG_DAMAGE.getNumber(difficulty);
        this.speed = TargetNumbers.MIG_SPEED.getNumber(difficulty);


        if (rotate) {
            super.setScaleX(-1);
            this.angle = 180 - angle;
            speed = -speed;
        }

        gameField.getChildren().add(this);
        this.visibleProperty().setValue(true);
    }

    private void migSetFill() {
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/mig.png")));
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }
}
