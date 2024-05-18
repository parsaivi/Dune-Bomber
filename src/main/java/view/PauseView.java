package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PauseView {

    public Button musicButton;
    public Pane Pane;
    private GameLauncher gameLauncher;
    private Parent pauseRoot;
    private boolean alreadyGuided = false;
    public void setGameLauncher(GameLauncher controller) {
        this.gameLauncher = controller;
    }

    public void showPauseMenu() {

    }

    public void visiblePauseRoot() {
        pauseRoot.setVisible(true);
    }

    public void invisiblePauseRoot() {
        pauseRoot.setVisible(false);
    }

    public void setPauseRoot(Parent pauseRoot) {
        this.pauseRoot = pauseRoot;
    }

    public void resumeClicked(MouseEvent event) {
        gameLauncher.unPause();
    }


    public void saveAndCloseClicked(MouseEvent event) {
        gameLauncher.saveAndClose();
    }

    public void exitClicked(MouseEvent event) {
        gameLauncher.game.gameStop();
        gameLauncher.exit();
    }

    public void MusicClicked(MouseEvent event) {
        gameLauncher.changeMusic();
        if (gameLauncher.game.isMusicPlaying())
            musicButton.setText("Play Music");
        else
            musicButton.setText("Pause Music");
    }

    public void nextSong(MouseEvent event) {
        gameLauncher.nextSong();
    }

    public void previousSong(MouseEvent event) {
        gameLauncher.previousSong();
    }

    public void prevSong(MouseEvent event) {
        gameLauncher.nextSong();
    }

    public void GuidLine(MouseEvent event) {
        if (alreadyGuided) {
            Pane.getChildren().clear();
            alreadyGuided = false;
            return;
        }
        Image image;
        if (gameLauncher.game.player.playWithArrow())
            image = new Image("file:src/main/resources/Images/Game/Guid_a.png");
        else
            image = new Image("file:src/main/resources/Images/Game/Guid_w.png");
        ImageView imageView = new ImageView(image);
        Pane.getChildren().add(imageView);
        imageView.setFitHeight(600);
        imageView.setFitWidth(800);
        imageView.setLayoutX(0);
        imageView.setLayoutY(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(5000), event1 -> {
            Pane.getChildren().remove(imageView);
        });
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        alreadyGuided = true;
    }
}
