package model;


public class ShortRangeTower extends Tower {

    public final static int COST = 150;
    public final static int[] UPGRADE_COST = {200, 250}; 
    
    public ShortRangeTower(Position pos, Player player) {
        super(3, 90, pos, player);
    }
    
}