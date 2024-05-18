package controller.animations;

import model.gameModels.targets.Truck;
import model.gameModels.Wave;
import model.enums.TargetNumbers;
import javafx.animation.Transition;

public class TruckAnimation extends Transition {
    private double X;
    private double Y;
    public Truck truck;
    public double speed;
    public int angle;
    Wave wave;
    public TruckAnimation(Truck truck, Wave wave) {
        this.truck = truck;
        setCycleCount(-1);
        setCycleDuration(javafx.util.Duration.millis(100));
        this.wave = wave;
        speed = truck.speed;
        angle = (int) truck.angle;
    }
    @Override
    protected void interpolate(double v) {
        truckMove();
    }

    private void truckMove() {

        checkValid();
        double X = truck.getX();
        double Y = truck.getY();
        this.X = X + speed;
        this.Y =  truck.gameField.getHeight() - truck.gameField.getGroundHeightAt((int) this.X);

        double x2 =  this.X + TargetNumbers.TRUCK_WIDTH.getNumber(truck.difficulty);
        double y2 =  truck.gameField.getHeight() - truck.gameField.getGroundHeightAt((int) x2);

        angle = (int) (Math.toDegrees(Math.atan((this.Y - y2) / TargetNumbers.TRUCK_WIDTH.getNumber(truck.difficulty))));
        angleCheck();

        truck.setRotate(-angle);

        truck.setX(this.X);
        truck.setY(this.Y - TargetNumbers.TRUCK_HEIGHT.getNumber(truck.difficulty) + 10);
    }

    private void angleCheck() {
        if (angle < 0) {
            angle += 360;
        }
        if (angle > 360) {
            angle -= 360;
        }
    }

    private void checkValid() {
        if (X > 900){
            truck.setScaleX(-1);
            angle = 180 - angle;
            X = 850;
            speed = -speed;

        }
        if (X < -100){
            truck.setScaleX(1);
            angle = 180 - angle;
            X = -50;
            speed = -speed;
        }
    }
}
