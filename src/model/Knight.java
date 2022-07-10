package model;

/**
 * Faster unit
 */
public class Knight extends Unit {
    
    public static final int COST = 55;
    
    public Knight(Position pos, Position goalPos) {
        super(pos, goalPos, 2, 300, 150);
        type = 'f';
    }

    @Override
    public int getCost() {
        return COST;
    }
}
