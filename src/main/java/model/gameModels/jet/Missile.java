package model.gameModels.jet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Missile extends Shooter {
    public Missile() {
        super(0, 0, 10, 10);
    }
    public Missile(double x, double y) {
        super(x, y, 20, 6);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/missile.png")));
    }
    public Missile(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
