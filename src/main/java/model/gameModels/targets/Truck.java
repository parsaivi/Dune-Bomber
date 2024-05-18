package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.gameModels.Vehicle;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Truck extends Target implements Vehicle {
    public Truck(GameField gameField, GameDifficulty difficulty, double x, boolean rotate, double angle, double y3) {
        super(gameField, TargetNumbers.TRUCK_WIDTH.getNumber(difficulty), TargetNumbers.TRUCK_HEIGHT.getNumber(difficulty), difficulty);
        this.maxHP = TargetNumbers.TRUCK_HP.getNumber(difficulty);
        this.damage = TargetNumbers.TRUCK_DAMAGE.getNumber(difficulty);
        this.speed = TargetNumbers.TRUCK_SPEED.getNumber(difficulty);
        super.setX(x);
        super.setY(gameField.getHeight() - y3 - TargetNumbers.TRUCK_HEIGHT.getNumber(difficulty) + 10);
        super.setRotate(angle);
        this.angle = angle;
        truckSetFill();
        if (rotate) {
            super.setScaleX(-1);
            this.angle = 180 - angle;
            speed = -speed;
        }
        gameField.getChildren().add(this);
        this.visibleProperty().setValue(true);
    }

    private void truckSetFill() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/truck1.png")));
                break;
            case 1:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/truck2.png")));
                break;
            default:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/truck3.png")));
                break;
        }
    }

}
