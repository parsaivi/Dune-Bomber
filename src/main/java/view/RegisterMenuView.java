package view;

import controller.MainMenuController;
import controller.RegisterController;
import model.App;
import model.IO.Errors;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class RegisterMenuView {
    public TextField signUpUsername;
    public TextField signUpPassword;
    public Button signInButton2nd;
    public Button signUpButton2nd;
    public TextField signInUsername;
    public TextField signInPassword;
    private Stage stage;
    private Scene RegisterScene;
    public Text errorText;
    public Button signInButton;
    public Button signUpButton;
    public Button guestButton;
    public Button exitButton;

    public RegisterMenuView() {
        //
    }

    public static void error(Errors errors) {
        switch (errors) {
            case INVALID_USERNAME:
                showInvalidUsername();
                break;
            case INVALID_PASSWORD:
                showInvalidPassword();
                break;
            case REGISTERED:
                showRegistered();
                break;
            case USERNAME_NOT_FOUND:
                showUsernameNotFound();
                break;
            case WRONG_PASSWORD:
                showWrongPassword();
                break;
            case LOGGED_IN:
                showLoggedIn();
                break;
            case USERNAME_ALREADY_TAKEN:
                showUsernameAlreadyTaken();
                break;
        }
    }

    private static void showUsernameAlreadyTaken() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Username Already Taken");
        alert.setContentText("Username is already taken.");
        alert.showAndWait();
    }

    private static void showLoggedIn() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Logged In");
        alert.setContentText("You have successfully logged in.");
        alert.showAndWait();
    }

    private static void showWrongPassword() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Wrong Password");
        alert.setContentText("Password is incorrect.");
        alert.showAndWait();
    }

    private static void showUsernameNotFound() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Username Not Found");
        alert.setContentText("Username does not exist.");
        alert.showAndWait();
    }

    private static void showInvalidUsername() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Username");
        alert.setContentText("Username must contain only letters and numbers.");
        alert.showAndWait();
    }

    private static void showInvalidPassword() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Password");
        alert.setContentText("Password must be at least 8 characters long and not the same as the username.");
        alert.showAndWait();
    }

    private static void showRegistered() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Registered");
        alert.setContentText("You have successfully registered.");
        alert.showAndWait();
    }


    public void signInDragged() {
        errorText.setText("Do you want to sign in?");
    }

    public void signUpDragged() {
        errorText.setText("Do you want to sign up?");
    }

    public void guestDragged() {
        errorText.setText("Do you want to continue as a guest?");
    }

    public void exitDragged() {
        errorText.setText("Do you want to exit?");
    }

    public void dragOut() {
        errorText.setText("Welcome to the Register Menu!");

    }

    public void signInButtonClicked(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SignIn.fxml"));
            Parent root = loader.load();
            loader.setController(this);

            // Create a new scene with the new FXML file
            Scene scene = new Scene(root);

            // Get the current stage
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guestButtonAction() {
        //
    }

    public void signUpButtonClicked(MouseEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SignUp.fxml"));
            Parent root = loader.load();
            loader.setController(this);

            // Create a new scene with the new FXML file
            Scene scene = new Scene(root);

            // Get the current stage
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void guestButtonClicked(MouseEvent event) {
        RegisterController.guest();
        mainMenuProgress(event);
    }

    public void signUpButtonAction(MouseEvent event) {
        String username = signUpUsername.getText();
        String password = signUpPassword.getText();
        Errors output = RegisterController.signUp(username, password);
        error(output);
        switch (output) {
            case USERNAME_ALREADY_TAKEN:
                signUpUsername.clear();
                signUpPassword.clear();
                break;
            case REGISTERED:
                signUpUsername.clear();
                signUpPassword.clear();
                mainMenuProgress(event);
                break;
            case INVALID_USERNAME:
                signUpUsername.clear();
                signUpPassword.clear();
                break;
            case INVALID_PASSWORD:
                signUpPassword.clear();
                break;
        }
    }

    private void mainMenuProgress(MouseEvent event) {
        // 1. انجام عملیات ثبت نام (جمع‌آوری اطلاعات کاربر و ذخیره در پایگاه داده یا هر کار دیگری)

        // 2. بارگذاری صفحه لودینگ
        try {
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/FXML/Loading.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScene = new Scene(loadingRoot, 800, 600);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loadingScene);
            // 3. لود کردن داده‌های مورد نیاز برای صفحه منوی اصلی در یک رشته جدید
            Thread loadDataThread = new Thread(() -> {
                // لود اطلاعات کاربر، تصویر پروفایل، و دیگر داده‌ها

                // 4. بارگذاری صفحه منوی اصلی و انتقال به آن
                Platform.runLater(() -> {
                    try {
                        FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("/FXML/MainMenu.fxml"));
                        Parent mainMenuRoot = mainMenuLoader.load();
                        MainMenuView mainMenuView = mainMenuLoader.getController();
                        // تنظیم اطلاعات کاربر و تصویر پروفایل در کنترلر منوی اصلی
                        MainMenuController.setUserInfo(mainMenuView);
                        Scene mainMenuScene = new Scene(mainMenuRoot, 800, 600);
                        mainMenuView.setScene(mainMenuScene);
                        Thread.sleep(500);
                        stage.setScene(mainMenuScene);
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

    public void signInButtonAction(MouseEvent event) {
        String username = signInUsername.getText();
        String password = signInPassword.getText();
        Errors output = RegisterController.signIn(username, password);
        error(output);
        switch (output) {
            case LOGGED_IN:
                signInUsername.clear();
                signInPassword.clear();
                mainMenuProgress(event);
                break;
            case USERNAME_NOT_FOUND:
                signInUsername.clear();
                signInPassword.clear();
                break;
            case INVALID_PASSWORD:
                signInPassword.clear();
                break;
        }
    }


    public void exitButtonClicked() {
        App.saveUsers();
        //exit the program:
        System.exit(0);
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/RegisterMenu.fxml"));
            Parent root = loader.load();
            loader.setController(this);
            Scene scene = new Scene(root);
            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backDragged(MouseEvent mouseEvent) {
        //
    }

    public void setScene(Scene scene) {
        RegisterScene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
