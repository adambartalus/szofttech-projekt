package model;


public class ShortRangeTower extends Tower {

    public final static int COST = 150;
    public final static int[] UPGRADE_COST = {200, 250}; 
    
    public ShortRangeTower(Position pos, Player player) {
        super(1, 90, pos, player);
        damageinc = 30;
        rangeinc = 0;
        upgradecost = 100;
        type = 's';
    }
    
}
