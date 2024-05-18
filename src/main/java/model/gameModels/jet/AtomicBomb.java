package model.gameModels.jet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class AtomicBomb extends Shooter {
    public AtomicBomb() {
        super(0, 0, 10, 10);
    }
    public AtomicBomb(double x, double y) {
        super(x, y, 20, 40);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/atomic.png")));
    }
    public AtomicBomb(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
