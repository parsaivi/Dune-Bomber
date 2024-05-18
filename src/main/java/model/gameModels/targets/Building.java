package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Building extends Target {
    public Building(GameField gameField, GameDifficulty gameDifficulty, double x,  double angle, double y3) {
        super(gameField, TargetNumbers.BUILDING_WIDTH.getNumber(gameDifficulty), TargetNumbers.BUILDING_HEIGHT.getNumber(gameDifficulty), gameDifficulty);
        this.damage = TargetNumbers.BUILDING_DAMAGE.getNumber(gameDifficulty);
        this.maxHP = TargetNumbers.BUILDING_HP.getNumber(gameDifficulty);
        this.speed = 0;
        super.setX(x);
        super.setY(600 - y3 - TargetNumbers.BUILDING_HEIGHT.getNumber(gameDifficulty) + 10);
        super.setRotate(angle);
        buildingSetFill();
    }

    private void buildingSetFill() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/building1.png")));
                break;
            case 1:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/building2.png")));
                break;
            case 2:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/building3.png")));
                break;
            case 3:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/building4.png")));
                break;
            default:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/building5.png")));
                break;
        }
    }
}
