package model;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class storing the game data
 */
public class Game {
    public static int cellSize = 48;
    
    private int activePlayerIndex;
    private final Player[] players;
    private final ArrayList<Unit> units;
    private final ArrayList<Tower> towers;
    private final Dimension mapDimension;

    /**
     * @param d the dimensions of the map
     */
    public Game(Dimension d) {
        this.players = new Player[]{
            new Player(new Position(1, d.width / 2 + 1)),
            new Player(new Position(d.height - 2, d.width / 2 - 1))};
        this.units = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.mapDimension = d;
    }
    public void nextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % 2;
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
        players[activePlayerIndex].addUnit(u);
    }
    public void addTower(Class towerClass, Position pos) {
        Field costField;
        int cost;
        try {
            costField = towerClass.getField("COST");
            cost = costField.getInt(null);
        } catch (Exception ex) {
            System.out.println("error");
            return ;
        }

        if(players[activePlayerIndex].getGold() < cost) {
            System.out.println("not enough gold!");
            return ; // the player doesn't have enough gold
        }
        try {
            Constructor c = towerClass.getConstructor(new Class[]{Position.class, Player.class});
            Tower t = (Tower)c.newInstance(pos, players[activePlayerIndex]);
            
            this.towers.add(t);
            players[activePlayerIndex].addTower(t);
            players[activePlayerIndex].decreaseGold(cost);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    
    public ArrayList<Unit> getUnits() {
        return this.units;
    }
    
}
