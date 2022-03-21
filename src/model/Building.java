package model;


public class Building {
    
    private final Position position;
    
    protected Building(Position pos) {
        this.position = pos;
    }
    
    public Position getPosition() {
        return position;
    }
}
