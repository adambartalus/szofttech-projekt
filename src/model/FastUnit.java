package model;

/**
 * Faster unit
 */
public class FastUnit extends Unit {
    
    public static final int COST = 55;
    
    public FastUnit(Position pos, Position goalPos) {
        super(pos, goalPos, 2, 300, 150);
        type = 'f';
    }
}
