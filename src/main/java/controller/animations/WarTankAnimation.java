package controller.animations;

import model.gameModels.Wave;
import model.gameModels.enemies.CircleAround;
import model.gameModels.enemies.Rocket;
import model.gameModels.enemies.WarTank;
import model.enums.TargetNumbers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;

public class WarTankAnimation extends Transition {
    public WarTank warTank;
    private double X;
    private double Y;
    private boolean isShootedRecently;
    WarTank tank;
    public double speed;
    public int angle;
    Wave wave;
    private CircleAround circleAround;
    public WarTankAnimation(WarTank tank, Wave wave) {
        this.tank = tank;
        setCycleCount(-1);
        setCycleDuration(javafx.util.Duration.millis(100));
        this.wave = wave;
        speed = tank.speed;
        angle = (int) tank.angle;
        warTank = tank;
        circleAround = new CircleAround(tank);
        warTank.circleAround = circleAround;
        wave.game.gamePane.getChildren().add(circleAround);
    }
    @Override
    protected void interpolate(double v) {
        tankMove();
            if (circleAround.intersects(wave.game.warJet.getBoundsInParent()) && !isShootedRecently){
                shootRocket();
                isShootedRecently = true;
            }
    }

    private void tankMove() {

        checkValid();
        double X =  tank.getX();
        double Y =  tank.getY();
        this.X = X + (speed);
        this.Y = (tank.gameField.getHeight() - tank.gameField.getGroundHeightAt((int) this.X));

        double x2 = this.X + TargetNumbers.WARTANK_WIDTH.getNumber(tank.difficulty);
        double y2 = tank.gameField.getHeight() - tank.gameField.getGroundHeightAt((int) x2);

        angle = (int) (Math.toDegrees(Math.atan((this.Y - y2) / TargetNumbers.WARTANK_WIDTH.getNumber(tank.difficulty))));
        angleCheck();

        tank.setRotate(-angle);

        tank.setX(this.X);
        tank.setY(this.Y - TargetNumbers.WARTANK_HEIGHT.getNumber(tank.difficulty) + 10);
        circleAround.setCenterX(tank.getCenterX());
        circleAround.setCenterY(tank.getCenterY());
    }

    private void shootRocket() {
        double Xjet = wave.game.warJet.getX();
        double Yjet = wave.game.warJet.getY();
        double Xtank = tank.getCenterX();
        double Ytank = tank.getCenterY();
        double angle = Math.toDegrees(Math.atan((Ytank - Yjet) / (Xjet - Xtank)));
        if (Xjet < Xtank){
            angle = 180 + angle;
        }
        Rocket rocket = new Rocket( Xtank, Ytank, angle);
        wave.game.gamePane.getChildren().add(rocket);
        RocketAnimation rocketAnimation = new RocketAnimation(rocket, wave, TargetNumbers.WARTANK_DAMAGE.getNumber(tank.difficulty));
        wave.animations.add(rocketAnimation);
        rocketAnimation.play();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(1000), event -> {
            isShootedRecently = false;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
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
        if ((X > 900 && speed > 0) || (X < -100 && speed < 0)){
            //destroy tank
            tank.gameField.getChildren().remove(tank);
            wave.animations.remove(this);
        }
    }
}
