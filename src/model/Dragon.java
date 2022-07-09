package model;

/**
 * A type of units that can get through obstacles
 */
public class Dragon extends Unit {
    
    public static final int COST = 60;
    
    public Dragon(Position pos, Position goalPos) {
        super(pos, goalPos, 1, 350, 100);
        ignoreObstacle = true;
        type = 'o';
    }
}
