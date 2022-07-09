package model;


public class LongRangeTower extends Tower {

    public final static int COST = 150;
    public final static int UPGRADE_COST = 300;
    
    public LongRangeTower(Position pos, Player player) {
        super(3, 58, pos, player);
        damageinc = 10;
        rangeinc = 2;
        type = 'l';
        freeze = false;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public int getUpgradeCost() {
        return UPGRADE_COST;
    }
    
}
