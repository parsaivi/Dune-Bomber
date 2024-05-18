package model.enums;

public enum GameSize {
    WIDTH(800),
    HEIGHT(600);

    private final int size;

    GameSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
