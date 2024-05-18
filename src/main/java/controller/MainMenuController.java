package controller;

import model.App;
import model.enums.GameDifficulty;
import model.IO.Errors;
import model.User;
import view.MainMenuView;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class MainMenuController {
    public static void setUserInfo(MainMenuView mainMenuView) {
        User user = App.getLoggedInUser();
        //set username and profile picture on the scene:
        mainMenuView.username.setText(user.getUserName());
        mainMenuView.username.setAlignment(javafx.geometry.Pos.CENTER);
        mainMenuView.avatarImageView.setImage(user.getProfilePicture());
    }

    public static void exit() {
        App.setLoggedInUser(null);
    }

    public static boolean getControls() {
        return App.getUsingArrowKey();
    }

    public static void changeControls() {
        App.setUsingArrowKey(!App.getUsingArrowKey());
    }

    public static boolean isSoundMuted() {
        return App.getLoggedInUser().getGameSettings().isSoundMuted();
    }

    public static boolean isMusicMuted() {
        return App.getLoggedInUser().getGameSettings().isMusicMuted();
    }

    public static boolean isBWColorize() {
        return App.getLoggedInUser().getGameSettings().isBWColorize();
    }

    public static GameDifficulty getDifficulty() {
        return App.getLoggedInUser().getGameSettings().getDifficulty();
    }

    public static void setDifficulty(GameDifficulty difficulty) {
        App.getLoggedInUser().getGameSettings().setDificulty(difficulty);
        System.out.println(App.getLoggedInUser().getGameSettings().getDifficulty());
    }

    public static void muteGameAudio() {
        App.getLoggedInUser().getGameSettings().setSoundMuted(true);
    }

    public static void muteMusic() {
        App.getLoggedInUser().getGameSettings().setMusicMuted(true);
    }

    public static void setBWColorize() {
        App.getLoggedInUser().getGameSettings().setBWColorize(true);
    }

    public static void unmuteGameAudio() {
        App.getLoggedInUser().getGameSettings().setSoundMuted(false);
    }

    public static void unmuteMusic() {
        App.getLoggedInUser().getGameSettings().setMusicMuted(false);
    }

    public static void unsetBWColorize() {
        App.getLoggedInUser().getGameSettings().setBWColorize(false);
    }

    public static Errors changeUserInfo(String text, String text1, boolean picChanged) {
        return App.changeUserInfo(text, text1, picChanged);
    }

    public static void deleteAccount() {
        App.deleteAccount();
    }

    public static void logOut() {
        App.logOut();
    }

    public static void setProfilePicture(File selectedFile) {
        try {
            String path = copyFileToResources(selectedFile);
            System.out.println(path);
            App.setProfilePicture(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String copyFileToResources(File file) {
        try {
            // Check if the file is null or does not exist
            if (file == null || !file.exists()) {
                System.out.println("File is null or does not exist");
                return null;
            }

            // Check if there is a logged-in user
            User loggedInUser = App.getLoggedInUser();
            if (loggedInUser == null) {
                System.out.println("No user is logged in");
                return null;
            }

            // Define the target directory
            Path targetDirectory = Path.of("src/main/resources/Images/Profiles/");

            // Ensure the directory exists
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // Check if the directory exists
            if (!Files.exists(targetDirectory)) {
                System.out.println("Failed to create target directory");
                return null;
            }

            // Define the target path
            Path targetPath = targetDirectory.resolve(loggedInUser.getUserName() + ".jpg");

            // Copy the file
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // return path from resources:
            return "/Images/Profiles/" + loggedInUser.getUserName() + ".jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setProfilePicture(int image) {
        String path = "/Images/ProfilePictures/" + image + ".jpg";
        System.out.println(path);
        App.setProfilePicture(path);
    }


    public static Image getProfilePicture() {
        return App.getLoggedInUser().getProfilePicture();
    }

    public static User getLoggedInUser() {
        return App.getLoggedInUser();
    }
}
