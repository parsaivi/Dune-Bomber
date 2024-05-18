package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class Tree extends Target {
    public Tree(GameField gameField, GameDifficulty gameDifficulty, int x, int y) {
        super(gameField, TargetNumbers.TREE_WIDTH.getNumber(gameDifficulty), TargetNumbers.TREE_HEIGHT.getNumber(gameDifficulty), gameDifficulty);
        this.maxHP = TargetNumbers.TREE_HP.getNumber(gameDifficulty);
        this.damage = TargetNumbers.TREE_DAMAGE.getNumber(gameDifficulty);
        this.speed = 0;
        super.setX(x);
        super.setY(600 - y - TargetNumbers.TREE_HEIGHT.getNumber(gameDifficulty) + 10);
        treeSetFill();
    }

    private void treeSetFill() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tree1.png")));
                break;
            case 1:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tree2.png")));
                break;
            case 2:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tree3.png")));
                break;
            default:
                this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/tree4.png")));
                break;
        }
    }
}
