package model;

import model.enums.GameDifficulty;
import javafx.scene.image.Image;

public class User {
    private String userName;
    private String password;
    private String profilePicturePath;
    private GameSettings gameSettings;
    private int score;
    private int wins;
    private int lastWave;
    private int kills;
    private int diffKill;
    private int allShoots;
    private int allHits;
    private boolean isGuest;

    public User(String userName, String password, boolean isGuest) {
        this.userName = userName;
        this.password = password;
        this.profilePicturePath = App.getRandomProfiles().get((int) (Math.random() * App.getRandomProfiles().size()));
        this.isGuest = isGuest;
        gameSettings = new GameSettings();
        if (!isGuest){
            App.addUser(this);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getProfilePicture() {
        return new Image(String.valueOf(getClass().getResource(profilePicturePath))); //ERROR HERE
    }

    public boolean getIsGuest() {
        return isGuest;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public String getUsername() {
        return userName;
    }

    public void setProfilePicturePath(String path) {
        this.profilePicturePath = path;
        System.out.println(path);
    }

    public int getScore() {
        return score;
    }

    public int getKills(){
        return kills;
    }

    public int getProKills(){
        return diffKill;
    }

    public int getAccuracy(){
        return (int) ((double) allHits / allShoots * 100);
    }

    public String getName() {
        return userName;
    }

    public int getLastWave() {
        return lastWave;
    }

    public String getDiffKill() {
        return String.valueOf(diffKill);
    }

    public int getAllShoots() {
        return allShoots;
    }

    public int getAllHits() {
        return allHits;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLastWave(int lastWave) {
        this.lastWave = lastWave;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDiffKill(int diffKill) {
        this.diffKill = diffKill;
    }

    public void setAllShoots(int allShoots) {
        this.allShoots = allShoots;
    }

    public void setAllHits(int allHits) {
        this.allHits = allHits;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public GameDifficulty getDifficulty() {
        return gameSettings.getDifficulty();
    }

    public boolean playWithArrow() {
        return gameSettings.isUsingArrowKeys();
    }

    public void addScore(int score) {
        this.score += score;
    }

    public boolean isMusicMuted() {
        return gameSettings.isMusicMuted();
    }
    public boolean isSoundMuted(){
        return gameSettings.isSoundMuted();
    }

    public void addAllShots(int shoots) {
        allShoots += shoots;
    }

    public void addWins() {
        wins++;
    }

    public void addAllHits(int hits) {
        allHits += hits;
    }

    public void setData(int wave, int kills, int accuracy ,int allShoots, int allHits) {
        lastWave = wave;
        this.kills += kills;
        switch (getDifficulty()){
            case EASY:
                diffKill += kills;
                break;
            case MEDIUM:
                diffKill += kills * 2;
                break;
            case HARD:
                diffKill += kills * 3;
                break;
        }
        this.allShoots += allShoots;
        this.allHits += allHits;
        this.score += wave * 10 + kills * 10 + accuracy ;
    }
}
