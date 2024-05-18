package model.gameModels.targets;

import model.gameModels.GameField;
import model.gameModels.Target;
import model.gameModels.Vehicle;
import model.enums.GameDifficulty;
import model.enums.TargetNumbers;

public class Helicopter extends Target implements Vehicle {
    public Helicopter(GameField gameField, GameDifficulty difficulty) {
        super(gameField);
        this.maxHP = TargetNumbers.HELICOPTER_HP.getNumber(difficulty);
        this.damage = TargetNumbers.HELICOPTER_DAMAGE.getNumber(difficulty);
        this.speed = TargetNumbers.HELICOPTER_SPEED.getNumber(difficulty);
    }

    public void move() {
        // Move tank based on gameField
        // Update the tank's x-coordinate based on its speed
        this.setLayoutX(this.getLayoutX() + speed);

        // Update the tank's y-coordinate based on the ground's height at the new x-coordinate
        int groundHeight = gameField.getGroundHeightAt((int) this.getLayoutX());
        this.setLayoutY(gameField.getHeight() - groundHeight - this.getHeight());
    }
}
