package model.gameModels.jet;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;


public class ClusterBomb extends Shooter {
    public ClusterBomb() {
        super(0, 0, 10, 10);
    }
    public ClusterBomb(double x, double y) {
        super(x, y, 30, 15);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/cluster.png")));
    }
    public ClusterBomb(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
}
