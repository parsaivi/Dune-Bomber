package model;

import controller.RegisterController;
import model.enums.GameDifficulty;
import model.IO.Errors;
import view.MainMenuView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static MainMenuView mainMenuView;
    private static ArrayList<User> allUsers = new ArrayList<>();
    private final static String userDataDir = "src/main/DB/Users.txt";
    private static List<String> randomProfiles = List.of(
            "/Images/ProfilePictures/1.jpg",
            "/Images/ProfilePictures/2.jpg",
            "/Images/ProfilePictures/3.jpg",
            "/Images/ProfilePictures/4.jpg",
            "/Images/ProfilePictures/5.jpg",
            "/Images/ProfilePictures/6.jpg",
            "/Images/ProfilePictures/7.jpg",
            "/Images/ProfilePictures/8.jpg",
            "/Images/ProfilePictures/9.jpg",
            "/Images/ProfilePictures/10.jpg"
    );
    private static User loggedInUser;
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage newStage) {
        stage = newStage;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static boolean getUsingArrowKey() {
        return loggedInUser.getGameSettings().isUsingArrowKeys();
    }

    public static void setUsingArrowKey(boolean b) {
        loggedInUser.getGameSettings().setUsingArrowKeys(b);
    }

    public static List<String> getRandomProfiles() {
        return randomProfiles;
    }

    public static void stopGameAudio() {
        // stop game audio
    }

    public static void stopMusic() {
        //
    }

    public static Errors changeUserInfo(String newUsername, String newPassword, boolean pictureChanged) {
        String userName = loggedInUser.getUserName();
        String password = loggedInUser.getPassword();
        if (newPassword.equals(password) && newUsername.equals(userName) && !pictureChanged) {
            return Errors.SAME_USERNAME;
        }
        if (getUser(newUsername) != null && getUser(newUsername) != loggedInUser) {
            return Errors.USERNAME_ALREADY_TAKEN;
        }
        if (!RegisterController.isValidUsername(newUsername, newPassword)) {
            return Errors.INVALID_USERNAME;
        }
        if (!RegisterController.isValidPassword(newUsername, newPassword)) {
            return Errors.WEAK_PASSWORD;
        }
        loggedInUser.setUserName(newUsername);
        loggedInUser.setPassword(newPassword);
        return Errors.INFO_CHANGED;

    }

    public static User getUser(String newUsername) {
        for (User user : allUsers) {
            if (user.getUserName().equals(newUsername)) {
                return user;
            }
        }
        return null;
    }

    public static void logOut() {
        loggedInUser = null;
    }

    public static void deleteAccount() {
        allUsers.remove(loggedInUser);
        loggedInUser = null;
    }

    public static void saveUsers() {
        File file = new File(userDataDir);
        // each user one line:
        // username password profilePicturePath gameSettings
        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file));
            for (User user : allUsers) {
                writer.write(user.getUserName() + " " + user.getPassword() + " " + user.getProfilePicturePath() + " " + user.getGameSettings().toString() + " " + user.getScore() + " " + user.getLastWave() + " " + user.getKills() + " " + user.getDiffKill() + " " + user.getAllShoots() + " " + user.getAllHits() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUsers() {
        File file = new File(userDataDir);
        // each user one line:
        // username password profilePicturePath gameSettings
        // read each line and create a new user object
        try {
            java.util.Scanner scanner = new java.util.Scanner(file);
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                User user = new User(data[0], data[1], false);
                user.setProfilePicturePath(data[2]);
                user.getGameSettings().setUsingArrowKeys(Boolean.parseBoolean(data[3]));
                user.getGameSettings().setSoundMuted(Boolean.parseBoolean(data[4]));
                user.getGameSettings().setMusicMuted(Boolean.parseBoolean(data[5]));
                user.getGameSettings().setBWColorize(Boolean.parseBoolean(data[6]));
                user.getGameSettings().setDifficulty(GameDifficulty.valueOf(data[7]));
                user.setScore(Integer.parseInt(data[8]));
                user.setLastWave(Integer.parseInt(data[9]));
                user.setKills(Integer.parseInt(data[10]));
                user.setDiffKill(Integer.parseInt(data[11]));
                user.setAllShoots(Integer.parseInt(data[12]));
                user.setAllHits(Integer.parseInt(data[13]));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setProfilePicture(String path) {
        loggedInUser.setProfilePicturePath(path);
    }

    public static Image getBackgroundPath(int i) {
        switch (i) {
            case 1:
                return new Image("file:src/main/resources/Images/back3.jpg");
            case 2:
                return new Image("file:src/main/resources/Images/back2.jpg");
            case 3:
                return new Image("file:src/main/resources/Images/back8.jpg");
        }
        return new Image("file:src/main/resources/Images/back3.jpg");
    }

    public static Image getFreezeImage() {
        return new Image("file:src/main/resources/Images/Game/snow.jpg");
    }

    public static String getFreezeSound() {
        return "/Sounds/iceStart.mp3";
    }

    public static String getUnfreezeSound() {
        return "/Sounds/iceEnd.mp3";
    }

    public static String getMigAlert() {
        return "/Sounds/migAlert.mp3";
    }

    public static void setMainMenuView(MainMenuView mainMenuView) {
        App.mainMenuView = mainMenuView;
    }

    public static MainMenuView getMainMenuView() {
        return mainMenuView;
    }
}
