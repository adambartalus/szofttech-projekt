package model;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * The class storing the game data
 */
public class Game {
    public static int cellSize = 48;
    
    private int activePlayerIndex;
    private final Player[] players;
    private final ArrayList<Unit> units;
    private final ArrayList<Tower> towers;
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Goldmine> goldmines;
    private final Dimension mapDimension;
    public static boolean[][] map;
    public Player neutral;
    private final ArrayList<TowerShot> towerShots;
    public ArrayList<ActiveSpell> activeSpells;
    
    private boolean gameOver;
    private Random rand;
    private Player winner;

    /**
     * 
     * @param d the dimensions of the map
     * @param player1 the name of player 1
     * @param player2 the name of player 2
     */
    public Game(Dimension d, String player1, String player2) {
        rand = new Random();
        Random r = new Random();
        
        this.players = new Player[]{
            new Player(player1, new Position(r.nextInt(d.width), r.nextInt(2))),
            new Player(player2, new Position(r.nextInt(d.width), d.height - 1 - (r.nextInt(2))))};
        this.units = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.towerShots = new ArrayList<>();
        this.goldmines = new ArrayList<>();
        this.activeSpells = new ArrayList<>();
        this.mapDimension = d;
        generateRandomObstacles();
        map = new boolean[mapDimension.width][mapDimension.height];
        reloadObstacles();
        neutral = new Player("",null);
    }
    /**
     * 
     * @param d the dimensions of the map
     */
    public Game(Dimension d) {
        this(d, "Player1", "Player2");
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public Player getWinner() {
        return winner;
    }
    public void addTowerShot(TowerShot ts) {
        towerShots.add(ts);
    }
    public void clearTowerShots() {
        towerShots.clear();
    }
    public ArrayList<TowerShot> getTowerShots() {
        return towerShots;
    }
    public void reloadObstacles() {
    	for(int i = 0; i < mapDimension.width; i++) {
    		for(int j = 0; j < mapDimension.height; j++) {
        		map[i][j] = true;
        	}
    	}
    	ArrayList<Position> obstaclePosition = getObstaclePositions();
    	ArrayList<Position> towerPosition = getTowerPositions();
    	for(int i = 0; i < obstaclePosition.size(); i++) {
    		map[obstaclePosition.get(i).getX()][obstaclePosition.get(i).getY()] = false;
    	}
    	for(int i = 0; i < towerPosition.size(); i++) {
    		map[towerPosition.get(i).getX()][towerPosition.get(i).getY()] = false;
    	}
    }
    public void nextPlayer() {
        activePlayerIndex = (activePlayerIndex + 1) % 2;
    }
    
    public Player getActivePlayer() {
        return this.players[activePlayerIndex];
    }
    
    public int getActivePlayerIndex() {
        return this.activePlayerIndex;
    }
    
    public Player getOpponent() {
        return this.players[(activePlayerIndex + 1) % 2];
    }
    
    public Player getPlayer(int i) {
        return players[i];
    }
    
    public Dimension getMapDimension() {
        return this.mapDimension;
    }
    public ArrayList<Position> getObstaclePositions() {
        ArrayList<Position> pos = new ArrayList<>();
        obstacles.forEach(o -> {
            pos.add(new Position(o.getPosition()));
        });
        return pos;
    }
    
    public ArrayList<Position> getTowerPositions() {
        ArrayList<Position> pos = new ArrayList<>();
        towers.forEach(t -> {
            pos.add(new Position(t.getPosition()));
        });
        return pos;
    }
    public ArrayList<Position> getGoldminePositions() {
        ArrayList<Position> pos = new ArrayList<>();
        goldmines.forEach(g -> {
            pos.add(new Position(g.getPosition()));
        });
        return pos;
    }
    public boolean isObstacleAtPos(Position pos) {
        return obstacles.stream().anyMatch(o -> (pos.equals(o.getPosition())));
    }
    public void buildGoldmine(Position pos) throws
            NotEnoughGoldException, NotAvailableBuildingPositionException {
        if(getActivePlayer().getGold() < Goldmine.COST) {
            throw new NotEnoughGoldException();
        }
        if(isPositionTaken(pos)) {
            throw new NotAvailableBuildingPositionException();
        }
        Goldmine g = new Goldmine(pos, getActivePlayer());
        goldmines.add(g);
        getActivePlayer().decreaseGold(Goldmine.COST);
    }
    public void goldmineTurn() {
        goldmines.forEach(g -> {
            g.turn();
        });
    }
    /**
     * Adds an unit to the game, the active player is the owner
     * @param u the unit to add
     * @throws java.lang.Exception
     */
    public void addUnit(Unit u) throws Exception {
        Field costField;
        int cost;
        try {
            costField = u.getClass().getField("COST");
            cost = costField.getInt(null);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
            throw new Exception();
        }
        if(players[activePlayerIndex].getGold() < cost) {
            throw new NotEnoughGoldException();
        }
        u.owner = players[activePlayerIndex];
    	u.findPath(getOpponent().getCastlePosition());
        this.units.add(u);
        players[activePlayerIndex].addUnit(u);
        players[activePlayerIndex].decreaseGold(cost);
    }
    public void addTower(Tower t) {
        towers.add(t);
    }
    /**
     * Builds a tower to the game, the active player is the owner
     * @param towerClass the class of the tower
     * @param pos the position of the tower
     * @throws java.lang.Exception
     */
    public void buildTower(Class<?> towerClass, Position pos) throws Exception {
        Field costField;
        int cost;
        try {
            costField = towerClass.getField("COST");
            cost = costField.getInt(null);
        } catch (Exception ex) {
            throw new Exception();
        }

        if(players[activePlayerIndex].getGold() < cost) {
            throw new NotEnoughGoldException();
        }
        if(!canBuildTower(pos)) {
            throw new NotAvailableBuildingPositionException();
        }
        // Checking if new tower blocks the path to the castle
        map[pos.getX()][pos.getY()] = false;
        Unit testunit = new StrongUnit(getActivePlayer().getCastlePosition(), this);
        testunit.findPath(getOpponent().getCastlePosition());
        if(testunit.path.isEmpty()) {
            map[pos.getX()][pos.getY()] = true;
            throw new NotAvailableBuildingPositionException();
        }
        //
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
    public Goldmine getGoldmineAtPos(Position pos) {
        for(Goldmine g : goldmines) {
            if(g.getPosition().equals(pos)) {
                return g;
            }
        }
        return null;
    }
    public Tower getTowerAtPos(Position pos) {
        for(Tower t : towers) {
            if(t.getPosition().equals(pos)) {
                return t;
            }
        }
        return null;
    }
    public ArrayList<Unit> getUnitsAtPos(Position pos) {
        ArrayList<Unit> unitsAtPos = new ArrayList<>();
        units.stream().filter(u -> (pos.equals(u.getPosition()))).forEachOrdered(u -> {
            unitsAtPos.add(u);
        });
        return unitsAtPos;
    }
    
    public ArrayList<Unit> getUnits() {
        return this.units;
    }
    public ArrayList<Tower> getTowers() {
        return this.towers;
    }
    /**
     * Checks if the active player can build a tower at the given position
     * @param pos The position of the tower
     * @return True if the active player can build a tower at the given position
     */
    private boolean canBuildTower(Position pos) {
        boolean allyBuildingInRange = false;
        for(Obstacle o : obstacles) {
            if(o.getPosition().equals(pos)) {
                return false;
            }
        }
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
    private boolean isPositionTaken(Position pos) {
        return createCollisionMap()[pos.getY()][pos.getX()] == 1;
    }
    private Position getRandomPosition() {
        Random r = new Random();
        return new Position(
                r.nextInt(mapDimension.width),
                r.nextInt(mapDimension.height)
        );
    }
    private void generateRandomObstacles() {
        Random r = new Random();
        int num = 10 + r.nextInt(4);
        int[][] cMap;
        Position p;
        while(obstacles.size() < num) {
            cMap = createCollisionMap();
            p = getRandomPosition();
            if(cMap[p.getY()][p.getX()] == 0) {
                obstacles.add(new Obstacle(p));
            }
        }
    }
    int[][] createCollisionMap() {
        int rows = mapDimension.height;
        int cols = mapDimension.width;
        int[][] map = new int[rows][cols];
        Position p = players[0].getCastlePosition();
        map[p.getY()][p.getX()] = 1;
        p = players[1].getCastlePosition();
        map[p.getY()][p.getX()] = 1;
        for(Tower t : towers) {
            p = t.getPosition();
            map[p.getY()][p.getX()] = 1;
        }
        for(Goldmine g : goldmines) {
            p = g.getPosition();
            map[p.getY()][p.getX()] = 1;
        }
        for(Obstacle o : obstacles) {
            p = o.getPosition();
            map[p.getY()][p.getX()] = 1;
        }
        return map;
    }
    public void upgradeTower(Tower tower) throws NotEnoughGoldException {
        if(tower.getOwner().getGold() < tower.upgradecost) {
            throw new NotEnoughGoldException();
        }
        tower.getOwner().decreaseGold(tower.upgradecost);
        tower.upgrade();
    }
    public void turn() {
        if(!gameOver) {
            reloadObstacles();
            units.forEach(u -> {
                u.step(this);
            });
            units.removeIf(u -> u.isDead());
            clearTowerShots();
            activeSpells.clear();
            towers.forEach(t -> {
                t.turn(this);
            });
            nextPlayer();
            goldmineTurn();
            getActivePlayer().increaseGold(500);
            if(getActivePlayer().getCastleHp() < 0) {
                gameOver = true;
                winner = getOpponent();
            }
            for(int i = 0; i < getMapDimension().width; i++) {
                for(int j = 0; j < getMapDimension().height; j++) {
                        if(rand.nextInt(1000) < 1 && map[i][j]) {
                        Unit newunit = new StrongUnit(new Position(i,j), this);
                        newunit.owner = neutral;
                        newunit.findPath(getActivePlayer().getCastlePosition());
                        getUnits().add(newunit);
                    }
                }
            }
        }
    }
}
