package model;

/**
 * Stronger unit
 */
public class StrongUnit extends Unit {
    
    public static final int COST = 50;
    
    public StrongUnit(Position pos, Position goalPos) {
        super(pos, goalPos, 1, 500, 200);
        type = 's';
    }
}
