package model;

import java.awt.Dimension;
import java.util.ArrayList;


public class Game {
    public static int cellSize = 32;
    
    private int activePlayerIndex;
    private final Player[] players;
    private final ArrayList<Unit> units;
    private final Dimension mapDimension;

    public Game(Dimension d) {
        this.players = new Player[]{new Player(), new Player()};
        this.units = new ArrayList<>();
        this.mapDimension = d;
    }
    
    public Player getActivePlayer() {
        return this.players[activePlayerIndex];
    }
    
    public Dimension getMapDimension() {
        return this.mapDimension;
    }
    
    public void addUnit(Unit u) {
        this.units.add(u);
        this.getActivePlayer().addUnit(u);
    }
    
    public ArrayList<Unit> getUnits() {
        return this.units;
    }
    
}
