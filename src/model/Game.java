package model;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

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
     * 
     * @param d the dimensions of the map
     * @param player1 the name of player 1
     * @param player2 the name of player 2
     */
    public Game(Dimension d, String player1, String player2) {
        this.players = new Player[]{
            new Player(player1, new Position(d.width / 2 + 1, 1)),
            new Player(player2, new Position(d.width / 2 - 1, d.height - 2))};
        this.units = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.mapDimension = d;
    }
    /**
     * 
     * @param d the dimensions of the map
     */
    public Game(Dimension d) {
        this(d, "Player1", "Player2");
    }
    public void nextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % 2;
    }
    
    public Player getActivePlayer() {
        return this.players[activePlayerIndex];
    }
    
    private Player getOpponent() {
        return this.players[(activePlayerIndex + 1) % 2];
    }
    
    public Player getPlayer(int i) {
        return players[i];
    }
    
    public Dimension getMapDimension() {
        return this.mapDimension;
    }
    
    /**
     * Adds an unit to the game, the active player is the owner
     * @param u the unit to add
     */
    public void addUnit(Unit u) {
    	u.findPath(getOpponent().getCastlePosition());
        this.units.add(u);
        players[activePlayerIndex].addUnit(u);
    }
    /**
     * Adds a tower to the game, the active player is the owner
     * @param towerClass the class of the tower
     * @param pos the position of the tower
     */
    public void addTower(Class<?> towerClass, Position pos) {
        Field costField;
        int cost;
        try {
            costField = towerClass.getField("COST");
            cost = costField.getInt(null);
        } catch (Exception ex) {
            System.err.println("Error");
            return ;
        }

        if(players[activePlayerIndex].getGold() < cost) {
            System.err.println("Not enough gold!");
            return ;
        }
        if(!canBuildTower(pos)) {
            System.err.println("Cannot build tower there!");
            return ;
        }
        try {
            Class<?>[] types = new Class[] {Position.class, Player.class};
            Constructor<?> c = towerClass.getConstructor(types);
            Tower t = (Tower)c.newInstance(pos, players[activePlayerIndex]);
            
            this.towers.add(t);
            players[activePlayerIndex].addTower(t);
            players[activePlayerIndex].decreaseGold(cost);
        } catch (Exception e) {
            System.err.println("Error");
        }
    }
    public void demolishTower(Tower t) {
        towers.remove(t);
        t.getOwner().removeTower(t);
    }
    public Tower getTowerAtPos(Position pos) {
        for(Tower t : towers) {
            if(t.getPosition().equals(pos)) {
                return t;
            }
        }
        return null;
    }
    
    public ArrayList<Unit> getUnits() {
        return this.units;
    }
    /**
     * Checks if the active player can build a tower at the given position
     * @param pos The position of the tower
     * @return True if the active player can build a tower at the given position
     */
    private boolean canBuildTower(Position pos) {
        boolean allyBuildingInRange = false;
        for(Tower t : towers) {
            //check if there is already a tower at the given position
            if(t.getPosition().equals(pos)) {
                return false;
            }
            //check if there is an enemy tower in range
            if(t.getPosition().distance(pos) <= 2) {
                if(t.getOwner() == getOpponent()) {
                    return false;
                }
                allyBuildingInRange = true;
            }
        }
        //checking castles
        if(getOpponent().getCastlePosition().distance(pos) <= 2) {
            return false;
        }
        
        if(getActivePlayer().getCastlePosition().equals(pos)) return false;
        if(getActivePlayer().getCastlePosition().distance(pos) <= 2) {
            allyBuildingInRange = true;
        }
        
        return allyBuildingInRange;
    }
    
}
