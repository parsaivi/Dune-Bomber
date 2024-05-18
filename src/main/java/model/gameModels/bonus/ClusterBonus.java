package model.gameModels.bonus;

import model.Game;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ClusterBonus extends Bonus{
    public ClusterBonus(Game game, double x, double y) {
        super(game, x, y);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/clusterBombIcon.png")));
    }

    @Override
    public void addBonus() {
        game.addClusterBombForce();
    }
}
