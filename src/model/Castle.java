package model;

/**
 * The headquarters building
 */
public class Castle extends Building {
    public static final int START_HP = 5000;
    private int hp;
    
    public Castle(Position pos, Player player) {
        super(pos, player);
        this.hp = START_HP;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
}
