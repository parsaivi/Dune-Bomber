package model.gameModels.jet;

import model.App;
import model.gameModels.targets.Fire;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class ClusterExplosion extends Fire {
    public ClusterExplosion(ClusterBomb clusterBomb){
        super(clusterBomb, TargetNumbers.CLUSTER_BOMB_EXPLOSION_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.CLUSTER_BOMB_EXPLOSION_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        clusterSetFill();
    }

    private void clusterSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/hit5.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}
