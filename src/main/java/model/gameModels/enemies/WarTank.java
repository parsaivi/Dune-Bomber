package model.gameModels.enemies;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class WarTank extends Target {
    public CircleAround circleAround;

    public WarTank(GameField gameField, GameDifficulty difficulty, double x, boolean rotate, double angle, double y3) {
        super(gameField, TargetNumbers.WARTANK_WIDTH.getNumber(difficulty), TargetNumbers.WARTANK_HEIGHT.getNumber(difficulty), difficulty);
        super.setX(x);
        super.setY(gameField.getHeight() - y3 - TargetNumbers.WARTANK_HEIGHT.getNumber(difficulty) + 10);
        super.setRotate(angle);

        warTankSetFill();

        this.angle = angle;
        this.maxHP = TargetNumbers.WARTANK_HP.getNumber(difficulty);
        this.damage = TargetNumbers.WARTANK_DAMAGE.getNumber(difficulty);
        this.speed = TargetNumbers.WARTANK_SPEED.getNumber(difficulty);


        if (rotate) {
            super.setScaleX(-1);
            this.angle = 180 - angle;
            speed = -speed;
        }

        gameField.getChildren().add(this);
        this.visibleProperty().setValue(true);
    }

    private void warTankSetFill() {
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/warTank.png")));
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }
}
