package model;

/**
 * A type of units that can get through obstacles
 */
public class ObstacleUnit extends Unit {
    
    public static final int COST = 60;
    
    public ObstacleUnit(Position pos, Game g) {
        
        super(pos, 1, 350, g);
        ignoreObstacle = true;
        type = 'o';
    }
}
