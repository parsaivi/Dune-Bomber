package view;

import controller.MainMenuController;
import model.App;
import model.IO.Errors;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProfileView {
    private Scene scene;
    public Button backButton;
    public TextField usernameField;
    public TextField passwordField;
    public ImageView profileImage;
    private boolean isChanged = false;
    private boolean isSaved = false;
    private boolean pictureChanged = false;

    public void setPictureChanged(boolean pictureChanged) {
        this.pictureChanged = pictureChanged;
    }
    private MainMenuView mainMenuView;
    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void initialize(){
        usernameField.setText(App.getLoggedInUser().getUsername());
        passwordField.setText(App.getLoggedInUser().getPassword());
        profileImage.setImage(App.getLoggedInUser().getProfilePicture());
        scene = usernameField.getScene();
    }

    public void backButtonClicked(MouseEvent event){
        if (isChanged && !isSaved){
            //ask sure to leave without saving?
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save Changes");
            alert.setHeaderText("Do you want to save changes?");
            alert.setContentText("if you leave without saving, changes will be lost");
            alert.showAndWait();
            if (alert.getResult().getText().equals("OK")){
                return;
            }
        }
        if (isSaved){
            mainMenuView.username.setText(usernameField.getText());
            mainMenuView.username.setAlignment(javafx.geometry.Pos.CENTER);
            mainMenuView.avatarImageView.setImage(App.getLoggedInUser().getProfilePicture());
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuView.getScene());
    }

    public void saveClicked(MouseEvent mouseEvent) {
        Errors error = MainMenuController.changeUserInfo(usernameField.getText(), passwordField.getText(), pictureChanged);
        switch (error){
            case INVALID_USERNAME:
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Username");
                alert.setContentText("You must use English words");
                alert.showAndWait();
                break;
            case WEAK_PASSWORD:
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Error");
                alert1.setHeaderText("Invalid Password");
                alert1.setContentText("Password must be at least 8 characters long");
                alert1.showAndWait();
                break;
            case SAME_USERNAME:
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Invalid Username");
                alert2.setContentText("Username is already taken");
                alert2.showAndWait();
                break;
            case SAME_PASSWORD:
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Error");
                alert3.setHeaderText("Invalid Password");
                alert3.setContentText("Password is already taken");
                alert3.showAndWait();
                break;
            case USERNAME_ALREADY_TAKEN:
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Error");
                alert4.setHeaderText("Invalid Username");
                alert4.setContentText("Username is already taken");
                alert4.showAndWait();
                break;
            case INFO_CHANGED:
                isSaved = true;
                Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
                alert5.setTitle("Success");
                alert5.setHeaderText("Information Changed");
                alert5.setContentText("Your information has been changed successfully");
                alert5.showAndWait();
                break;
        }
    }


    public void deleteAccountClicked(MouseEvent mouseEvent) {
        boolean sure = areYouSure("Do you want to delete your account?\nThere is no way to undo this action");
        if (!sure) return;
        MainMenuController.deleteAccount();
        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/FXML/RegisterMenu.fxml"));
            Parent registerRoot = registerLoader.load();
            Scene registerScene = new Scene(registerRoot, 800, 600);
            stage.setScene(registerScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOutClicked(MouseEvent mouseEvent) {
        boolean sure = areYouSure("Do you want to logout?");
        if (!sure) return;
        MainMenuController.logOut();
        try {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/FXML/RegisterMenu.fxml"));
            Parent registerRoot = registerLoader.load();
            Scene registerScene = new Scene(registerRoot, 800, 600);
            stage.setScene(registerScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean areYouSure(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText("Are you sure?");
        alert.setContentText(message);
        alert.showAndWait();
        return alert.getResult().getText().equals("OK");
    }


    public void usernameChanged(KeyEvent event){
        changed();
    }


    public void passwordChanged(KeyEvent event){
        changed();
    }

    private void changed() {
        isChanged = true;
        isSaved = false;
    }

    public void avatarClicked(MouseEvent event) {
        try {
            scene = profileImage.getScene();
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/FXML/Loading.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScene = new Scene(loadingRoot, 800, 600);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loadingScene);
            // 3. لود کردن داده‌های مورد نیاز برای صفحه منوی اصلی در یک رشته جدید
            Thread loadDataThread = new Thread(() -> {
                // لود اطلاعات کاربر، تصویر پروفایل، و دیگر داده‌ها

                // 4. بارگذاری صفحه منوی اصلی و انتقال به آن
                Platform.runLater(() -> {
                    try {
                        FXMLLoader avatarLoader = new FXMLLoader(getClass().getResource("/FXML/Avatar.fxml"));
                        Parent avatarRoot = avatarLoader.load();
                        Scene avatarScene = new Scene(avatarRoot, 800, 600);
                        AvatarView avatarView = avatarLoader.getController();
                        avatarView.setProfileView(this);
                        avatarView.setMainMenuView(mainMenuView);
                        Thread.sleep(2000);
                        stage.setScene(avatarScene);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            loadDataThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
