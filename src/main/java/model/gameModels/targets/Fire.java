package model.gameModels.targets;

import model.App;
import model.gameModels.Target;
import model.gameModels.jet.*;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Fire extends Rectangle {
    public Fire(Target target) {
        super(TargetNumbers.FIRE_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.FIRE_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        this.setX(target.getX());
        this.setY(target.getY());
        fireSetFill();
    }

    public Fire(Target target, double x, double y) {
        super(x, y);
        this.setX(target.getX() + 5);
        this.setY(target.getY() - 10);
        fireSetFill();
    }

    public Fire(WarJet jet, double x, double y) {
        super(x, y);
        this.setX(jet.getX());
        this.setY(jet.getY());
        fireSetFill();
    }

    public Fire(Missile missile, double x, double y) {
        super(x, y);
        this.setX(missile.getX() - 5);
        this.setY(missile.getY() - 50);
        //fireSetFill();
    }

    public Fire(AtomicBomb atomicBomb, double x, double y) {
        super(x, y);
        this.setX(atomicBomb.getX() - 25);
        this.setY(atomicBomb.getY() - 50);
        //fireSetFill();
    }

    public Fire(ClusterBomb clusterBomb, double x, double y) {
        super(x, y);
        this.setX(clusterBomb.getX() - 40);
        this.setY(clusterBomb.getY() - 30);
        fireSetFill();
    }

    public Fire(Bomblet bomblet, double x, double y) {
        super(x, y);
        this.setX(bomblet.getX());
        this.setY(bomblet.getY());
        //fireSetFill();
    }

    private void fireSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/fire2.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}
