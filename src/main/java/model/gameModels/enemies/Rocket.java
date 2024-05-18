package model.gameModels.enemies;

import model.gameModels.jet.Shooter;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Rocket extends Shooter {
    public double angle = 0;
    public Rocket() {
        super(0, 0, 10, 10);
    }
    public Rocket(double x, double y , double angle){
        super(x, y, 20, 6);
        setRotate(-angle);
        this.angle = angle;
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/rocket.png")));
    }
    public Rocket(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
