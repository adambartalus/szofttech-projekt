package model.tower;

import exception.MaxLevelReachedException;
import exception.NotEnoughGoldException;
import model.Building;
import model.Buyable;
import model.Game;
import model.Player;
import model.Position;
import model.TowerShot;
import model.Upgradable;
import model.unit.Unit;


public abstract class Tower extends Building implements Buyable, Upgradable {

    protected int range;
    protected int level;
    protected int damage;
    protected int rangeinc;
    protected int damageinc;
    protected int goldSpent;
    public boolean aoe;
    private boolean frozen = false;
    
    protected Tower(int range, int damage, Position pos, Player player) {
        super(pos, player);
        this.level = 1;
        this.range = range;
        this.damage = damage;
        this.frozen = false;
        this.goldSpent = getCost();
    }
    public abstract String getName();
    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }
    
    public int getGoldSpent() {
        return goldSpent;
    }
    
    public int getGoldReturnOnDemolish() {
        return getGoldSpent() / 2;
    }
    
    public boolean isFrozen() {
        return frozen;
    }
    @Override
    public int getLevel() {
        return level;
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }
    @Override
    public void upgradeEffect() {
        range += rangeinc;
        damage += damageinc;
    }
    public void upgrade() throws MaxLevelReachedException, NotEnoughGoldException {
        if(getLevel() == getMaxLevel()) {
            throw new MaxLevelReachedException();
        }
        if(owner.getGold() < getUpgradeCost()) {
            throw new NotEnoughGoldException();
        }
        upgradeEffect();
        this.owner.decreaseGold(getUpgradeCost());
        this.goldSpent += getUpgradeCost();
        this.level += 1;
    }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    public void turn(Game g) {
        if(frozen) {
            frozen = false;
            return ;
        }
        for(Unit cUnit : g.getUnits()) {
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
