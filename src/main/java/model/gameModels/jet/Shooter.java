package model.gameModels.jet;

import javafx.scene.shape.Rectangle;

public class Shooter extends Rectangle {
    public Shooter() {
        super(0, 0, 10, 10);
    }
    public Shooter(double x, double y) {
        super(x, y, 20, 6);
    }
    public Shooter(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
