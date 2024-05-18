package model;

import model.enums.GameDifficulty;

public class GameSettings {
    private GameDifficulty dificulty = GameDifficulty.EASY;
    private boolean musicMuted = false;
    private boolean soundMuted = false;
    private boolean BWColorize = false;
    private boolean usingArrowKeys = false;


    public void setDificulty(GameDifficulty dificulty) {
        this.dificulty = dificulty;
    }

    public void setMusicMuted(boolean musicMuted) {
        this.musicMuted = musicMuted;
    }

    public void setSoundMuted(boolean soundMuted) {
        this.soundMuted = soundMuted;
    }

    public void setBWColorize(boolean BWColorize) {
        this.BWColorize = BWColorize;
    }

    public void setUsingArrowKeys(boolean usingArrowKeys) {
        this.usingArrowKeys = usingArrowKeys;
    }

    public GameDifficulty getDifficulty() {
        return dificulty;
    }

    public boolean isMusicMuted() {
        return musicMuted;
    }

    public boolean isSoundMuted() {
        return soundMuted;
    }

    public boolean isBWColorize() {
        return BWColorize;
    }

    public boolean isUsingArrowKeys() {
        return usingArrowKeys;
    }

    public void setDifficulty(GameDifficulty gameDifficulty) {
        this.dificulty = gameDifficulty;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (usingArrowKeys) result.append(true).append(" ");
        else result.append(false).append(" ");
        if (soundMuted) result.append(true).append(" ");
        else result.append(false).append(" ");
        if (musicMuted) result.append(true).append(" ");
        else result.append(false).append(" ");
        if (BWColorize) result.append(true).append(" ");
        else result.append(false).append(" ");
        result.append(dificulty);
        return result.toString();
    }
}
