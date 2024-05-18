package controller;

import model.App;
import model.IO.Errors;
import model.User;

public class RegisterController {
    public static Errors signUp(String username, String password) {
        if (!isValidUsername(username, password)) {
            return Errors.INVALID_USERNAME;
        }
        if (!isValidPassword(username, password)) {
            return Errors.INVALID_PASSWORD;
        }
        if (App.getUser(username) != null){
            return Errors.USERNAME_ALREADY_TAKEN;
        }
        User user = new User(username, password, false);
        App.setLoggedInUser(user);
        return Errors.REGISTERED;
    }

    public static boolean isValidPassword(String username, String password) {
        if (password.length() < 8) {
            return false;
        }
        if (password.equals(username)) {
            return false;
        }
        return true;
    }

    public static boolean isValidUsername(String username, String password) {
        if (username.matches("^[a-zA-Z0-9]+$")) {
            return true;
        }
        return false;
    }

    public static Errors signIn(String username, String password) {
        for (User user : App.getAllUsers()) {
            if (user.getUserName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    App.setLoggedInUser(user);
                    return Errors.LOGGED_IN;
                }
                return Errors.WRONG_PASSWORD;
            }
        }
        return Errors.USERNAME_NOT_FOUND;
    }

    public static void guest() {
        App.setLoggedInUser(new User("Guest", "", true));
    }
}
