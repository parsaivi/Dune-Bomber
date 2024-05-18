package model.gameModels.jet;

import model.App;
import model.gameModels.targets.Fire;
import model.enums.TargetNumbers;

public class JetFire extends Fire {
    public JetFire(WarJet jet) {
        super(jet, TargetNumbers.JET_FIRE_WIDTH.getNumber(App.getLoggedInUser().getDifficulty()), TargetNumbers.JET_FIRE_HEIGHT.getNumber(App.getLoggedInUser().getDifficulty()));
        //jetFireSetFill();
        jet.setFire(this);
        System.out.println("JetFire constructor");
    }

    private void jetFireSetFill() {
        this.setFill(javafx.scene.paint.Color.RED);
    }



}
