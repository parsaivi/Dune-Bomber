package model.gameModels.jet;

import model.App;
import model.gameModels.targets.Fire;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BombletExplosion extends Fire {
    public BombletExplosion(Bomblet bomblet){
        super(bomblet, TargetNumbers.BOMBLET_EXPLOSION_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.BOMBLET_EXPLOSION_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        bombletSetFill();
    }

    private void bombletSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/bomblet.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}
