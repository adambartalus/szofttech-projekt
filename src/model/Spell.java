package model;

public interface Spell {
    public abstract void Effect(Position pos, Game game, Player player);
    
    public int getCost();
}
