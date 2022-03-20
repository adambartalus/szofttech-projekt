package model;

import java.util.ArrayList;


public class Player {
    
    private int gold;
    ArrayList<Unit> units;

    public Player() {
        this.gold = 1000;
        this.units = new ArrayList<>();
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
    
    public void addUnit(Unit u) {
        this.units.add(u);
    }
    public void removeUnit(Unit u) {
        this.units.remove(u);
    }
}
