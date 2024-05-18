package model.saved;

import java.util.List;

public class SavedWave implements java.io.Serializable{
    public int waveNumber;
    public int kills;
    public int shoots;
    public int hits;
    public int AtomicBombCount;
    public int ClusterBombCount;
    public List<SavedTarget> savedTargets;
    public SavedGameField savedGameField;
}
