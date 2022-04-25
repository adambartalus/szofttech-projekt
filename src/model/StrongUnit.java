package model;

/**
 * Stronger unit
 */
public class StrongUnit extends Unit {
    
    public static final int COST = 50;
    
    public StrongUnit(Position pos) {
        super(pos, 1, 500);
        damage = 200;
    }
}
