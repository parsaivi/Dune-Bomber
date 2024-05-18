package model.gameModels.jet;

import model.gameModels.GameField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class WarJet extends Rectangle {
    //    public ImageView imageView;
    public double maxHP = 100;
    public double speed = 1;
    public double HP = 100;
    public double damage = 10;
    public int remainingAtomicBombs = 0;
    public int remainingClusterBombs = 0;

    public GameField gameField;
    public ArrayList<JetFire> jetFires = new ArrayList<>();
    public int angle;
    public boolean isRotated = false;

    public WarJet() {
        super(0, 100, 70, 30);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/Jet.png")));
    }

    public WarJet(WarJet warJet) {
        super(warJet == null ? 0 : warJet.getX(),
                warJet == null ? 100 : warJet.getY(),
                70,
                30);
        this.setFill(new ImagePattern(new Image("file:src/main/resources/Images/Game/Jet.png")));
        if (warJet != null) {
            this.maxHP = warJet.maxHP;
            this.HP = warJet.HP;
            this.damage = warJet.damage;
            this.remainingAtomicBombs = warJet.remainingAtomicBombs;
            this.remainingClusterBombs = warJet.remainingClusterBombs;
            this.gameField = warJet.gameField;
            this.jetFires = warJet.jetFires;
            this.angle = warJet.angle;
            if (warJet.isRotated)
                this.setScaleX(-1);
            this.gameField = gameField;
        }
    }

    public void bottomHits() {
        HP = 0;
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }


    public void Hit(double damage) {
        HP -= damage;
    }

    public void setFire(JetFire jetFire) {
        this.jetFires.add(jetFire);
    }

    public void removeFire(JetFire jetFire) {
        this.jetFires.remove(jetFire);
    }
}
