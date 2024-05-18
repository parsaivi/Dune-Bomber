package model.gameModels.jet;

import model.App;
import model.gameModels.targets.Fire;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class AtomicExplosion extends Fire {
    public AtomicExplosion(AtomicBomb atomicBomb){
        super(atomicBomb, TargetNumbers.ATOMIC_BOMB_EXPLOSION_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.ATOMIC_BOMB_EXPLOSION_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        atomicSetFill();
    }

    private void atomicSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/atomic.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }
}
