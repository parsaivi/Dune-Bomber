package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Trench extends Target {
    public Trench(GameField gameField , GameDifficulty gameDifficulty, double x, double angle, double y3) {
        super(gameField, TargetNumbers.TRENCH_WIDTH.getNumber(gameDifficulty), TargetNumbers.TRENCH_HEIGHT.getNumber(gameDifficulty), gameDifficulty);
        this.damage = TargetNumbers.BUILDING_DAMAGE.getNumber(gameDifficulty);
        this.maxHP = TargetNumbers.BUILDING_HP.getNumber(gameDifficulty);
        this.speed = 0;
        super.setX(x);
        super.setY(600 - y3 - TargetNumbers.TRENCH_HEIGHT.getNumber(gameDifficulty) + 10);
        super.setRotate(angle);
        trenchSetFill();
    }

    private void trenchSetFill() {
        Random random = new Random();
        switch (random.nextInt()) {
            case 0:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/trench1.png")));
                break;
            case 1:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/trench2.png")));
                break;
            default:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/trench3.png")));
                break;
        }
    }

    public Trench(GameField gameField) {
        super(gameField);
    }
}
