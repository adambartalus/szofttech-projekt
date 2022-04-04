package model;

/**
 * The headquarters building
 */
public class Castle extends Building {
    
    private int hp;
    
    public Castle(Position pos, Player player) {
        super(pos, player);
        this.hp = 5000;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
}
