package model.gameModels;

import controller.animations.MigAnimation;
import controller.animations.TankAnimation;
import controller.animations.TruckAnimation;
import controller.animations.WarTankAnimation;
import model.gameModels.enemies.Mig;
import model.gameModels.enemies.WarTank;
import model.gameModels.targets.*;
import model.enums.GameDifficulty;
import model.Game;
import model.enums.TargetNumbers;
import model.saved.SavedGameField;
import model.saved.SavedTarget;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wave extends Transition {
    public Timeline timeline;
    private GameDifficulty difficulty;
    public boolean isFinished = false;
    public Game game;
    public GameField gameField;
    public ArrayList<Target> targets = new ArrayList<Target>();
    public ArrayList<Transition> animations = new ArrayList<Transition>();
    public ArrayList<Integer> badX = new ArrayList<Integer>();
    public int waveNumber = 1;
    public int numberOfTruck;
    public int numberOfTank;
    public int numberOfBuilding;
    public int numberOfTree;
    public int numberOfTrench;
    public int numberOfWarTank;
    public int numberOfMig;
    public int killCount = 0;
    public int waveKillCount = 0;
    private javafx.scene.canvas.Canvas canvas;

    public Wave(Game game, GameField gameField, int waveNumber, GameDifficulty difficulty) {
        this.game = game;
        this.gameField = gameField;
        this.waveNumber = waveNumber;
        this.difficulty = difficulty;
        generateNumbers(waveNumber);
        //generateVehicles(waveNumber, difficulty);
        this.canvas = new javafx.scene.canvas.Canvas();
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(100));
        timeline = new Timeline();
    }

    private void generateTargets(GameDifficulty difficulty) {
        for (int i = 0; i < numberOfTree; i++) {
            Tree tree = generateTree(difficulty);
            gameField.getChildren().add(tree);
        }
        for (int i = 0; i < numberOfBuilding; i++) {
            Building building = generateBuilding(difficulty);
            gameField.getChildren().add(building);
        }
        for (int i = 0; i < numberOfTrench; i++) {
            Trench trench = generateTrench(difficulty);
            gameField.getChildren().add(trench);
        }
        for (int i = 0; i < numberOfTruck; i++) {
            int delay = i * 15;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay), e -> {
                Truck truck = generateTruck(difficulty);
                TruckAnimation truckAnimation = generateTruckAnimation(truck);
                animations.add(truckAnimation);
                truckAnimation.play();
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        for (int i = 0; i < numberOfTank; i++) {
            int delay = i * 8 + 6;
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay), e -> {
                Tank tank = generateTank(difficulty);
                TankAnimation tankAnimation = generateTankAnimation(tank);
                animations.add(tankAnimation);
                tankAnimation.play();
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        for (int i = 0; i < numberOfWarTank; i++) {
            int delay = randomNumberForEnemy();
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay), e -> {
                WarTank warTank = generateWarTank(difficulty);
                WarTankAnimation warTankAnimation = generateWarTankAnimation(warTank);
                animations.add(warTankAnimation);
                warTankAnimation.play();
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        for (int i = 0; i < numberOfMig; i++) {
            int delay = randomNumberForEnemy();
            KeyFrame alert = new KeyFrame(Duration.seconds(delay - 5), e -> {
                game.gameLauncher.playMigAlert();
            });
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delay), e -> {
                game.gameLauncher.stopMigAlert();
                Mig mig = generateMig(difficulty);
                MigAnimation migAnimation = generateMigAnimation(mig);
                animations.add(migAnimation);
                migAnimation.play();
            });
            timeline.getKeyFrames().add(alert);
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    private int randomNumberForEnemy() {
        Random random = new Random();
        return random.nextInt(20) + random.nextInt(5) + random.nextInt(5) + 10;
    }

    private MigAnimation generateMigAnimation(Mig mig) {
        return new MigAnimation(mig, this);
    }

    private Mig generateMig(GameDifficulty difficulty) {
        Random random = new Random();
        int migWidth = (int) TargetNumbers.MIG_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        x = randomIntOutOfGame();
        x -= 2 * migWidth;
        boolean rotate = x > 0;
        int y = random.nextInt(100);
        y += 400;
        Mig mig = new Mig(gameField, difficulty, x, rotate, y);
        return mig;
    }

    private WarTankAnimation generateWarTankAnimation(WarTank warTank) {
        return new WarTankAnimation(warTank, this);
    }

    private WarTank generateWarTank(GameDifficulty difficulty) {
        Random random = new Random();
        int warTankWidth = (int) TargetNumbers.WARTANK_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        x = randomIntOutOfGame();
        x -= 2 * warTankWidth;
        boolean rotate = x > 0;
        int y = gameField.getGroundHeightAt(x);
        int x2 = x + warTankWidth;
        int y2 = gameField.getGroundHeightAt(x2);
        int x3 = x + warTankWidth / 2;
        int y3 = gameField.getGroundHeightAt(x3);
        int angle = (int) (Math.toDegrees(Math.atan((double) (y - y2) / warTankWidth)));
        WarTank warTank = new WarTank(gameField, difficulty, x, rotate, angle, y3);
        targets.add(warTank);
        return warTank;
    }

    public TankAnimation generateTankAnimation(Tank tank) {
        return new TankAnimation(tank, this);
    }

    private TruckAnimation generateTruckAnimation(Truck truck) {
        return new TruckAnimation(truck, this);
    }

    public Tank generateTank(GameDifficulty difficulty) {
        Random random = new Random();
        boolean rotate = random.nextBoolean();
        int tankWidth = (int) TargetNumbers.TANK_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        x = random.nextInt(paneWidth + 4 * tankWidth);
        x -= 2 * tankWidth;
        int y = gameField.getGroundHeightAt(x);
        int x2 = x + tankWidth;
        int y2 = gameField.getGroundHeightAt(x2);
        int x3 = x + tankWidth / 2;
        int y3 = gameField.getGroundHeightAt(x3);
        int angle = (int) (Math.toDegrees(Math.atan((double) (y - y2) / tankWidth)));
        Tank tank = new Tank(gameField, difficulty, x, rotate, angle, y3);
        targets.add(tank);
        return tank;
    }

    private Truck generateTruck(GameDifficulty difficulty) {
        Random random = new Random();
        boolean rotate = random.nextBoolean();
        int truckWidth = (int) TargetNumbers.TRUCK_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        x = random.nextInt((int) (paneWidth + 4 * truckWidth));
        x -= 2 * truckWidth;
        int y = gameField.getGroundHeightAt(x);
        int x2 = x + truckWidth;
        int y2 = gameField.getGroundHeightAt(x2);
        int x3 = x + truckWidth / 2;
        int y3 = gameField.getGroundHeightAt(x3);
        int angle = (int) (Math.toDegrees(Math.atan((double) (y - y2) / truckWidth)));
        Truck truck = new Truck(gameField, difficulty, x, rotate, angle, y3);
        targets.add(truck);
        return truck;
    }

    private Trench generateTrench(GameDifficulty difficulty) {
        Random random = new Random();
        int trenchWidth = (int) TargetNumbers.TRENCH_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        do {
            x = random.nextInt((int) (paneWidth - trenchWidth));
        } while (badX.contains(x));
        for (int i = x; i < x + trenchWidth; i++) {
            badX.add(i);
        }
        int y = gameField.getGroundHeightAt(x);
        int x2 = x + trenchWidth;
        int y2 = gameField.getGroundHeightAt(x2);
        int x3 = x + trenchWidth / 2;
        int y3 = gameField.getGroundHeightAt(x3);
        int angle = (int) (Math.toDegrees(Math.atan((double) (y - y2) / trenchWidth)));
        Trench trench = new Trench(gameField, difficulty, x, angle, y3);
        targets.add(trench);
        return trench;
    }

    private Tree generateTree(GameDifficulty difficulty) {
        Random random = new Random();
        int treeWidth = (int) TargetNumbers.TREE_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        int x;
        do {
            x = random.nextInt((int) (paneWidth - treeWidth));
        } while (badX.contains(x));
        for (int i = x; i < x + treeWidth; i++) {
            badX.add(i);
        }
        int y = gameField.getGroundHeightAt(x);
        Tree tree = new Tree(gameField, difficulty, x, y);
        targets.add(tree);
        return tree;
    }

    private Building generateBuilding(GameDifficulty difficulty) {
        Random random = new Random();
        int buildingWidth = (int) TargetNumbers.BUILDING_WIDTH.getNumber(difficulty);
        int paneWidth = (int) TargetNumbers.PANE_WIDTH.getNumber(difficulty);
        System.out.println(game.gamePane.getWidth());
        int x = random.nextInt((int) (paneWidth - buildingWidth));
        for (int i = x; i < x + buildingWidth; i++) {
            badX.add(i);
        }
        int y = gameField.getGroundHeightAt(x);
        int x2 = x + buildingWidth;
        int y2 = gameField.getGroundHeightAt(x2);
        int x3 = x + buildingWidth / 2;
        int y3 = gameField.getGroundHeightAt(x3);
        int angle = (int) (Math.toDegrees(Math.atan((double) (y - y2) / buildingWidth)));
        Building building = new Building(gameField, difficulty, x, angle, y3);
        targets.add(building);
        return building;
    }

    private void generateNumbers(int waveNumber) {
        numberOfTruck = waveNumber * 2;
        numberOfTank = (waveNumber - 1) * 2 + 3;
        numberOfBuilding = waveNumber;
        numberOfTree = waveNumber * 3;
        numberOfTrench = waveNumber;
        numberOfWarTank = waveNumber / 2;
        numberOfMig = waveNumber / 3;
        waveKillCount = numberOfTruck * 2 + numberOfTank + numberOfBuilding * 5 + numberOfTrench * 3;
    }


    public void startWave(Pane gamePane) {

        gameField.layoutChildren();
        gamePane.getChildren().add(gameField);
        gamePane.getChildren().add(canvas);
        if (targets.isEmpty()) {
            generateTargets(difficulty);
        }
        gameField.drawGround(canvas.getGraphicsContext2D());
        this.play();
    }

    public void checkWaveOver() {
        if (killCount >= waveKillCount) waveOver();
    }

    private void waveOver() {
        isFinished = true;
        killCount = 0;
        this.stop();
        game.waveEnd();
    }

    @Override
    protected void interpolate(double v) {
        checkHits();
        checkWaveOver();
    }

    private void checkHits() {
        Target hitTarget = null;
        for (Target target : targets) {
            if (target.isDistroyed) {
                killCount += target.getScore();
                game.addKill(target.getScore());
                game.addHits();
                getBonus(target);

                if (target instanceof WarTank || target instanceof Tank || target instanceof Truck) {
                    //stop the animation of the vehicle:
                    for (Transition animation : animations) {
                        if (animation instanceof TankAnimation) {
                            if (((TankAnimation) animation).tank == target) {
                                animation.stop();
                            }
                        }
                        if (animation instanceof TruckAnimation) {
                            if (((TruckAnimation) animation).truck == target) {
                                animation.stop();
                            }
                        }
                        if (animation instanceof WarTankAnimation) {
                            if (((WarTankAnimation) animation).warTank == target) {
                                animation.stop();
                            }
                        }
                    }
                }

                setOnFire(target);

                Timeline timeline = new Timeline();
                KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event -> {
                    removeTarget(target);
                });
                timeline.getKeyFrames().add(keyFrame);
                timeline.setCycleCount(1);
                timeline.play();

                hitTarget = target;
            }
        }
        if (hitTarget != null) {
            targets.remove(hitTarget);
        }
    }

    private void setOnFire(Target target) {
        TargetFire targetFire = new TargetFire(target);
        game.gamePane.getChildren().add(targetFire);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), event -> {
            game.gamePane.getChildren().remove(targetFire);
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void removeTarget(Target target) {
        if (target instanceof WarTank) {
            game.gamePane.getChildren().remove(((WarTank) target).circleAround);
        }
        gameField.getChildren().remove(target);
    }

    private void getBonus(Target target) {
        if (target instanceof Building) {
            game.addAtomicBomb(target.getX() + target.getWidth() / 2, target.getY());
            game.addBoost(10);
        }
        if (target instanceof Trench) {
            game.addClusterBomb(target.getX() + target.getWidth() / 2, target.getY());
            game.addBoost(20);
        }
        if (target instanceof Tree) {
            game.addBoost(34);
        }
    }


    public void atomicExplosion(double x) {
        for (Target target : targets) {
            double targetX = target.getX() + target.getWidth() / 2;
            double radius = TargetNumbers.ATOMIC_BOMB_RADIUS.getNumber(difficulty);
            if (targetX < x + radius && targetX > x - radius) {
                target.destroy();
            }
        }
    }

    public void bombletExplosion(double x) {
        for (Target target : targets) {
            double targetX = target.getX() + target.getWidth() / 2;
            double radius = TargetNumbers.BOMBLET_RADIUS.getNumber(difficulty);
            if (targetX < x + radius && targetX > x - radius) {
                target.destroy();
            }
        }
    }

    public void clusterExplosion(double x) {
        for (Target target : targets) {
            double targetX = target.getX() + target.getWidth() / 2;
            double radius = TargetNumbers.CLUSTER_BOMB_RADIUS.getNumber(difficulty);
            if (targetX < x + radius && targetX > x - radius) {
                target.destroy();
            }
        }
    }

    public void missileExplosion(double x) {
        for (Target target : targets) {
            double targetX = target.getX() + target.getWidth() / 2;
            double radius = TargetNumbers.MISSILE_RADIUS.getNumber(difficulty);
            if (targetX < x + radius && targetX > x - radius) {
                target.destroy();
            }
        }
    }

    public int randomIntOutOfGame() {
        Random random = new Random();
        //return sth between -200 and 1000 but not in the range of 0 to 800:
        int x;
        do {
            x = random.nextInt(1300) - 200;
        } while (x > 0 && x < 800);
        return x;
    }

    public List<SavedTarget> saveTargets() {
        List<SavedTarget> savedTargets = new ArrayList<>();
        for (Target target : targets) {
            if (target.isDistroyed) continue;
            savedTargets.add(target.save());
        }
        return savedTargets;
    }

    public SavedGameField saveGameField() {
        return gameField.save();
    }

    public void loadTargets(List<SavedTarget> savedTargets, GameField gameField) {
        for (SavedTarget savedTarget : savedTargets) {
            Target target = Target.load(savedTarget, gameField, this);
            targets.add(target);
            game.gamePane.getChildren().add(target);
            if (target instanceof WarTank) {
                game.gamePane.getChildren().add(((WarTank) target).circleAround);
            }
        }
    }
}
