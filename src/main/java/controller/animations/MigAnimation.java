package controller.animations;

import model.gameModels.Wave;
import model.gameModels.enemies.CircleAround;
import model.gameModels.enemies.Mig;
import model.gameModels.enemies.Rocket;
import model.enums.TargetNumbers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;

public class MigAnimation extends Transition {
    private double X;
    private double Y;
    private boolean isShootedRecently;
    Mig mig;
    public double speed;
    public int angle;
    Wave wave;
    private CircleAround circleAround;
    public MigAnimation(Mig mig, Wave wave) {
        this.mig = mig;
        setCycleCount(-1);
        setCycleDuration(javafx.util.Duration.millis(100));
        this.wave = wave;
        speed = mig.speed;
        angle = (int) mig.angle;
        circleAround = new CircleAround(mig);
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

    private void shootRocket() {
        double Xjet = wave.game.warJet.getX();
        double Yjet = wave.game.warJet.getY();
        double Xtank = mig.getCenterX();
        double Ytank = mig.getCenterY();
        double angle = Math.toDegrees(Math.atan((Yjet - Ytank) / (Xjet - Xtank)));
        if (Xjet < Xtank){
            angle = 180 + angle;
        }
        angle = -angle;
        Rocket rocket = new Rocket( Xtank, Ytank, angle);
        wave.game.gamePane.getChildren().add(rocket);
        RocketAnimation rocketAnimation = new RocketAnimation(rocket, wave, TargetNumbers.MIG_DAMAGE.getNumber(mig.difficulty));
        rocketAnimation.play();
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(1000), event -> {
            isShootedRecently = false;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void tankMove() {

        checkValid();
        double X =  mig.getX();
        this.X = X + (speed);

        mig.setX(this.X);

        circleAround.setCenterX(mig.getCenterX());
        circleAround.setCenterY(mig.getCenterY());
    }


    private void checkValid() {
        if ((X > 900 && speed > 0) || (X < 0 && speed < 0)){
            //destroy mig
            mig.gameField.getChildren().remove(mig);
            wave.animations.remove(this);
        }
    }
}
