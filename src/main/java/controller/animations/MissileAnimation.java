package controller.animations;

import model.Game;
import model.gameModels.jet.Missile;
import model.gameModels.jet.MissileExplosion;
import model.gameModels.jet.WarJet;
import model.gameModels.Wave;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MissileAnimation extends Transition {
    public Missile missile;
    public WarJet warJet;
    public MediaPlayer mediaPlayer;
    public WarJetAnimation warJetAnimation;
    public Game game;
    public Wave wave;
    public double xSpeed = 2;
    public double ySpeed = 0;
    public int angle = 0;
    public double acceleration = 0.05;

    public MissileAnimation(Game game, Missile missile, WarJetAnimation warJetAnimation, WarJet warJet) {
        this.missile = missile;
        this.warJet = warJet;
        this.warJetAnimation = warJetAnimation;
        this.game = game;
        this.wave = game.currentWave;
        angle = warJetAnimation.angle;
        this.missile.setRotate(- angle);
        xSpeed = (warJetAnimation.angle > 90 && warJetAnimation.angle < 270) ? -1 : 1;
        xSpeed += warJetAnimation.speed * Math.cos(Math.toRadians(angle));
        ySpeed = warJetAnimation.speed * 3 * Math.sin(Math.toRadians(angle));
        if (warJetAnimation.angle > 90 && warJetAnimation.angle < 270) {
            this.missile.setScaleX(-1);
        }
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
            Media sound = new Media(getClass().getResource(warJetAnimation.missileSoundSelecter()).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
    }


    public void topHitBox() {
        if (missile.getY() < 0) {
            miss();
        }
    }

    private void miss() {
        game.gamePane.getChildren().remove(missile);
        mediaPlayer.stop();
        this.stop();
    }

    private void explosion() {
        MissileExplosion missileExplosion = new MissileExplosion(this.missile);
        wave.game.gamePane.getChildren().add(missileExplosion);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), e -> {
            wave.game.gamePane.getChildren().remove(missileExplosion);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();
        wave.missileExplosion(missile.getX() + missile.getWidth() / 2);
        game.gamePane.getChildren().remove(missile);
        mediaPlayer.stop();
        Media sound = new Media(getClass().getResource("/Sounds/missileHits.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
        this.stop();

    }

    public void bottomHitBox() {
        if(game.currentWave.gameField.groundPolygon.intersects(missile.getX(), missile.getY(), missile.getWidth(), missile.getHeight())){
            explosion();
        }
    }

    @Override
    protected void interpolate(double v) {
        double X = missile.getX();
        double Y = missile.getY();
        angleCheck();
        double xTransition = xSpeed;
        double yTransition = ySpeed;
        ySpeed -= acceleration;
        topHitBox();
        bottomHitBox();
        this.angle = angleCalculation();
        missile.setRotate(-angle);
        missile.setX(X + xTransition);
        missile.setY(Y - yTransition);
    }

    private int angleCalculation() {
        return (int) Math.toDegrees(Math.atan(ySpeed / xSpeed));
    }

    private void angleCheck() {
        if (angle < 0) {
            angle += 360;
        }
        if (angle > 360) {
            angle -= 360;
        }
    }
}
