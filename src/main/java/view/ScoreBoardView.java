package view;

import model.App;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardView {

    public ChoiceBox sortChoice;
    public VBox scoreboardContainer;
    public Label thirdPlaceLabel;
    public Label secondPlaceLabel;
    public Label firstPlaceLabel;
    public Button backButton;
    private MainMenuView mainMenuView;

    public void initialize() {
        sortChoice.getItems().setAll("Score", "Kills", "pro-Kills", "accuracy");
        sortChoice.setValue("Score");
        sortChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateScoreBoard((String) newValue);
        });
        updateScoreBoard("Score");
    }

    private void updateScoreBoard(String sortBy) {
        List<User> allPlayers = getAllUsers();
        Comparator<User> comparator;

        switch (sortBy) {
            case "Score":
                //if the score is the same, the player with higher lastWave should be higher
                comparator = Comparator.comparing(User::getScore).thenComparing(User::getLastWave).reversed();
                break;
            case "Kills":
                comparator = Comparator.comparing(User::getKills).reversed();
                break;
            case "pro-Kills":
                comparator = Comparator.comparing(User::getProKills).reversed();
                break;
            case "accuracy":
                comparator = Comparator.comparing(User::getAccuracy).reversed();
                break;
            default:
                throw new IllegalArgumentException("Invalid sort option: " + sortBy);
        }

        List<User> sortedPlayers = allPlayers.stream()
                .sorted(comparator)
                .limit(10)
                .collect(Collectors.toList());

        scoreboardContainer.getChildren().clear();
        //1st player:
        if (sortedPlayers.size() > 0) {
            User firstPlayer = sortedPlayers.get(0);
            firstPlaceLabel.setText(firstPlayer.getName() + ": " + firstPlayer.getScore() + " points" + " " + firstPlayer.getKills() + " kills" + " " + firstPlayer.getProKills() + " pro-Kills" + " " + firstPlayer.getAccuracy() + " accuracy" + " " + firstPlayer.getLastWave() + " last wave");
        } else {
            firstPlaceLabel.setText("No player found");
        }
        //2nd player:
        if (sortedPlayers.size() > 1) {
            User secondPlayer = sortedPlayers.get(1);
            secondPlaceLabel.setText(secondPlayer.getName() + ": " + secondPlayer.getScore() + " points" + " " + secondPlayer.getKills() + " kills" + " " + secondPlayer.getProKills() + " pro-Kills" + " " + secondPlayer.getAccuracy() + " accuracy" + " " + secondPlayer.getLastWave() + " last wave");
        } else {
            secondPlaceLabel.setText("No player found");
        }
        //3rd player:
        if (sortedPlayers.size() > 2) {
            User thirdPlayer = sortedPlayers.get(2);
            thirdPlaceLabel.setText(thirdPlayer.getName() + ": " + thirdPlayer.getScore() + " points" + " " + thirdPlayer.getKills() + " kills" + " " + thirdPlayer.getProKills() + " pro-Kills" + " " + thirdPlayer.getAccuracy() + " accuracy" + " " + thirdPlayer.getLastWave() + " last wave");
        } else {
            thirdPlaceLabel.setText("No player found");
        }
        //the rest:
        for (User player : sortedPlayers.subList(3, sortedPlayers.size()))   {
            scoreboardContainer.getChildren().add(new Label(player.getName() + ": " + player.getScore() + " points" + " " + player.getKills() + " kills" + " " + player.getProKills() + " pro-Kills" + " " + player.getAccuracy() + " accuracy" + " " + player.getLastWave() + " last wave"));
        }
    }

    private List<User> getAllUsers() {
        return App.getAllUsers();
    }


    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void getData() {
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        mainMenuView.setScene(mainMenuView.getScene());
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(mainMenuView.getScene());
        stage.show();
    }
}
