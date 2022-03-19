package model;


public class Player {
    
    private int gold;

    public Player() {
        this.gold = 1000;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public void increaseGold(int v) {
        this.gold += v;
    }
    public void decreaseGold(int v) {
        this.gold -= v;
    }
}
