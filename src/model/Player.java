package model;

import java.util.ArrayList;

/**
 * Class of player
 */
public class Player {
    
    private int gold;
    private final Castle castle;
    private ArrayList<Unit> units;
    private ArrayList<Tower> towers;

    public Player(Position castlePos) {
        this.gold = 1000;
        this.castle = new Castle(castlePos, this);
        this.units = new ArrayList<>();
        this.towers = new ArrayList<>();
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    /**
     * Increases this player's gold by {@code v }
     * @param v the amount of gold to be added
     */
    public void increaseGold(int v) {
        this.gold += v;
    }
    /**
     * Decreases this player's gold by {@code v }
     * @param v the amount of gold to be removed
     */
    public void decreaseGold(int v) {
        this.gold -= v;
    }
    
    public void addUnit(Unit u) {
        this.units.add(u);
    }
    public void removeUnit(Unit u) {
        this.units.remove(u);
    }
    public void addTower(Tower t) {
        this.towers.add(t);
    }
    
    public ArrayList<Position> getTowerPositions() {
        ArrayList<Position> tPos = new ArrayList<>();
        for(Tower t : this.towers) {
            tPos.add(new Position(t.getPosition()));
        }
        
        return tPos;
    }
    
    public Position getCastlePosition() {
        return castle.getPosition();
    }
}
