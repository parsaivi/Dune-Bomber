package controller.animations;

import model.Game;
import model.gameModels.bonus.Bonus;
import javafx.animation.Transition;

public class BonusAnimation extends Transition {
    Bonus bonus;
    Game game;
    public BonusAnimation(Bonus bonus) {
        this.bonus = bonus;
        this.game = bonus.game;
        this.setCycleCount(-1);
        setCycleDuration(javafx.util.Duration.millis(1000));
    }

    @Override
    protected void interpolate(double v) {
        bonus.setY(bonus.getY() - bonus.ySpeed);
        if (bonus.getY() > game.gamePane.getHeight()) {
            game.gamePane.getChildren().remove(bonus);
            this.stop();
        }
        bonus.setX(Math.sin(bonus.getY() / 20) + bonus.getX());

        if (bonus.getBoundsInParent().intersects(game.warJet.getBoundsInParent())) {
            game.gamePane.getChildren().remove(bonus);
            this.stop();
            bonus.addBonus();
        }
    }
}
