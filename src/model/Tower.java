package model;


public abstract class Tower extends Building {

    protected int range;
    protected int damage;
    protected int rangeinc;
    protected int damageinc;
    protected int goldSpent;
    public boolean aoe;
    public boolean freeze = false;
    
    protected Tower(int range, int damage, Position pos, Player player) {
        super(pos, player);
        this.range = range;
        this.damage = damage;
        freeze = false;
        
        try {
            int cost = this.getClass().getField("COST").getInt(null);
            this.goldSpent = cost;
        } catch(Exception exc) {
            
        }
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }
    
    public int getGoldSpent() {
        return goldSpent;
    }
    
    
    public abstract int getCost();
    
    public abstract int getUpgradeCost();
    
    public void upgrade() {
        range += rangeinc;
        damage += damageinc;
        
        try {
            int upgradeCost = this.getClass().getField("UPGRADE_COST").getInt(null);
            this.goldSpent += upgradeCost;
        } catch(Exception exc) {
            
        }
    }
    
    public void turn(Game g) {
        if(freeze) {
            freeze = false;
            return ;
        }
        Unit cUnit;
        for(int i = 0; i < g.getUnits().size(); i++) {
            cUnit = g.getUnits().get(i);
            if(this.getOwner() != cUnit.owner){
                int distancex = Math.abs(cUnit.getPosition().getX() - this.getPosition().getX());
                int distancey = Math.abs(cUnit.getPosition().getY() - this.getPosition().getY());
                if(distancex <= range && distancey <= range) {
                    cUnit.takeDamage(damage);
                    if(cUnit.isDead()) {
                        this.owner.increaseGold(100);
                    }
                    g.addTowerShot(new TowerShot(this, cUnit));
                }
                if(!aoe)
                    break;
            }
        }
    }
}
