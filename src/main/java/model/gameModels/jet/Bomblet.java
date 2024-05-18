package model.gameModels.jet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Bomblet extends Rectangle {
    public Bomblet() {
        super(0, 0, 10, 10);
    }
    public Bomblet(double x, double y) {
        super(x, y, 15, 8);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/bomblet.png")));
    }
    public Bomblet(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
