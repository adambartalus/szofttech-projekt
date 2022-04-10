package model;


public class Tower extends Building {

    protected int range;
    protected int damage;
    
    protected Tower(int range, int damage, Position pos, Player player) {
        super(pos, player);
        this.range = range;
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }
    
    public void upgrade() {
        //TODO
    }
}
