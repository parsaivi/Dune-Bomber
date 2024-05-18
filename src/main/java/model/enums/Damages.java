package model.enums;

public enum Damages {
    BOTTOM_DAMAGE(10),
    BULLET_DAMAGE(10),
    MISSILE_DAMAGE(20),
    TANK_DAMAGE(30),
    TRUCK_DAMAGE(20),
    TREE_DAMAGE(5),
    BUILDING_DAMAGE(10);

    private final int damage;

    Damages(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
