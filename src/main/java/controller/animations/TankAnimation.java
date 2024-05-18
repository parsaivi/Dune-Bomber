package controller.animations;

import model.gameModels.targets.Tank;
import model.gameModels.Wave;
import model.enums.TargetNumbers;
import javafx.animation.Transition;

public class TankAnimation extends Transition {
    private double X;
    private double Y;
    public Tank tank;
    public double speed;
    public int angle;
    Wave wave;
    public TankAnimation(Tank tank, Wave wave) {
        this.tank = tank;
        setCycleCount(-1);
        setCycleDuration(javafx.util.Duration.millis(100));
        this.wave = wave;
        speed = tank.speed;
        angle = (int) tank.angle;
    }
    @Override
    protected void interpolate(double v) {
        tankMove();
    }

    private void tankMove() {

        checkValid();
        double X =  tank.getX();
        double Y =  tank.getY();
        this.X = X + (speed);
        this.Y = (tank.gameField.getHeight() - tank.gameField.getGroundHeightAt((int) this.X));

        double x2 = this.X + TargetNumbers.TANK_WIDTH.getNumber(tank.difficulty);
        double y2 = tank.gameField.getHeight() - tank.gameField.getGroundHeightAt((int) x2);

        angle = (int) (Math.toDegrees(Math.atan((this.Y - y2) / TargetNumbers.TANK_WIDTH.getNumber(tank.difficulty))));
        angleCheck();

        tank.setRotate(-angle);
        tank.setX(this.X);
        tank.setY(this.Y - TargetNumbers.TANK_HEIGHT.getNumber(tank.difficulty) + 10);
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
            tank.setScaleX(-1);
            angle = 180 - angle;
            X = 850;
            speed = -speed;

        }
        if (X < -100){
            tank.setScaleX(1);
            angle = 180 - angle;
            X = -50;
            speed = -speed;
        }
    }
}
