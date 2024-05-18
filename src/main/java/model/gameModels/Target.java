package model.gameModels;

import controller.animations.TankAnimation;
import controller.animations.TruckAnimation;
import controller.animations.WarTankAnimation;
import model.App;
import model.enums.GameDifficulty;
import model.gameModels.enemies.WarTank;
import model.gameModels.targets.*;
import model.saved.SavedTarget;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public abstract class Target extends Rectangle {
    public GameDifficulty difficulty;
    public double WIDTH = 100;
    public double HEIGHT = 100;
    public double speed = 1;
    public double maxHP = 100;
    public double HP = 100;
    public double damage = 10;
    public double angle;

    public final GameField gameField;
    public boolean isHit;
    public boolean isDistroyed;

    public Target(GameField gameField, double width, double height) {
        super(width, height);
        this.gameField = gameField;
    }

    public Target(GameField gameField) {
        super(100, 100);
        this.gameField = gameField;
    }

    public Target(GameField gameField, double width, double height, GameDifficulty difficulty) {
        super(width, height);
        this.gameField = gameField;
        this.difficulty = difficulty;
    }

    public static Target load(SavedTarget savedTarget, GameField gameField, Wave wave) {
        switch (savedTarget.type) {
            case 0:
                return new Tree(gameField, App.getLoggedInUser().getDifficulty(), (int) savedTarget.x, (int) savedTarget.y);
            case 1:
                Tank tank = new Tank(gameField, App.getLoggedInUser().getDifficulty(), savedTarget.x, savedTarget.isRotate, savedTarget.angle, savedTarget.y);
                TankAnimation tankAnimation = new TankAnimation(tank, wave);
                tankAnimation.play();
                wave.animations.add(tankAnimation);
                return tank;
            case 2:
                Truck truck = new Truck(gameField, App.getLoggedInUser().getDifficulty(), savedTarget.x, savedTarget.isRotate, savedTarget.angle, savedTarget.y);
                TruckAnimation truckAnimation = new TruckAnimation(truck, wave);
                truckAnimation.play();
                wave.animations.add(truckAnimation);
                return truck;
            case 3:
                return new Trench(gameField, App.getLoggedInUser().getDifficulty(), savedTarget.x, savedTarget.angle, savedTarget.y);
            case 4:
                WarTank warTank = new WarTank(gameField, App.getLoggedInUser().getDifficulty(), savedTarget.x, savedTarget.isRotate, savedTarget.angle, savedTarget.y);
                WarTankAnimation warTankAnimation = new WarTankAnimation(warTank, wave);
                warTankAnimation.play();
                wave.animations.add(warTankAnimation);
                return warTank;
                case 5:
                return new Building(gameField, App.getLoggedInUser().getDifficulty(), savedTarget.x, savedTarget.angle, savedTarget.y);
            default:
                return null;
        }
    }

    public void draw(GraphicsContext g) {
        g.fillRect(this.getX(), this.getY(), WIDTH, HEIGHT);
    }

    public void hit(double damage) {
        HP -= damage;
        if (HP <= 0) {
            destroy();
        }
    }

    void destroy() {
        isDistroyed = true;
    }

    public int getScore() {//truck 2 - tank 1 - tree 0 ...
        switch (this.getClass().getSimpleName()) {
            case "Truck":
                return 2;
            case "Tank":
                return 1;
            case "Tree":
                return 0;
            case "Building":
                return 5;
            case "Trench":
                return 3;
            case "WarTank":
                return 4;
            default:
                return 0;
        }
    }

    public SavedTarget save() {
        SavedTarget savedTarget = new SavedTarget();
        savedTarget.x = this.getX();
        savedTarget.y = 600 - this.getY();
        savedTarget.angle = this.angle;
        savedTarget.health = this.HP;
        savedTarget.isRotate = this.getScaleX() == -1;
        savedTarget.type = this.getScore();
        return savedTarget;
    }
}
