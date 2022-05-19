package model;

/**
 * Faster unit
 */
public class FastUnit extends Unit {
    
    public static final int COST = 55;
    
    public FastUnit(Position pos) {
        super(pos, 2, 300);
        type = 'f';
    }
}
