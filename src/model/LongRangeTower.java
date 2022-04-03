package model;


public class LongRangeTower extends Tower {

    public final static int COST = 150;
    public final static int[] UPGRADE_COST = {200, 250}; 
    
    public LongRangeTower(Position pos, Player player) {
        super(5, 58, pos, player);
    }
    
}
