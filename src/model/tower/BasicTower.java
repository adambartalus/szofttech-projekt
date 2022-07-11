package model.tower;

import model.Player;
import model.Position;


public class BasicTower extends Tower {

    public final static int COST = 150;
    public final static int UPGRADE_COST = 300;
            
    public BasicTower(Position pos, Player player) {
        super(2, 70, pos, player);
        damageinc = 10;
        rangeinc = 1;
        type = 'b';
    }
    
    @Override
    public String getName() {
        return "Basic tower";
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
