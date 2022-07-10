package model;


public interface Upgradable {
    public int getLevel();
    public int getMaxLevel();
    public int getUpgradeCost();
    public void upgrade();
}
