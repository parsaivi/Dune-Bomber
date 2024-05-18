package model.gameModels.enemies;

import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class CircleAround extends Circle {
    public CircleAround(double x, double y, double radius) {
        super(x, y, radius);
    }

    public CircleAround(WarTank tank) {
        super(tank.getCenterX(), tank.getCenterY(), TargetNumbers.CIRCLE_AROUND_RADIUS.getNumber(tank.difficulty));
        //set a black border on it
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/radar.png")));

    }

    public CircleAround(Mig mig) {
        super(mig.getCenterX(), mig.getCenterY(), TargetNumbers.CIRCLE_AROUND_RADIUS.getNumber(mig.difficulty));
        //set a black border on it
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/radar.png")));
    }
}
