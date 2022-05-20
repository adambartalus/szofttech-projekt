package model;

/**
 * Stronger unit
 */
public class StrongUnit extends Unit {
    
    public static final int COST = 50;
    
    public StrongUnit(Position pos, Game g) {
        super(pos, 1, 500, g);
        damage = 200;
        type = 's';
    }
}
