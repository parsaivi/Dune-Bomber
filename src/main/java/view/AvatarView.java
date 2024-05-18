package view;

import controller.MainMenuController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AvatarView {
    public Button backButton;

    public Button uploadButton;
    public Label dragDrop;
    private MainMenuView mainMenuView;
    private ProfileView profileView;
    public Pane dropPane;
    public ImageView avatar1;
    public ImageView avatar2;
    public ImageView avatar3;
    public ImageView avatar4;
    public ImageView avatar5;

    public void backButtonClicked(MouseEvent event) {
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
                        Thread.sleep(1000);
                        profileView.profileImage.setImage(MainMenuController.getProfilePicture());
                        Scene scene = profileView.getScene();
                        stage.setScene(scene);
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


    public void avatar1Click(MouseEvent mouseEvent) {
        MainMenuController.setProfilePicture(7);
        profileView.setPictureChanged(true);
    }

    public void avatar2Click(MouseEvent mouseEvent) {
        MainMenuController.setProfilePicture(2);
        profileView.setPictureChanged(true);

    }

    public void avatar3Click(MouseEvent mouseEvent) {
        MainMenuController.setProfilePicture(9);
        profileView.setPictureChanged(true);
    }

    public void avatar4Click(MouseEvent mouseEvent) {
        MainMenuController.setProfilePicture(10);
        profileView.setPictureChanged(true);
    }

    public void avatar5Click(MouseEvent mouseEvent) {
        MainMenuController.setProfilePicture(1);
        profileView.setPictureChanged(true);
    }


    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }

    public void setProfileView(ProfileView profileView) {
        this.profileView = profileView;
    }

    public void uploadClicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Avatar Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            MainMenuController.setProfilePicture(selectedFile);
            profileView.setPictureChanged(true);
        }

    }

    public void dragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    public void dragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            File file = db.getFiles().get(0);
            // Use the file as you need
            // For example, set it as a profile picture
            MainMenuController.setProfilePicture(file);
            profileView.setPictureChanged(true);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void dragExited(DragEvent dragEvent) {
        dragDrop.setText("Drag and Drop Image Here");
    }

    public void dragEntered(DragEvent dragEvent) {
        dragDrop.setText("Drop Image");
    }
}
