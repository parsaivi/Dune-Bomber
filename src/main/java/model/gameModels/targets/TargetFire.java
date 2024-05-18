package model.gameModels.targets;

import model.gameModels.Target;
import model.enums.TargetNumbers;

public class TargetFire extends Fire{
    public TargetFire(Target target) {
        super(target, TargetNumbers.FIRE_WIDTH.getNumber(target.difficulty), TargetNumbers.FIRE_HEIGHT.getNumber(target.difficulty));
    }
}
