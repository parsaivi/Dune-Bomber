package model.enums;

public enum TargetNumbers {
    TRUCK_HP(100, 150, 200),
    TRUCK_DAMAGE(10, 20, 30),
    TRUCK_SPEED(1, 2, 3),
    TRUCK_WIDTH(60, 60, 60),
    TRUCK_HEIGHT(30, 30, 30),
    TANK_HP(200, 250, 300),
    TANK_DAMAGE(20, 30, 40),
    TANK_SPEED(0.3, 0.6, 0.9),
    TANK_WIDTH(70, 70, 70),
    TANK_HEIGHT(25, 25, 25),
    BUILDING_HP(300, 350, 400),
    BUILDING_DAMAGE(30, 40, 50),
    BUILDING_WIDTH(60, 60, 60),
    BUILDING_HEIGHT(40, 40, 40),
    TREE_HP(50, 100, 150),
    TREE_DAMAGE(5, 10, 15),
    TREE_WIDTH(40, 40, 40),
    TREE_HEIGHT(120, 120, 120d),
    HELICOPTER_HP(150, 200, 250),
    HELICOPTER_DAMAGE(15, 25, 35),
    HELICOPTER_SPEED(2, 3, 4),
    WARTANK_HP(300, 350, 400),
    WARTANK_DAMAGE(30, 40, 50),
    WARTANK_SPEED(0.5, 1, 1.5),
    WARTANK_WIDTH(100, 100, 100),
    WARTANK_HEIGHT(50, 50, 50),
    MIG_HP(200, 250, 300),
    MIG_DAMAGE(20, 30, 40),
    MIG_SPEED(1, 2, 3),
    MIG_WIDTH(100, 100, 100),
    MIG_HEIGHT(50, 50, 50),
    CIRCLE_AROUND_RADIUS(100, 200, 300),
    TRENCH_WIDTH(40, 40, 40),
    TRENCH_HEIGHT(60, 60, 60),
    PANE_WIDTH(800, 800, 800),
    PANE_HEIGHT(600, 600, 600),
    MISSILE_RADIUS(30, 20, 10),
    BOMBLET_RADIUS(20, 10, 5),
    ATOMIC_BOMB_RADIUS(200, 100, 50),
    CLUSTER_BOMB_RADIUS(80, 50, 30),
    BONUS_WIDTH(50, 30, 15),
    BONUS_HEIGHT(50, 30, 15),
    BONUS_SPEED(1, 2, 3),
    TARGET_FIRE_WIDTH(40, 40, 40),
    TARGET_FIRE_HEIGHT(40, 40, 40),
    FIRE_WIDTH(40, 40, 40),
    FIRE_HEIGHT(40, 40, 40),
    JET_FIRE_WIDTH(40, 40, 40),
    JET_FIRE_HEIGHT(40, 40, 40),
    MISSILE_EXPLOSION_WIDTH(40, 40, 40),
    MISSILE_EXPLOSION_HEIGHT(60, 60, 60),
    BOMBLET_EXPLOSION_WIDTH(30, 30, 30),
    BOMBLET_EXPLOSION_HEIGHT(30, 30, 30),
    ATOMIC_BOMB_EXPLOSION_WIDTH(100, 100, 100),
    ATOMIC_BOMB_EXPLOSION_HEIGHT(100, 100, 100),
    ROCKET_BOMB_EXPLOSION_WIDTH(50, 50, 50),
    ROCKET_BOMB_EXPLOSION_HEIGHT(50, 50, 50),
    CLUSTER_BOMB_EXPLOSION_WIDTH(100, 100, 100),
    CLUSTER_BOMB_EXPLOSION_HEIGHT(100, 100, 100);

    public final double easy;
    public final double medium;
    public final double hard;

    TargetNumbers(double easy, double medium, double hard) {
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
    }

    public double getNumber(GameDifficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return easy;
            case MEDIUM:
                return medium;
            case HARD:
                return hard;
            default:
                return 0;
        }
    }

}
