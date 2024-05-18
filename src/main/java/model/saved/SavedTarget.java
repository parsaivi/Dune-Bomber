package model.saved;

public class SavedTarget implements java.io.Serializable{
    public double x;
    public double y;

    public double health;
    public int type; // 0 for tree, 1 for tank, 2 for truck, 3 for trench, 4 for WarTank, 5 for building
    public int waveNumber;
    public double angle;
    public boolean isRotate;
}

