package controller.animations;

import model.Game;
import model.gameModels.jet.AtomicBomb;
import model.gameModels.jet.AtomicExplosion;
import model.gameModels.jet.WarJet;
import model.gameModels.Wave;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AtomicBombAnimation extends Transition {
    public AtomicBomb atomicBomb;
    public MediaPlayer mediaPlayer;
    public WarJet warJet;
    public Wave wave;
    public WarJetAnimation warJetAnimation;
    public Game game;
    public double speed = 5;
    public double acceleration = 0.05;
    public int angle = 270;

    public AtomicBombAnimation(Game game, AtomicBomb atomicBomb, WarJetAnimation warJetAnimation, WarJet warJet) {
        this.atomicBomb = atomicBomb;
        this.warJet = warJet;
        this.warJetAnimation = warJetAnimation;
        this.game = game;
        this.wave = game.currentWave;
        speed = 0;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
        Media sound = new Media(getClass().getResource(warJetAnimation.atomicBombSoundSelecter()).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
    }

    private void explosion() {
        AtomicExplosion atomicExplosion = new AtomicExplosion(this.atomicBomb);
        wave.game.gamePane.getChildren().add(atomicExplosion);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(5000), e -> {
            wave.game.gamePane.getChildren().remove(atomicExplosion);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();

        wave.atomicExplosion(atomicBomb.getX() + atomicBomb.getWidth() / 2);
        game.gamePane.getChildren().remove(atomicBomb);
        mediaPlayer.stop();
        Media sound = new Media(getClass().getResource("/Sounds/atomicHits.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setMute(game.player.isSoundMuted());
        mediaPlayer.play();
        this.stop();
    }

    public void bottomHitBox() {
        if(game.currentWave.gameField.groundPolygon.intersects(atomicBomb.getX(), atomicBomb.getY(), atomicBomb.getWidth(), atomicBomb.getHeight())){
            explosion();
        }
    }

    @Override
    protected void interpolate(double v) {
        double Y = atomicBomb.getY();
        double yTransition = speed;
        speed -= acceleration;
        bottomHitBox();
        atomicBomb.setY(Y - yTransition);
    }



}
