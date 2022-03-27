package model;

import java.util.ArrayList;

/**
 * Class of player
 */
public class Player {
    
    private int gold;
    private final Castle castle;
    private ArrayList<Unit> units;

    public Player(Position castlePos) {
        this.gold = 1000;
        this.castle = new Castle(castlePos);
        this.units = new ArrayList<>();
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
    
    public Position getCastlePosition() {
        return castle.getPosition();
    }
}
