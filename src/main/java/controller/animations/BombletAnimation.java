package controller.animations;

import model.Game;
import model.gameModels.*;
import model.gameModels.jet.Bomblet;
import model.gameModels.jet.BombletExplosion;
import model.gameModels.jet.ClusterBomb;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BombletAnimation extends Transition {
    public Bomblet bomblet;
    public ClusterBomb clusterBomb;
    public MediaPlayer mediaPlayer;
    public ClusterBombAnimation clusterBombAnimation;
    public Game game;
    public Wave wave;
    public double xSpeed = 5;
    public double ySpeed = 5;
    public int angle = 0;
    public double acceleration = 0.05;

    public BombletAnimation(Game game, Bomblet bomblet, ClusterBombAnimation clusterBombAnimation, ClusterBomb clusterBomb, double xSpeed) {
        this.bomblet = bomblet;
        this.clusterBomb = clusterBomb;
        this.clusterBombAnimation = clusterBombAnimation;
        this.game = game;
        this.wave = game.currentWave;
        this.xSpeed = xSpeed;
        this.ySpeed = -0.1 * clusterBombAnimation.ySpeed;
        angle = angleCalculation();
        this.bomblet.setRotate(- angle);
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
    }


    public void topHitBox() {
        if (bomblet.getY() < 0) {
            explosion();
        }
    }

    private void explosion() {
        BombletExplosion bombletExplosion = new BombletExplosion(this.bomblet);
        wave.game.gamePane.getChildren().add(bombletExplosion);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> {
            wave.game.gamePane.getChildren().remove(bombletExplosion);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();

        wave.bombletExplosion(bomblet.getX() + bomblet.getWidth() / 2);
        game.gamePane.getChildren().remove(bomblet);
        Media sound = new Media(getClass().getResource("/Sounds/bombletHits.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
        this.stop();
    }

    public void bottomHitBox() {
        if(game.currentWave.gameField.groundPolygon.intersects(bomblet.getX(), bomblet.getY(), bomblet.getWidth(), bomblet.getHeight())){
            explosion();
        }
    }

    @Override
    protected void interpolate(double v) {
        double X = bomblet.getX();
        double Y = bomblet.getY();
        angleCheck();
        double xTransition = xSpeed;
        double yTransition = ySpeed;
        ySpeed -= acceleration;
        topHitBox();
        bottomHitBox();
        this.angle = angleCalculation();
        bomblet.setRotate(-angle);
        bomblet.setX(X + xTransition);
        bomblet.setY(Y - yTransition);
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
