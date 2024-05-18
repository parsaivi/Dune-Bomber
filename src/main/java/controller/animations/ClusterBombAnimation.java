package controller.animations;

import model.Game;
import model.gameModels.*;
import model.gameModels.jet.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Random;

public class ClusterBombAnimation extends Transition {

    public ClusterBomb clusterBomb;
    MediaPlayer mediaPlayer;
    public WarJet warJet;
    public WarJetAnimation warJetAnimation;
    public Game game;
    public Wave wave;
    public double xSpeed = 5;
    public double ySpeed = 5;
    public int angle = 0;
    public double acceleration = 0.05;

    public ClusterBombAnimation(Game game, ClusterBomb clusterBomb, WarJetAnimation warJetAnimation, WarJet warJet) {
        this.clusterBomb = clusterBomb;
        this.warJet = warJet;
        this.warJetAnimation = warJetAnimation;
        this.game = game;
        this.wave = game.currentWave;
        angle = warJetAnimation.angle;
        this.clusterBomb.setRotate(- angle);
        if (warJetAnimation.angle > 90 && warJetAnimation.angle < 270) {
            this.clusterBomb.setScaleX(-1);
        }
        xSpeed = warJetAnimation.speed * Math.cos(Math.toRadians(angle));
        ySpeed = warJetAnimation.speed * 3 * Math.sin(Math.toRadians(angle));
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
        Media sound = new Media(getClass().getResource(warJetAnimation.clusterBombSoundSelecter()).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();

    }


    public void topHitBox() {
        if (clusterBomb.getY() < 0) {
            miss();
        }
    }

    private void miss() {
        game.gamePane.getChildren().remove(clusterBomb);
        mediaPlayer.stop();
        this.stop();
    }

    private void explosion() {
        ClusterExplosion clusterBomb1 = new ClusterExplosion(this.clusterBomb);
        wave.game.gamePane.getChildren().add(clusterBomb1);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), e -> {
            wave.game.gamePane.getChildren().remove(clusterBomb1);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();

        wave.clusterExplosion(clusterBomb.getX() + clusterBomb.getWidth() / 2);
        game.gamePane.getChildren().remove(clusterBomb);
        mediaPlayer.stop();
        Media sound = new Media(getClass().getResource("/Sounds/clusterHits.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
        this.stop();
    }

    public void bottomHitBox() {
        if(game.currentWave.gameField.groundPolygon.intersects(clusterBomb.getX(), clusterBomb.getY(), clusterBomb.getWidth(), clusterBomb.getHeight())){
            explosion();
        }
    }

    @Override
    protected void interpolate(double v) {
        if (ySpeed < -4){
            bombletDrop();
        }
        double X = clusterBomb.getX();
        double Y = clusterBomb.getY();
        angleCheck();
        double xTransition = xSpeed;
        double yTransition = ySpeed;
        ySpeed -= acceleration;
        topHitBox();
        bottomHitBox();
        this.angle = angleCalculation();
        clusterBomb.setRotate(-angle);
        clusterBomb.setX(X + xTransition);
        clusterBomb.setY(Y - yTransition);
    }

    private void bombletDrop() {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            BombletAnimation bombletAnimation = new BombletAnimation(game, new Bomblet(clusterBomb.getX(), clusterBomb.getY()), this, clusterBomb, (rand.nextDouble() * 6 - 3));
            game.gamePane.getChildren().add(bombletAnimation.bomblet);
            bombletAnimation.play();
        }
        game.gamePane.getChildren().remove(clusterBomb);
        ClusterDrop clusterBomb1 = new ClusterDrop(this.clusterBomb);
        wave.game.gamePane.getChildren().add(clusterBomb1);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> {
            wave.game.gamePane.getChildren().remove(clusterBomb1);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();
        Media sound = new Media(getClass().getResource("/Sounds/missileHits.wav").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
        this.stop();
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
