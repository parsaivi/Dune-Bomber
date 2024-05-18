package controller.animations;

import model.Game;
import model.gameModels.*;
import model.gameModels.jet.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class WarJetAnimation extends Transition {
    public Game game;
    public GameField gameField;
    public Pane gamePane;
    public WarJet warJet;
    public JetFire jetFire;
    private Timeline timeline;
    public double speed = 0.5;
    public int angle = 0;
    public boolean upButton = false;
    public boolean downButton = false;
    public boolean leftButton = false;
    public boolean rightButton = false;
    public boolean isFlipped = false;
    public boolean isShootedRecently = false;
    public boolean isAtomicBombDroppedRecently = false;
    public boolean isClusterBombDroppedRecently = false;

    public WarJetAnimation(Game game, WarJet warJet) {
        this.game = game;
        gamePane = game.gamePane;
        this.gameField = game.currentWave.gameField;
        this.speed = warJet.speed;
        this.warJet = warJet;
        this.angle = warJet.angle;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
        timeline = new Timeline();
        timeline.play();
    }

    @Override
    protected void interpolate(double v) {
        double X = warJet.getX();
        double Y = warJet.getY();
        double xTransition = 0;
        double yTransition = 0;

        if (upButton) {
            speed += 0.01;
        }
        if (downButton) {
            speed -= 0.01;
        }
        controlSpeed();
        if (leftButton) {
            angle -= 2;
        }
        if (rightButton) {
            angle += 2;
        }
        controlAngle();

        checkRotate();
        topHitBox();
        bottomHitBox();

        gameField = game.currentWave.gameField;
        if (X < -warJet.getWidth()) {
            warJet.setX(gameField.getWidth());
        }
        if (X > gameField.getWidth()) {
            warJet.setX(-warJet.getWidth());
        }

        warJet.setRotate(-angle);
        warJet.setX(warJet.getX() + speed * Math.cos(Math.toRadians(angle)));
        warJet.setY(warJet.getY() - speed * Math.sin(Math.toRadians(angle)));
        warJet.speed = speed;
        warJet.angle = angle;
        warJet.isRotated = isFlipped;


        for (JetFire jetFire : warJet.jetFires) {
            jetFire.setX(warJet.getX() + warJet.getWidth() / 2 - jetFire.getWidth() / 2);
            jetFire.setY(warJet.getY() + warJet.getHeight() - jetFire.getHeight());
        }
    }

    private void bottomHitBox() {
        if(game.currentWave.gameField.groundPolygon.intersects(warJet.getX(), warJet.getY(), warJet.getWidth(), warJet.getHeight())){
            warJet.bottomHits();
            game.updateHP();
        }
    }

    private void topHitBox() {
        if (warJet.getY() < 40) {
            angle = 360 - angle;
        }
    }

    private void checkRotate() {
        if (angle > 90 && angle < 270) {
            if (!isFlipped) {
                warJet.setScaleY(-1);
                isFlipped = true;
            }
        } else {
            if (isFlipped) {
                warJet.setScaleY(1);
                isFlipped = false;
            }
        }
    }

    private void controlSpeed() {
        if (speed > 5) {
            speed = 5;
        }
        if (speed < 0.5) {
            speed = 0.5;
        }
    }

    private void controlAngle() {
        if (angle > 360) {
            angle = angle - 360;
        }
        if (angle < 0) {
            angle = angle + 360;
        }
    }

    public void shoot() {
        if (isShootedRecently) {
            return;
        }
        game.addShoots();
        //create a new missile and add it to the gamePane
        MissileAnimation missileAnimation = new MissileAnimation(game, new Missile(warJet.getX() + warJet.getWidth() / 2, warJet.getY() + warJet.getHeight() / 2), this, warJet);
        gamePane.getChildren().add(missileAnimation.missile);
        missileAnimation.play();
        isShootedRecently = true;
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), event -> {
            isShootedRecently = false;
            game.isReleazedTankRecently = false;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    String missileSoundSelecter() {
        Random random = new Random();
        int soundNumber = random.nextInt(5);
        switch (soundNumber) {
            case 0:
                return "/Sounds/missileShoot1.mp3";
            case 1:
                return "/Sounds/missileShoot2.mp3";
            case 2:
                return "/Sounds/missileShoot3.mp3";
            case 3:
                return "/Sounds/missileShoot4.mp3";
            default:
                return "/Sounds/missileShoot5.mp3";
        }

    }

    public void dropAtomicBomb() {
        if (warJet.remainingAtomicBombs == 0 || isAtomicBombDroppedRecently) {
            return;
        }
        game.addShoots();
        //create a new atomic bomb and add it to the gamePane
        AtomicBombAnimation atomicBombAnimation = new AtomicBombAnimation(game, new AtomicBomb(warJet.getX() + warJet.getWidth() / 2, warJet.getY() + warJet.getHeight() / 2), this, warJet);
        gamePane.getChildren().add(atomicBombAnimation.atomicBomb);
        atomicBombAnimation.play();
        game.dropAtomicBomb();
        isAtomicBombDroppedRecently = true;
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), event -> {
            isAtomicBombDroppedRecently = false;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    String atomicBombSoundSelecter() {
        return "/Sounds/atomicShoot.mp3";
    }

    public void dropClusterBomb() {
        if (warJet.remainingClusterBombs == 0 || isClusterBombDroppedRecently) {
            return;
        }
        game.addShoots();
        //create a new cluster bomb and add it to the gamePane
        ClusterBombAnimation clusterBombAnimation = new ClusterBombAnimation(game, new ClusterBomb(warJet.getX() + warJet.getWidth() / 2, warJet.getY() + warJet.getHeight() / 2), this, warJet);
        gamePane.getChildren().add(clusterBombAnimation.clusterBomb);
        clusterBombAnimation.play();
        game.dropClusterBomb();
        isClusterBombDroppedRecently = true;
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), event -> {
            isClusterBombDroppedRecently = false;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    String clusterBombSoundSelecter() {
        return "/Sounds/clusterShoot.mp3";
    }
}
