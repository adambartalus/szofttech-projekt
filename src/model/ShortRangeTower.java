package model;


public class ShortRangeTower extends Tower {

    public final static int COST = 150;
    public final static int UPGRADE_COST = 300;
    
    public ShortRangeTower(Position pos, Player player) {
        super(1, 90, pos, player);
        damageinc = 30;
        rangeinc = 0;
        type = 's';
    }
    @Override
    public String getName() {
        return "Short range tower";
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
