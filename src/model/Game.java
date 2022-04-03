package model;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * The class storing the game data
 */
public class Game {
    public static int cellSize = 48;
    
    private int activePlayerIndex;
    private final Player[] players;
    private final ArrayList<Unit> units;
    private final Dimension mapDimension;

    /**
     * @param d the dimensions of the map
     */
    public Game(Dimension d) {
        this.players = new Player[]{
            new Player(new Position(1, d.width / 2 + 1)),
            new Player(new Position(d.height - 2, d.width / 2 - 1))};
        this.units = new ArrayList<>();
        this.mapDimension = d;
    }
    
    public Player getActivePlayer() {
        return this.players[activePlayerIndex];
    }
    
    public Player getPlayer(int i) { // not final solution
        return players[i];
    }
    
    public Dimension getMapDimension() {
        return this.mapDimension;
    }
    
    /**
     * Adds an unit to the game
     * @param u the unit to add
     */
    public void addUnit(Unit u) {
    	u.findPath(new Position(8,8));
        this.units.add(u);
        this.getActivePlayer().addUnit(u);
    }
    
    public ArrayList<Unit> getUnits() {
        return this.units;
    }
    
}
