package model.gameModels.jet;

import model.App;
import model.gameModels.targets.Fire;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;


public class MissileExplosion extends Fire {
    private ImageView imageView;
    private Missile missile;
    public MissileExplosion(Missile missile){
        super(missile, TargetNumbers.MISSILE_EXPLOSION_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.MISSILE_EXPLOSION_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        this.missile = missile;
        missileSetFill();
    }

    private void missileSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/hit8.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}
