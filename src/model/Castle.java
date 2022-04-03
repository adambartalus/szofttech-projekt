package model;

/**
 * The headquarters building
 */
public class Castle extends Building {
    
    private int hp;
    
    public Castle(Position pos) {
        super(pos);
        this.hp = 5000;
    }
}
