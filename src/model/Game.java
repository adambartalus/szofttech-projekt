package model;

import exception.MaxLevelReachedException;
import exception.NotAvailableBuildingPositionException;
import exception.NotEnoughGoldException;
import model.tower.Tower;
import model.spell.Spell;
import model.spell.ActiveSpell;
import model.unit.StrongUnit;
import model.unit.Unit;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;

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
            new Player(player1, new Position(r.nextInt(2), r.nextInt(d.width))),
            new Player(player2, new Position(d.width - 1 - (r.nextInt(2)), r.nextInt(d.width)))};
        this.units = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.towerShots = new ArrayList<>();
        this.goldmines = new ArrayList<>();
        this.activeSpells = new ArrayList<>();
        this.mapDimension = d;
        generateRandomObstacles();
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
    public void applySpell(Spell spell, Position pos) {
        getActivePlayer().decreaseGold(spell.getCost());
        spell.Effect(pos, this, getActivePlayer());
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
        
        if(getActivePlayer().getGold() < u.getCost()) {
            throw new NotEnoughGoldException();
        }
        u.owner = getActivePlayer();
    	u.findPath(createCollisionMapWithoutCastles());
        this.units.add(u);
        getActivePlayer().addUnit(u);
        getActivePlayer().decreaseGold(u.getCost());
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
        Tower tower;
        try {
            Class<?>[] types = new Class[] {Position.class, Player.class};
            Constructor<?> c = towerClass.getConstructor(types);
            tower = (Tower)c.newInstance(pos, players[activePlayerIndex]);
        } catch (Exception e) {
            System.err.println("Error");
            return ;
        }
        
        if(getActivePlayer().getGold() < tower.getCost()) {
            throw new NotEnoughGoldException();
        }
        if(!canBuildTower(pos)) {
            throw new NotAvailableBuildingPositionException();
        }
        // Checking if new tower blocks the path to the castle
        int[][] cMap = createCollisionMapWithoutCastles();
        cMap[pos.getX()][pos.getY()] = 1;
        Unit testunit = new StrongUnit(
            getActivePlayer().getCastlePosition(),
            getOpponent().getCastlePosition()
        );
        testunit.findPath(cMap);
        if(testunit.path.isEmpty()) {
            throw new NotAvailableBuildingPositionException();
        }
        this.towers.add(tower);
        getActivePlayer().addTower(tower);
        getActivePlayer().decreaseGold(tower.getCost());
    }
    public void demolishTower(Tower t) {
        towers.remove(t);
        t.getOwner().removeTower(t);
        t.getOwner().increaseGold(t.getGoldReturnOnDemolish());
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
        return createCollisionMap()[pos.getX()][pos.getY()] == 1;
    }
    private Position getRandomPosition() {
        Random r = new Random();
        return new Position(
            r.nextInt(mapDimension.width),
            r.nextInt(mapDimension.height)
        );
    }
    /**
     * TODO: obstacle cannot block path to castle
     */
    private void generateRandomObstacles() {
        Random r = new Random();
        int num = 10 + r.nextInt(4);
        int[][] cMap;
        Position p;
        while(obstacles.size() < num) {
            cMap = createCollisionMap();
            p = getRandomPosition();
            if(cMap[p.getX()][p.getY()] == 0) {
                obstacles.add(new Obstacle(p));
            }
        }
    }
    void printMap(int[][] map) {
        for(int i = 0; i < map.length; ++i) {
            for(int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
    int[][] createCollisionMap() {
        int rows = mapDimension.width;
        int cols = mapDimension.height;
        int[][] cMap = new int[rows][cols];
        Position p = players[0].getCastlePosition();
        cMap[p.getX()][p.getY()] = 1;
        p = players[1].getCastlePosition();
        cMap[p.getX()][p.getY()] = 1;
        for(Tower t : towers) {
            p = t.getPosition();
            cMap[p.getX()][p.getY()] = 1;
        }
        for(Goldmine g : goldmines) {
            p = g.getPosition();
            cMap[p.getX()][p.getY()] = 1;
        }
        for(Obstacle o : obstacles) {
            p = o.getPosition();
            cMap[p.getX()][p.getY()] = 1;
        }
        return cMap;
    }
    int[][] createCollisionMapWithoutCastles() {
        int[][] cMap = createCollisionMap();
        Position p = players[0].getCastlePosition();
        cMap[p.getX()][p.getY()] = 0;
        p = players[1].getCastlePosition();
        cMap[p.getX()][p.getY()] = 0;
        
        return cMap;
    }
    public void upgradeTower(Tower tower)
            throws NotEnoughGoldException, MaxLevelReachedException {
        tower.upgrade();
    }
    private Player getOpponentOf(Player p) {
        if(players[0] == p) return players[1];
        return players[0];
    }
    public void turn() {
        if(gameOver) return;
        
        units.forEach(u -> {
            u.step(createCollisionMapWithoutCastles());
            if(isUnitAtOpponentCastle(u)) {
                getOpponentOf(u.getOwner()).damageCastleHp(u.getDamage());
                u.takeDamage(u.getHp());
            }
        });
        units.removeIf(u -> u.isDead());
        clearTowerShots();
        activeSpells.clear();
        towers.forEach(t -> {
            t.turn(this);
        });
        units.removeIf(u -> u.isDead());
        nextPlayer();
        goldmineTurn();
        getActivePlayer().increaseGold(100);
        if(getActivePlayer().getCastleHp() < 0) {
            gameOver = true;
            winner = getOpponent();
        }
        int[][] cMap = createCollisionMap();
        for(int i = 0; i < getMapDimension().width; i++) {
            for(int j = 0; j < getMapDimension().height; j++) {
                    if(rand.nextInt(1000) < 1 && 0 == cMap[i][j]) {
                    Unit newunit = new StrongUnit(new Position(i,j), getActivePlayer().getCastlePosition());
                    newunit.owner = neutral;
                    newunit.findPath(createCollisionMapWithoutCastles());
                    getUnits().add(newunit);
                }
            }
        }
    }

    private boolean isUnitAtOpponentCastle(Unit u) {
        if(u.getOwnerName().equals("")) {
            return u.getPosition().equals(getActivePlayer().getCastlePosition()) ||
                   u.getPosition().equals(getOpponent().getCastlePosition());
        }
        Player opponent = getOpponentOf(u.getOwner());
        return u.getPosition().equals(opponent.getCastlePosition());
    }
}
