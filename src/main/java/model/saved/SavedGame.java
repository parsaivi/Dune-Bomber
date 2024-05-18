package model.saved;

public class SavedGame implements java.io.Serializable{
    public int waveNumber;
    public int kills;
    public int shoots;
    public int hits;
    public int accuracy;
    public double jetHealth;
    public int boost;
    public SavedWarJet warJet;
    public int AtomicBombCount;
    public int ClusterBombCount;
    public SavedWave savedWave;
}
