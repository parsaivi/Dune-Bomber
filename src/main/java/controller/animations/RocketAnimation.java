package controller.animations;

import model.gameModels.Wave;
import model.gameModels.enemies.Rocket;
import model.gameModels.jet.JetFire;
import model.gameModels.jet.WarJet;
import model.gameModels.targets.RocketFire;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

public class RocketAnimation extends Transition {
    public Rocket rocket;
    public WarJet warJet;
    public MediaPlayer mediaPlayer;
    public Wave wave;
    public double speed = 2;
    public double angle = 0;
    double damage;
    public RocketAnimation(Rocket rocket, Wave wave, double damage) {
        Random random = new Random();
        this.rocket = rocket;
        this.wave = wave;
        this.angle = rocket.angle;
        angle += random.nextInt(2) - 1;
        this.warJet = wave.game.warJet;
        this.damage = damage;
        this.setCycleCount(-1);
        this.setCycleDuration(javafx.util.Duration.millis(100));
    }

    @Override
    protected void interpolate(double v) {
        rocketMove();
    }

    private void rocketMove() {
        checkShoot();
        checkMissed();
        double X = rocket.getX();
        double Y = rocket.getY();
        double xTransition;
        double yTransition;

        xTransition = speed * Math.cos(Math.toRadians(angle));
        yTransition = speed * Math.sin(Math.toRadians(angle));

        rocket.setX(X + xTransition);
        rocket.setY(Y - yTransition);
    }

    private void checkMissed() {
        if (rocket.getX() > wave.game.gamePane.getWidth() || rocket.getX() < 0 || rocket.getY() > wave.game.gamePane.getHeight() || rocket.getY() < 0){
            wave.game.gamePane.getChildren().remove(rocket);
            this.stop();
        }
    }

    private void checkShoot() {
        if (!rocket.intersects(warJet.getBoundsInLocal())) {
            return;
        }
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/Sounds/rocketHits.mp3").toExternalForm()));
        mediaPlayer.setMute(wave.game.player.isSoundMuted());
        mediaPlayer.play();

        warJet.Hit(damage);
        wave.game.gamePane.getChildren().remove(rocket);
        wave.game.checkForGameOver();
//        warJet.setFire(new JetFire(warJet));
        Timeline timeline = new Timeline();
        JetFire jetFire = new JetFire(warJet);
        RocketFire rocketFire = new RocketFire(warJet);
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(1000), e -> {
            warJet.removeFire(jetFire);
            wave.game.gamePane.getChildren().remove(jetFire);
            wave.game.gamePane.getChildren().remove(rocketFire);
        });
        warJet.setFire(jetFire);
        wave.game.gamePane.getChildren().add(jetFire);
        wave.game.gamePane.getChildren().add(rocketFire);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();
        this.stop();
    }
}
