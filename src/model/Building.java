package model;

/**
 * Class of buildings
 */
public class Building {
    
    private final Position position;
    protected final Player owner;
    
    protected Building(Position pos, Player player) {
        this.position = pos;
        this.owner = player;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public Player getOwner() {
        return owner;
    }
}
