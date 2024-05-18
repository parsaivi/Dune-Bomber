package view;

import controller.GameLauncherController;
import controller.animations.BonusAnimation;
import model.App;
import model.Game;
import model.gameModels.bonus.AtomicBonus;
import model.gameModels.bonus.ClusterBonus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class GameLauncher extends Application {
    public Game game;
    public MainMenuView mainMenuView;
    public MediaPlayer mediaPlayer;
    public Pane gamePane;
    public Scene scene;
    public ImageView backImage = new ImageView();
    public Label errorLabel;
    public boolean isReHilled = false;
    List<Scene> scenes;
    public ProgressBar HPBar = new ProgressBar();
    public ProgressBar BoostBar = new ProgressBar();
    public Label kills = new Label();
    public Label waveNumberLabel = new Label();
    public Label atomicBombs = new Label();
    public Label clusterBombs = new Label();
    private PauseView pauseView;
    private GameOverView gameOverView;
    private Parent root;
    private int i;
//    public Label kills;
//    public Label waveNumber;

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Game.fxml"));
            Parent root = loader.load();
            GameLauncher controller = loader.getController();
            controller.setRoot(root);

            FXMLLoader pause = new FXMLLoader(getClass().getResource("/FXML/Pause.fxml"));
            Parent pauseRoot = pause.load();
            pauseRoot.setVisible(false);
            PauseView pauseController = pause.getController();
            pauseController.setPauseRoot(pauseRoot);

            FXMLLoader gameOver = new FXMLLoader(getClass().getResource("/FXML/GameOver.fxml"));
            Parent gameOverRoot = gameOver.load();
            gameOverRoot.setVisible(false);
            GameOverView gameOverController = gameOver.getController();
            gameOverController.setGameOverRoot(gameOverRoot);

            GameLauncherController.root = gamePane;
            Font.loadFont(getClass().getResourceAsStream("/fonts/EuroStyle.ttf"), 14);

            pauseController.setGameLauncher(controller);
            controller.setPauseController(pauseController);
            gameOverController.setGameLauncher(controller);
            controller.setGameOverController(gameOverController);

            StackPane sceneHolder = new StackPane();
            sceneHolder.getChildren().add(root);
            sceneHolder.getChildren().add(pauseRoot);
            sceneHolder.getChildren().add(gameOverRoot);
            scene = new Scene(sceneHolder);

            controller.mainMenuView = App.getMainMenuView();

            if (App.getLoggedInUser().getGameSettings().isBWColorize()) turnSceneToBlackAndWhite(scene);

            setLabels();

            game = GameLauncherController.launchGame(this);
            game.gameLauncher = controller;
            controller.setGame(game);

            game.startNewGame(gamePane);

            primaryStage.setTitle("Game Launcher");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGameOverController(GameOverView gameOverController) {
        gameOverView = gameOverController;
    }

    private void setRoot(Parent root) {
        this.root = root;
    }

    private void setPauseController(PauseView pauseController) {
        pauseView = pauseController;
    }

    private void setLabels() {
        gamePane = (Pane) scene.lookup("#gamePane");
        HPBar = (ProgressBar) scene.lookup("#HPBar");
        BoostBar = (ProgressBar) scene.lookup("#BoostBar");
        kills = (Label) scene.lookup("#kills");
        waveNumberLabel = (Label) scene.lookup("#waveNumberLabel");
        atomicBombs = (Label) scene.lookup("#atomicBombs");
        clusterBombs = (Label) scene.lookup("#clusterBombs");
        errorLabel = (Label) scene.lookup("#errorLabel");

        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
        errorLabel.setStyle("-fx-font-size: 20");
        errorLabel.setAlignment(Pos.CENTER_RIGHT);
        errorLabel.setBackground(Background.fill(javafx.scene.paint.Color.rgb(32, 22, 17)));

        HPBar.setProgress(1);
        BoostBar.setProgress(0);
        kills.setText("0");
        waveNumberLabel.setText("1");
        atomicBombs.setText("0");
        clusterBombs.setText("0");
    }

    public void addKill(int kill) {
        kills.setText(String.valueOf(Integer.parseInt(kills.getText()) + kill));
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setBackgroundImage(int waveNumber) {
        switch (waveNumber) {
            case 1:
                waveNumberLabel.setText("1");
                backImage.setImage(App.getBackgroundPath(1));
                break;
            case 2:
                waveNumberLabel.setText("2");
                backImage.setImage(App.getBackgroundPath(2));
                break;
            case 3:
                waveNumberLabel.setText("3");
                backImage.setImage(App.getBackgroundPath(3));
                break;
        }
    }

    public void setAtomicBombs(int atomicBombs) {
        this.atomicBombs.setText(String.valueOf(atomicBombs));
    }

    public void setClusterBombs(int clusterBombs) {
        this.clusterBombs.setText(String.valueOf(clusterBombs));
    }

    public void turnSceneToBlackAndWhite(Scene scene) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1); // -1 means full desaturation which leads to a black and white image

        scene.getRoot().setEffect(colorAdjust);
    }


    public void addAtomicBomb(double x, double y) {
        AtomicBonus atomicBonus = new AtomicBonus(game, x, y);
        BonusAnimation atomicBonusAnimation = new BonusAnimation(atomicBonus);

        gamePane.getChildren().add(atomicBonus);
        atomicBonusAnimation.play();
    }

    public void addClusterBomb(double x, double y) {
        ClusterBonus clusterBonus = new ClusterBonus(game, x, y);
        BonusAnimation atomicBonusAnimation = new BonusAnimation(clusterBonus);

        gamePane.getChildren().add(clusterBonus);
        atomicBonusAnimation.play();
    }

    public void setBoost() {
        BoostBar.setProgress((double) game.boost / 100);
    }

    public void useBoost() {
        BoostBar.setProgress(0);
        game.boost = 0;
    }

    public void freezeMode() {
        //set an picture on top of scene:
        ImageView freezeImage = new ImageView(App.getFreezeImage());
        freezeImage.setFitWidth(800);
        freezeImage.setFitHeight(600);
        freezeImage.setOpacity(0.1);
        Timeline timeline = new Timeline();
        gamePane.getChildren().add(freezeImage);
        Media freezeSound = new Media(getClass().getResource(App.getFreezeSound()).toExternalForm());
        mediaPlayer = new MediaPlayer(freezeSound);
        mediaPlayer.play();
        //set timer for 5 second:
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.seconds(5), e -> {
            gamePane.getChildren().remove(freezeImage);
            mediaPlayer.stop();
            Media unFreezeSound = new Media(getClass().getResource(App.getUnfreezeSound()).toExternalForm());
            mediaPlayer = new MediaPlayer(unFreezeSound);
            mediaPlayer.play();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void setHP() {
        if (game.warJet.HP <= 0) {
            HPBar.setProgress(0);
            return;
        }
        HPBar.setProgress(game.warJet.HP / 100);
    }

    public void playMigAlert() {
        migAlertText();
        Media migAlert = new Media(getClass().getResource(App.getMigAlert()).toExternalForm());
        mediaPlayer = new MediaPlayer(migAlert);
        mediaPlayer.play();
    }

    private void migAlertText() {
        errorLabel.setTextFill(javafx.scene.paint.Color.RED);
        errorLabel.setStyle("-fx-font-size: 20");
        errorLabel.setAlignment(Pos.CENTER_RIGHT);
        errorLabel.setBackground(Background.fill(javafx.scene.paint.Color.rgb(32, 22, 17)));
        Timeline timeline = new Timeline();
        //set key frame for text
        i = 0;
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(200), e -> {
            printMigAlert(i);
            i++;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(10);
        timeline.play();
    }

    private void printMigAlert(int i) {
        switch (i) {
            case 0:
                errorLabel.setText("M");
                break;
            case 1:
                errorLabel.setText("MI");
                break;
            case 2:
                errorLabel.setText("MIG");
                break;
            case 3:
                errorLabel.setText("MIG ");
                break;
            case 4:
                errorLabel.setText("MIG A");
                break;
            case 5:
                errorLabel.setText("MIG AL");
                break;
            case 6:
                errorLabel.setText("MIG ALE");
                break;
            case 7:
                errorLabel.setText("MIG ALER");
                break;
            case 8:
                errorLabel.setText("MIG ALERT");
                break;
            case 9:
                errorLabel.setText("MIG ALERT!");
                break;
        }
    }

    public void stopMigAlert() {
        Timeline timeline = new Timeline();
        //set key frame for text
        i = 0;
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(200), e -> {
            printOutMigAlert(i);
            i++;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(11);
        timeline.play();
    }

    private void printOutMigAlert(int i) {
        switch (i) {
            case 0:
                errorLabel.setText("MIG ALERT");
                break;
            case 1:
                errorLabel.setText("MIG ALER");
                break;
            case 2:
                errorLabel.setText("MIG ALE");
                break;
            case 3:
                errorLabel.setText("MIG AL");
                break;
            case 4:
                errorLabel.setText("MIG A");
                break;
            case 5:
                errorLabel.setText("MIG ");
                break;
            case 6:
                errorLabel.setText("MIG");
                break;
            case 7:
                errorLabel.setText("MI");
                break;
            case 8:
                errorLabel.setText("M");
                break;
            case 9:
                errorLabel.setText("");
                break;
        }
    }

    public void gameOver() {

        errorLabel.setStyle("-fx-font-size: 20");
        errorLabel.setAlignment(Pos.CENTER_RIGHT);
        errorLabel.setBackground(Background.fill(javafx.scene.paint.Color.rgb(32, 22, 17)));
        setHP();
        game.gameStop();
        game.isGameStopped = true;
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(6), e -> {
            if (isReHilled) {
                game.gamePlay();
                errorLabel.setText("");
                System.out.println("Game is resumed!");
                return;
            }
            gameOverView.setData(game.getWave(), game.getKills(), game.getAccuracy());
            game.player.setData(game.getWave(), game.getKills(), game.getAccuracy(), game.getShoots(), game.getHits());
            gameOverView.setLose();
            gameOverView.visibleGameOverRoot();
            blurScene();
        });
        i = 0;
        Timeline timeline2 = new Timeline();
        KeyFrame counter = new KeyFrame(Duration.seconds(1), e -> {
            errorLabel.setText("Game Over! " + (5 - i));
            i++;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline2.getKeyFrames().add(counter);
        timeline.setCycleCount(1);
        timeline2.setCycleCount(5);
        timeline.play();
        timeline2.play();

    }

    public void pauseClicked(MouseEvent event) {
        game.pause();
        pauseView.visiblePauseRoot();
        blurScene();
    }

    private void blurScene() {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(5);
        root.setEffect(blur);
    }

    public void unPause() {
        i = 1;

        errorLabel.setStyle("-fx-font-size: 12");
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> {
            switchIGamePause();
            i++;
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(6);
        timeline.play();
        pauseView.invisiblePauseRoot();
        root.setEffect(null);
    }

    private void switchIGamePause() {
        if (i == 1) {
            errorLabel.setText("Game will resume in 3");
        }
        if (i == 2) {
            errorLabel.setText("Game will resume in 2");
        }
        if (i == 3) {
            errorLabel.setText("Game will resume in 1");
        }
        if (i == 4) {
            errorLabel.setText("Game Started!");
        }
        if (i == 5) {
            errorLabel.setText("");
            errorLabel.setStyle("-fx-font-size: 20");
            game.unPause();
        }
    }

    public boolean isMusicPlaying() {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void changeMusic() {
        if (game.isMusicPlaying()) {
            game.mediaPlayer.stop();
        } else {
            game.mediaPlayer.play();
        }
    }

    public void nextSong() {
        game.nextSong();
    }

    public void previousSong() {
        game.nextSong();
    }

    public void saveAndClose() {
        game.saveGame();
        game.gameStop();
        backToMainMenu();
    }

    public void exit() {// go to this.mainMenuView:
        backToMainMenu();
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void winGame() {
        game.gameStop();
        gameOverView.setData(game.getWave(), game.getKills(), game.getAccuracy());
        gameOverView.setWin();
        gameOverView.visibleGameOverRoot();
        blurScene();
    }

    public void backToMainMenu() {
        mainMenuView.setScene(mainMenuView.getScene());
        Stage stage = (Stage) gamePane.getScene().getWindow();
        stage.setScene(mainMenuView.getScene());
        stage.show();
    }

    public void nextWave(int waveNumber, int accuracy) {
        errorLabel.setStyle("-fx-font-size: 14");
        errorLabel.setAlignment(Pos.CENTER_RIGHT);
        errorLabel.setBackground(Background.fill(javafx.scene.paint.Color.rgb(32, 22, 17)));
        errorLabel.setText("Wave " + waveNumber + " Accuray: " + accuracy + "%");
    }

    public void nextWaveEnd() {
        errorLabel.setText("");
    }

    public void setKills() {
        kills.setText(String.valueOf(game.getKills()));
    }

    public void setWave(int waveNumber) {
        waveNumberLabel.setText(String.valueOf(waveNumber));
    }

}
