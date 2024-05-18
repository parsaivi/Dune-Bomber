package model.gameModels.targets;

import model.App;
import model.gameModels.jet.WarJet;
import model.enums.TargetNumbers;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class RocketFire extends Fire{
    public RocketFire(WarJet warJet){
        super(warJet, TargetNumbers.ROCKET_BOMB_EXPLOSION_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.ROCKET_BOMB_EXPLOSION_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        rocketSetFill();
    }

    private void rocketSetFill() {
        Image image = new Image("file:src/main/resources/Images/Game/hit6.gif");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }

}
