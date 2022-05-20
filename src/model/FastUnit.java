package model;

/**
 * Faster unit
 */
public class FastUnit extends Unit {
    
    public static final int COST = 55;
    
    public FastUnit(Position pos, Game g) {
        super(pos, 2, 300, g);
        type = 'f';
    }
}
