package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.gameModels.Vehicle;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Tank extends Target implements Vehicle {
    public Tank(GameField gameField, GameDifficulty difficulty, double x, boolean rotate, double angle, double y3) {
        super(gameField, TargetNumbers.TANK_WIDTH.getNumber(difficulty), TargetNumbers.TANK_HEIGHT.getNumber(difficulty), difficulty);
        this.maxHP = TargetNumbers.TANK_HP.getNumber(difficulty);
        this.damage = TargetNumbers.TANK_DAMAGE.getNumber(difficulty);
        this.speed = TargetNumbers.TANK_SPEED.getNumber(difficulty);
        super.setX(x);
        super.setY(gameField.getHeight() - y3 - TargetNumbers.TANK_HEIGHT.getNumber(difficulty) + 10);
        super.setRotate(angle);
        this.angle = angle;
        tankSetFill();

        if (rotate) {
            super.setScaleX(-1);
            this.angle = 180 - angle;
            speed = -speed;
        }

        gameField.getChildren().add(this);
        this.visibleProperty().setValue(true);
    }

    private void tankSetFill() {
        Random random = new Random();
        int i = random.nextInt(6);
        switch (i) {
            case 0:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank1.png")));
                break;
            case 1:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank2.png")));
                break;
            case 2:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank3.png")));
                break;
            case 3:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank4.png")));
                break;
            case 4:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank5.png")));
                break;
            default:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tank6.png")));
                break;
        }
    }

}
