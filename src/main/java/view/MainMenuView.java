package view;

import controller.GameLauncherController;
import controller.MainMenuController;
import model.App;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuView {

    public Scene getScene() {
        return scene;
    }


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private Scene scene;

    public ImageView avatarImageView;
    public Label username;

    public Button profileButton;
    public Button scoreBoardButton;
    public Button startGameButton;
    public Button exitButton;
    public Button continueButton;
    public Button settingButton;


    public void exitButtonClicked(MouseEvent event) {
        //go to register page:
        MainMenuController.exit();
        //go to register page:
        try {
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
                        FXMLLoader registerMenuloader = new FXMLLoader(getClass().getResource("/FXML/RegisterMenu.fxml"));
                        Parent registerMenuRoot = registerMenuloader.load();
                        RegisterMenuView registerMenuView = registerMenuloader.getController();
                        // تنظیم اطلاعات کاربر و تصویر پروفایل در کنترلر منوی اصلی
                        Scene registerMenuScene = new Scene(registerMenuRoot, 800, 600);
                        Thread.sleep(3000);
                        stage.setScene(registerMenuScene);
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


    public void settingButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("/FXML/Setting.fxml"));
            Parent settingRoot = settingLoader.load();
            Scene settingScene = new Scene(settingRoot, 800, 600);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            SettingView settingView = settingLoader.getController();
            settingView.setMainMenuView(this);
            stage.setScene(settingScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void continueButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loadingLoader = new FXMLLoader(getClass().getResource("/FXML/Loading.fxml"));
            Parent loadingRoot = loadingLoader.load();
            Scene loadingScene = new Scene(loadingRoot, 800, 600);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(loadingScene);
            // 3. لود کردن داده‌های مورد نیاز برای صفحه منوی اصلی در یک رشته جدید
            Thread loadDataThread = new Thread(() -> {
                // لود اطلاعات کاربر، تصویر پروفایل، و دیگر داده‌ها

                // 4. بارگذاری صفحه منوی اصلی و انتقال به آن
                Platform.runLater(() -> {
                    try {
                        GameLauncher gameLauncher = new GameLauncher();
                        App.setMainMenuView(this);
                        GameLauncherController.continueGame = true;
                        gameLauncher.start(stage);
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

    public void startButtonClicked(MouseEvent event) {
        try {
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
                        GameLauncherController.continueGame = false;
                        GameLauncher gameLauncher = new GameLauncher();
                        App.setMainMenuView(this);
                        gameLauncher.start(stage);
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

    public void scoreBoardButtonClicked(MouseEvent event) {
        try {
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
                        FXMLLoader scoreBoardLoader = new FXMLLoader(getClass().getResource("/FXML/ScoreBoard.fxml"));
                        Parent scoreBoardRoot = scoreBoardLoader.load();
                        Scene scoreBoardScene = new Scene(scoreBoardRoot, 800, 600);
                        ScoreBoardView scoreBoardView = scoreBoardLoader.getController();
                        //scoreBoardView.getData();
                        scoreBoardView.setMainMenuView(this);
                        Thread.sleep(3000);
                        stage.setScene(scoreBoardScene);
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

    public void profileButtonClicked(MouseEvent event) {
        try {
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
                        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
                        Parent profileRoot = profileLoader.load();
                        Scene profileScene = new Scene(profileRoot, 800, 600);
                        ProfileView profileView = profileLoader.getController();
                        profileView.setMainMenuView(this);
                        Thread.sleep(1000);
                        stage.setScene(profileScene);
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
}
