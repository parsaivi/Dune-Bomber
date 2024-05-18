package model.enums;

import javafx.scene.image.Image;

public enum BackgroundPath {
    WAVE_1("file:/Images/back3.jpg"),
    WAVE_2("file:/Images/back1.jpg"),
    WAVE_3("file:/Images/back7.jpg");

    private final String path;

    BackgroundPath(String path) {
        this.path = path;
    }

    public Image getImage() {
        return new Image(path);
    }
}
