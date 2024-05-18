package model;

import controller.GameLauncherController;
import controller.animations.TankAnimation;
import model.gameModels.GameField;
import model.gameModels.jet.WarJet;
import model.gameModels.Wave;
import controller.animations.WarJetAnimation;
import model.gameModels.targets.Tank;
import view.GameLauncher;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.saved.*;

import java.util.Random;

public class Game {
    public User player;
    public Wave wave1;
    public Wave wave2;
    public Wave wave3;
    public Wave currentWave;
    public int waveNumber;
    public MediaPlayer mediaPlayer;
    public int HP;
    public int boost;
    public int atomicBombs;
    public int clusterBombs;
    public WarJet warJet;
    public WarJetAnimation warJetAnimation;
    public GameLauncher gameLauncher;
    public Pane gamePane;

    private int kills;
    private int shoots;
    private int hits;

    private boolean isWin;
    public boolean isReleazedTankRecently;
    public boolean isGameStopped;
    private int i = 0;

    public Game(User player, Pane gamePane) {
        this.gamePane = gamePane;
        this.player = player;
        this.HP = 100;
        this.boost = 0;
        this.atomicBombs = 0;
        this.clusterBombs = 0;
        this.warJet = new WarJet();
        this.wave1 = new Wave(this, new GameField(1), 1, player.getDifficulty());
        this.wave2 = new Wave(this, new GameField(2), 2, player.getDifficulty());
        this.wave3 = new Wave(this, new GameField(3), 3, player.getDifficulty());
        waveNumber = 1;
        currentWave = wave1;
    }

    public Game(Pane gamePane) {
        this.gamePane = gamePane;
        this.player = App.getLoggedInUser();
        this.HP = 100;
        this.boost = 0;
        this.atomicBombs = 0;
        this.clusterBombs = 0;
        this.warJet = new WarJet();
        this.wave1 = new Wave(this, new GameField(1), 1, player.getDifficulty());
        this.wave2 = new Wave(this, new GameField(2), 2, player.getDifficulty());
        this.wave3 = new Wave(this, new GameField(3), 3, player.getDifficulty());
        waveNumber = 1;
        currentWave = wave1;
    }

    public void startNewGame(Pane gamePane) {
        if (!player.isMusicMuted()) playMusic();
        this.gamePane = gamePane;
        createWarJet(gamePane);

        while (!isGameOver()) {
            startNewWave(gamePane);
            break;
        }
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void playMusic() {
        Media sound = new Media(getClass().getResource(musicSelecter()).toExternalForm());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(-1); // This will make the music loop indefinitely
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                // This block of code will be executed when the music ends
                playMusic();
            }
        });
        mediaPlayer.play();
    }

    private String musicSelecter() {
        Random random = new Random();
        int musicNumber = random.nextInt(13);
        switch (musicNumber) {
            case 0:
                return "/Music/track1.mp3";
            case 1:
                return "/Music/track2.mp3";
            case 2:
                return "/Music/track3.mp3";
            case 3:
                return "/Music/track4.mp3";
            case 4:
                return "/Music/track5.mp3";
            case 5:
                return "/Music/track6.mp3";
            case 6:
                return "/Music/track7.mp3";
            case 7:
                return "/Music/track8.mp3";
            case 8:
                return "/Music/track9.mp3";
            case 9:
                return "/Music/track10.mp3";
            case 10:
                return "/Music/track11.mp3";
            case 11:
                return "/Music/track12.mp3";
            default:
                return "/Music/track13.mp3";
        }
    }

    private void createWarJet(Pane gamePane) {
        this.gamePane = gamePane;
        if (warJet == null){
            warJet = new WarJet();
        }
        warJetAnimation = new WarJetAnimation(this, warJet);
        warJetAnimation.play();
        gamePane.getChildren().add(warJet);
        //set focus on warJet:
        warJet.setFocusTraversable(true);

        warJet.setOnKeyPressed(e -> {
            if (player.playWithArrow()) {
                switch (e.getCode()) {
                    case UP:
                        warJetAnimation.upButton = true;
                        break;
                    case DOWN:
                        warJetAnimation.downButton = true;
                        break;
                    case LEFT:
                        warJetAnimation.leftButton = true;
                        break;
                    case RIGHT:
                        warJetAnimation.rightButton = true;
                        break;
                }
            } else {
                switch (e.getCode()) {
                    case W:
                        warJetAnimation.upButton = true;
                        break;
                    case S:
                        warJetAnimation.downButton = true;
                        break;
                    case A:
                        warJetAnimation.leftButton = true;
                        break;
                    case D:
                        warJetAnimation.rightButton = true;
                        break;
                }
            }
            switch (e.getCode()) {
                case SPACE:
                    warJetAnimation.shoot();
                    break;
                case R:
                    warJetAnimation.dropAtomicBomb();
                    break;
                case E:
                    warJetAnimation.dropClusterBomb();
                    break;
                case P:
                    //go to next wave:
                    waveEnd();
                    break;
                case G:
                    //add atomic bomb:
                    addAtomicBombForce();
                    break;
                case CONTROL:
                    //add cluster bomb:
                    addClusterBombForce();
                    break;
                case TAB:
                    //freeze mode:
                    freezeActivated();
                    break;
                case H:
                    //reHill:
                    ReHill();
                    break;
                case T:
                    releazeTank();
                    break;
            }
        });
        warJet.setOnKeyReleased(e -> {
            if (player.playWithArrow()) {
                switch (e.getCode()) {
                    case UP:
                        warJetAnimation.upButton = false;
                        break;
                    case DOWN:
                        warJetAnimation.downButton = false;
                        break;
                    case LEFT:
                        warJetAnimation.leftButton = false;
                        break;
                    case RIGHT:
                        warJetAnimation.rightButton = false;
                        break;
                }
            } else {
                switch (e.getCode()) {
                    case W:
                        warJetAnimation.upButton = false;
                        break;
                    case S:
                        warJetAnimation.downButton = false;
                        break;
                    case A:
                        warJetAnimation.leftButton = false;
                        break;
                    case D:
                        warJetAnimation.rightButton = false;
                        break;
                }
            }
        });
    }

    private void releazeTank() {
        if (isReleazedTankRecently) return;
        Tank tank = currentWave.generateTank(player.getDifficulty());
        TankAnimation tankAnimation = currentWave.generateTankAnimation(tank);
        currentWave.animations.add(tankAnimation);
        tankAnimation.play();
        isReleazedTankRecently = false;
    }

    private void ReHill() {
        gameLauncher.isReHilled = true;
    }

    public void gamePlay() {
        if (!isGameStopped) return;
        mediaPlayer.play();
        gameLauncher.isReHilled = false;

        //stop all animations:
        for (Transition animation : currentWave.animations) {
            animation.play();
        }
        warJet.setX(0);
        warJet.setY(100);
        warJet.setRotate(0);
        warJetAnimation.angle = 0;
        warJetAnimation.play();
        warJet.HP = 100;
        gameLauncher.HPBar.setProgress(1);
        Timeline timeline = new Timeline();
        i = 0;
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), event -> {
            if (i%2 == 0) {
                warJet.setOpacity(0.5);
            }else {
                warJet.setOpacity(1);
            }
            i++;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(8);
        timeline.play();
        currentWave.timeline.play();
    }

    private void freezeActivated() {
        if (boost < 100) return;
        gameLauncher.useBoost();
        gameLauncher.freezeMode();

        for (Transition animation : currentWave.animations) {
            animation.pause();
        }
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {
            for (Transition animation : currentWave.animations) {
                animation.play();
            }
        });
        pause.play();
    }

    public void addClusterBombForce() {
        clusterBombs += 1;
        warJet.remainingClusterBombs += 1;
        gameLauncher.setClusterBombs(clusterBombs);

    }

    public void addAtomicBombForce() {
        atomicBombs += 1;
        warJet.remainingAtomicBombs += 1;
        gameLauncher.setAtomicBombs(atomicBombs);
    }

    public void waveEnd() {
        //clear last wave structures but warJet
        Timeline timeline = new Timeline();
        gameLauncher.nextWave(waveNumber, getAccuracy());
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(4), event -> {
            gamePane.getChildren().clear();
            gamePane.getChildren().add(warJet);

            //new wave:
            waveNumber++;
            startNewWave(gamePane);
            gameLauncher.nextWaveEnd();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();


    }

    private void startNewWave(Pane gamePane) {
        if (waveNumber != 1){
//            currentWave.timeline.stop();
//            for (Transition animation : currentWave.animations) {
//                animation.stop();
//            }
            System.out.println("whyy");
        }
        setBackGroundImage();
        switch (waveNumber) {
            case 1:
                System.out.println("Wave 1 started");
                currentWave = wave1;
                break;
            case 2:
                System.out.println("Wave 2 started");
                currentWave = wave2;
                break;
            case 3:
                System.out.println("Wave 3 started");
                currentWave = wave3;
                break;
            case 4:
                waveNumber = 3;
                System.out.println("Game Over");
                gameLauncher.winGame();
                return;
        }
        currentWave.startWave(gamePane);
        System.out.println("wave started");
    }

    private void setBackGroundImage() {
        gameLauncher.setBackgroundImage(waveNumber);
    }


    private boolean isGameOver() {
        return false;
    }

    public void updateHP() {
        gameLauncher.HPBar.setProgress((double) warJet.HP / 100);
        checkGameOver();
    }

    private void checkGameOver() {
        if (warJet.HP <= 0) {
            System.out.println("Game Over");
            gameLauncher.gameOver();
        }
    }

    public void addKill(int score) {
        gameLauncher.addKill(score);
        kills += score;
    }

    public void dropAtomicBomb() {
        atomicBombs -= 1;
        warJet.remainingAtomicBombs -= 1;
        gameLauncher.setAtomicBombs(atomicBombs);
    }

    public void dropClusterBomb() {
        clusterBombs -= 1;
        warJet.remainingClusterBombs -= 1;
        gameLauncher.setClusterBombs(clusterBombs);
    }

    public void addAtomicBomb(double x, double y) {
        gameLauncher.addAtomicBomb(x, y);
//        atomicBombs += 1;
//        warJet.remainingAtomicBombs += 1;
//        gameLauncher.setAtomicBombs(atomicBombs);
    }

    public void addClusterBomb(double x, double y) {
        gameLauncher.addClusterBomb(x, y);
//        clusterBombs += 1;
//        warJet.remainingClusterBombs += 1;
//        gameLauncher.setClusterBombs(clusterBombs);
    }

    public void addBoost(int boost) {
        this.boost += boost;
        gameLauncher.setBoost();
    }

    public void checkForGameOver() {
        gameLauncher.setHP();
        if (warJet.HP <= 0) {
            System.out.println("Game Over");
            gameLauncher.gameOver();
        }
    }

    public void pause() {
        for (Transition animation : currentWave.animations) {
            animation.pause();
        }
        warJetAnimation.pause();
        //dont generate new targets:
        currentWave.timeline.stop();
    }

    public void unPause() {
        for (Transition animation : currentWave.animations) {
            animation.play();
        }
        warJetAnimation.play();
        currentWave.timeline.play();
    }

    public boolean isMusicPlaying() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void nextSong() {
        mediaPlayer.stop();
        playMusic();
    }

    public void gameStop() {
        mediaPlayer.stop();

        //stop all animations:
        for (Transition animation : currentWave.animations) {
            animation.stop();
        }
        warJetAnimation.stop();
        currentWave.timeline.stop();
    }

    public void addHits() {
        hits++;
    }

    public void addShoots() {
        shoots++;
    }

    public int getWave() {
        return waveNumber;
    }

    public int getKills() {
        return kills;
    }

    public int getAccuracy(){
        return (int) ((double) hits / shoots * 100);
    }


    public int getShoots() {
        return shoots;
    }

    public int getHits() {
        return hits;
    }

    public void saveGame() {
        SavedGame savedGame = new SavedGame();
        savedGame.waveNumber = waveNumber;
        savedGame.kills = kills;
        savedGame.shoots = shoots;
        savedGame.hits = hits;
        savedGame.accuracy = getAccuracy();
        savedGame.jetHealth = warJet.HP;
        savedGame.boost = boost;
        savedGame.AtomicBombCount = atomicBombs;
        savedGame.ClusterBombCount = clusterBombs;
        savedGame.warJet = new SavedWarJet();
        savedGame.warJet.jetX = warJet.getX();
        savedGame.warJet.jetY = warJet.getY();
        savedGame.warJet.speed = warJet.speed;
        savedGame.warJet.isRotated = warJet.isRotated;
        savedGame.warJet.angle = warJet.angle;
        savedGame.savedWave = new SavedWave();
        savedGame.savedWave.waveNumber = waveNumber;
        savedGame.savedWave.kills = kills;
        savedGame.savedWave.shoots = shoots;
        savedGame.savedWave.hits = hits;
        savedGame.savedWave.AtomicBombCount = atomicBombs;
        savedGame.savedWave.ClusterBombCount = clusterBombs;
        savedGame.savedWave.savedTargets = currentWave.saveTargets();
        savedGame.savedWave.savedGameField = currentWave.saveGameField();
        GameLauncherController.saveGame(savedGame);
    }

    public void loadFromSavedGame(SavedGame savedGame) {
        waveNumber = savedGame.waveNumber;
        kills = savedGame.kills;
        shoots = savedGame.shoots;
        hits = savedGame.hits;
        boost = savedGame.boost;
        atomicBombs = savedGame.AtomicBombCount;
        clusterBombs = savedGame.ClusterBombCount;
        WarJet warJet = new WarJet();
        warJet.HP = savedGame.jetHealth;
        warJet.setX(savedGame.warJet.jetX);
        warJet.setY(savedGame.warJet.jetY);
        warJet.setRotate(savedGame.warJet.angle);
        warJet.angle = savedGame.warJet.angle;
        warJet.speed = savedGame.warJet.speed;
        warJet.remainingAtomicBombs = atomicBombs;
        warJet.remainingClusterBombs = clusterBombs;
        this.warJet = warJet;
        gameLauncher.setAtomicBombs(atomicBombs);
        gameLauncher.setClusterBombs(clusterBombs);
        gameLauncher.setBoost();
        gameLauncher.setHP();
        gameLauncher.setKills();
        gameLauncher.setWave(waveNumber);
        GameField savedGameField = GameLauncherController.loadGameField(savedGame);
        Wave newWave = new Wave(this, savedGameField, waveNumber, player.getDifficulty());
        switch (waveNumber) {
            case 1:
                wave1 = newWave;
                break;
            case 2:
                wave2 = newWave;
                break;
            case 3:
                wave3 = newWave;
                break;
        }
        currentWave = newWave;
        gamePane = gameLauncher.gamePane;
        currentWave.loadTargets(savedGame.savedWave.savedTargets, savedGameField);

        gameLauncher.setBackgroundImage(waveNumber);
    }
}
