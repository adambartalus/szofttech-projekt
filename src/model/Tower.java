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
    	for(int i = 0; i < Main.gw.gameArea.game.getUnits().size(); i++) {
    		if(this.getOwner() != Main.gw.gameArea.game.getUnits().get(i).owner){
        		int distancex = Main.gw.gameArea.game.getUnits().get(i).getPosition().getX() - this.getPosition().getX();
        		int distancey = Main.gw.gameArea.game.getUnits().get(i).getPosition().getY() - this.getPosition().getY();
        		int distance = distancex*distancex+distancey+distancey;
        		if(distance-1<range*range)
        			Main.gw.gameArea.game.getUnits().get(i).takeDamage(damage);
        		if(!aoe)
        			break;
    		}
    	}
    }
}
