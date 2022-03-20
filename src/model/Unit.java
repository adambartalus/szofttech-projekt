package model;


public class Unit {
    
    private Position position;
    protected int speed;
    protected int hp;
    
    protected Unit(Position pos, int speed, int hp) {
        
        this.position = pos;
        this.speed = speed;
        this.hp = hp;
    }
    
    public Position getPosition() {
        return this.position;
    }
    
    public void takeDamage(int v) {
        this.hp -= v;
    }
    
    public void heal(int v) {
        this.hp += v;
    }
}
