package model.enums;

public enum GameDifficulty {
    EASY,
    MEDIUM,
    HARD;

    public static GameDifficulty getDifficulty(String difficulty) {
        switch (difficulty) {
            case "EASY":
                return EASY;
            case "MEDIUM":
                return MEDIUM;
            case "HARD":
                return HARD;
            default:
                return null;
        }
    }

    public static String getDifficulty(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return "EASY";
            case MEDIUM:
                return "MEDIUM";
            case HARD:
                return "HARD";
            default:
                return null;
        }
    }
}
