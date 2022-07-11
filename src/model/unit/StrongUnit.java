package model.unit;

import model.Position;

/**
 * Stronger unit
 */
public class StrongUnit extends Unit {
    
    public static final int COST = 50;
    
    public StrongUnit(Position pos, Position goalPos) {
        super(pos, goalPos, 1, 500, 200);
        type = 's';
    }

    @Override
    public int getCost() {
        return COST;
    }
    @Override
    public String getName() {
        return "Strong";
    }
}
