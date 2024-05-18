package view;

import controller.MainMenuController;
import model.enums.GameDifficulty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SettingView {
    private MainMenuView mainMenuView;
    public ChoiceBox difficultyChoice;
    public CheckBox muteGameAudio;
    public CheckBox muteMusic;
    public CheckBox blackAndWhite;
    public Button remapControls;
    public Button backButton;

    public void backButtonClicked(MouseEvent mouseEvent) {
        try {
            Scene mainScene = mainMenuView.getScene();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(mainScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void remapControlClicked(MouseEvent mouseEvent) {
        boolean isArrowKeys = MainMenuController.getControls();
        MainMenuController.changeControls();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Controls");
        alert.setHeaderText("controllers changed");
        if (isArrowKeys) {
            alert.setContentText("controllers changed!\nnow you can use W-A-S-D system");
        } else {
            alert.setContentText("controllers changed!\nnow you can use up-left-down-right system");
        }
        alert.showAndWait();
    }

    public void initialize() {
        difficultyChoice.getItems().setAll("EASY", "MEDIUM", "HARD");
        difficultyChoice.setValue(GameDifficulty.getDifficulty(MainMenuController.getDifficulty()));
        muteGameAudio.setSelected(MainMenuController.isSoundMuted());
        muteMusic.setSelected(MainMenuController.isMusicMuted());
        blackAndWhite.setSelected(MainMenuController.isBWColorize());
    }

    public void difficultyChoiceClicked(MouseEvent mouseEvent) {
        GameDifficulty difficulty = GameDifficulty.getDifficulty(difficultyChoice.getValue().toString());
        MainMenuController.setDifficulty(difficulty);
    }

    public void muteGameAudioClicked(MouseEvent mouseEvent) {
        if (muteGameAudio.isSelected()){
            MainMenuController.muteGameAudio();
        }else {
            MainMenuController.unmuteGameAudio();
        }
    }

    public void muteMusicClicked(MouseEvent mouseEvent) {
        if (muteMusic.isSelected()){
            MainMenuController.muteMusic();
        }else {
            MainMenuController.unmuteMusic();
        }
    }

    public void blackAndWhiteClicked(MouseEvent mouseEvent) {
        if (blackAndWhite.isSelected()){
            MainMenuController.setBWColorize();
        }else {
            MainMenuController.unsetBWColorize();
        }
    }




}
