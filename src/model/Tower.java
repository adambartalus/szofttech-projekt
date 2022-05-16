package model;

import main.Main;

public class Tower extends Building {

    protected int range;
    protected int damage;
    protected int rangeinc;
    protected int damageinc;
    public int upgradecost;
    public boolean aoe;
    
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
        range += rangeinc;
        damage += damageinc;
    }
    
    public void turn() {
        Unit cUnit;
    	for(int i = 0; i < Main.gw.gameArea.game.getUnits().size(); i++) {
            cUnit = Main.gw.gameArea.game.getUnits().get(i);
            if(this.getOwner() != cUnit.owner){
                int distancex = Math.abs(cUnit.getPosition().getX() - this.getPosition().getX());
                int distancey = Math.abs(cUnit.getPosition().getY() - this.getPosition().getY());
                if(distancex <= range && distancey <= range) {
                    if(cUnit.takeDamage(damage))
                        this.owner.increaseGold(100);
                    Main.gw.gameArea.game.addTowerShot(new TowerShot(this, cUnit));
                }
                if(!aoe)
                    break;
            }
    	}
    }
}
